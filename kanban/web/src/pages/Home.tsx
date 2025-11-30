import { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import logo from '../assets/logo_copyon.png';
import './Home.css';

const API_BASE_URL = 'http://localhost:8080/api';

interface Page {
    id: number;
    title: string;
    path: string;
    membros?: number;
}

interface UserData {
    id: number;
    name: string;
    email: string;
    pages: Page[];
}

export function Home() {
    const [isLoading, setIsLoading] = useState(true);
    const [userData, setUserData] = useState<UserData | null>(null);
    const navigate = useNavigate();

    // ------------------------- BUSCAR USUÁRIO -------------------------
    const fetchUserData = async () => {
        try {
            const email = localStorage.getItem("email");
            const token = localStorage.getItem("token");

            if (!email) {
                alert("Nenhum e-mail encontrado. Faça login novamente.");
                navigate("/");
                return;
            }

            const response = await fetch(
                `${API_BASE_URL}/usuario/email/${encodeURIComponent(email)}`,
                {
                    headers: {
                        "Content-Type": "application/json",
                        ...(token ? { Authorization: `Bearer ${token}` } : {})
                    }
                }
            );

            if (!response.ok) throw new Error("Erro ao buscar usuário");

            const data = await response.json();

            const formattedData: UserData = {
                id: data.idUsuario,
                name: data.nome,
                email: data.email,
                pages: (data.paginas ?? []).map((p: any) => ({
                    id: p.idPagina,
                    title: p.nome,
                    path: `/board/${p.idPagina}`,
                    membros: 1
                }))
            };

            setUserData(formattedData);
        } catch (err) {
            console.error(err);
            alert("Falha ao carregar dados do usuário.");
        } finally {
            setIsLoading(false);
        }
    };

    useEffect(() => {
        fetchUserData();
    }, []);

    // ------------------------- CRIAR PÁGINA -------------------------
    const handleCreatePage = async () => {
        const nomePagina = prompt("Nome da nova página:");
        if (!nomePagina) return;

        const token = localStorage.getItem("token");

        try {
            const response = await fetch(`${API_BASE_URL}/pagina`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    ...(token ? { Authorization: `Bearer ${token}` } : {})
                },
                body: JSON.stringify({ nome: nomePagina })
            });

            if (!response.ok) throw new Error("Erro criando página");

            const created = await response.json();
            const newPageId = created.idPagina;

            // Vincular usuário
            await fetch(`${API_BASE_URL}/paginas/${newPageId}/usuarios`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    ...(token ? { Authorization: `Bearer ${token}` } : {})
                },
                body: JSON.stringify({
                    email: userData?.email,
                    papel: "adm"
                })
            });

            // Atualizar na tela
            setUserData(prev =>
                prev
                    ? {
                          ...prev,
                          pages: [
                              ...prev.pages,
                              {
                                  id: newPageId,
                                  title: nomePagina,
                                  path: `/board/${newPageId}`,
                                  membros: 1
                              }
                          ]
                      }
                    : prev
            );
        } catch (err) {
            alert("Erro ao criar página.");
            console.error(err);
        }
    };

    const handleViewPage = (path: string) => navigate(path);

    // ------------------------- RENDER -------------------------
    return (
        <div className="home-container">
            <nav className="navbar navbar-expand-lg navbar-dark bg-dark border-bottom border-secondary px-4">
                <div className="container-fluid">
                    <Link className="navbar-brand d-flex align-items-center gap-2" to="#">
                        <img src={logo} height="30" alt="Logo" />
                        <strong>CopyOn</strong>
                    </Link>

                    <div className="d-flex align-items-center gap-3">
                        <span className="text-secondary small d-none d-md-block">
                            {userData ? `Olá, ${userData.name}` : "Carregando..."}
                        </span>
                        <div
                            className="rounded-circle bg-secondary d-flex justify-content-center align-items-center text-white"
                            style={{ width: 35, height: 35, cursor: "pointer" }}
                        >
                            <i className="fa-solid fa-user"></i>
                        </div>
                    </div>
                </div>
            </nav>

            <main className="main-content">
                <div className="welcome-section mb-5">
                    <h1 className="display-6 fw-bold text-white">
                        Bem vindo, {userData?.name || "Usuário"}!
                    </h1>
                    <p className="text-secondary">Gerencie seus projetos.</p>
                </div>

                <div className="section-header">
                    <h2>Suas páginas</h2>
                    <button className="btn-primary-custom" onClick={handleCreatePage}>
                        <i className="fa-solid fa-plus"></i> Criar Página
                    </button>
                </div>

                <div className="projects-grid mt-4">
                    {isLoading ? (
                        <div className="empty-state">
                            <div className="spinner-border text-primary" />
                        </div>
                    ) : userData?.pages.length ? (
                        userData.pages.map(page => (
                            <div
                                key={page.id}
                                className="note-card"
                                onClick={() => handleViewPage(page.path)}
                            >
                                <div className="card-body">
                                    <h5>{page.title}</h5>
                                    <p className="note-preview">
                                        Clique para visualizar o quadro Kanban.
                                    </p>
                                </div>

                                <div className="card-footer-info">
                                    <span>
                                        <i className="fa-regular fa-calendar"></i> Acessar
                                    </span>
                                    <span>
                                        <i className="fa-solid fa-users"></i> {page.membros}
                                    </span>
                                </div>
                            </div>
                        ))
                    ) : (
                        <div className="empty-state">
                            <i className="fa-regular fa-folder-open empty-icon"></i>
                            <h4>Nenhuma página encontrada</h4>
                            <p>Clique em "Criar Página" para começar.</p>
                        </div>
                    )}
                </div>
            </main>

            <footer className="py-4 mt-auto border-top border-secondary" style={{ backgroundColor: "#1c1f23" }}>
                <div className="container text-center">
                    <span className="text-muted small">&copy; 2025 CopyOn. Todos os direitos reservados.</span>
                </div>
            </footer>
        </div>
    );
}
