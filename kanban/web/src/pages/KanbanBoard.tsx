import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import "./KanbanBoard.css";

interface Tarefa {
  idTarefa: number;
  texto: string;
  estado: boolean;
}

interface Bloco {
  idBloco: number;
  nome: string;
  estado: string; // todo | doing | done
  tarefas: Tarefa[];
}

interface Pagina {
  idPagina: number;
  nome: string;
  blocos: Bloco[];
}

export default function KanbanBoard() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [pagina, setPagina] = useState<Pagina | null>(null);
  const [erro, setErro] = useState<string | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  
  // Estado para rastrear o ID da tarefa com o dropdown de mover aberto
  const [openDropdownId, setOpenDropdownId] = useState<number | null>(null); 
  
  // Estado para rastrear o dropdown de ações da página
  const [isPageDropdownOpen, setIsPageDropdownOpen] = useState(false); 

  // NOVO: Estado para rastrear o ID do bloco com o dropdown de ações aberto
  const [openBlockDropdownId, setOpenBlockDropdownId] = useState<number | null>(null); 

  // Função para alternar o dropdown da tarefa
  const toggleDropdown = (idTarefa: number) => {
    setOpenDropdownId(openDropdownId === idTarefa ? null : idTarefa);
    setIsPageDropdownOpen(false); // Fecha o dropdown da página
  };
  
  // Função para alternar o dropdown da página
  const togglePageDropdown = () => {
    setIsPageDropdownOpen(!isPageDropdownOpen);
    setOpenDropdownId(null); // Fecha o dropdown da tarefa
  };

  // Funções Auxiliares para CRUD de Blocos
  const toggleBlockDropdown = (idBloco: number) => {
    setOpenBlockDropdownId(openBlockDropdownId === idBloco ? null : idBloco);
  };
  
  async function carregarPagina() {
    try {
      setLoading(true);
      setErro(null);

      const token = localStorage.getItem("token");

      const response = await fetch(`http://localhost:8080/api/pagina/${id}`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          ...(token ? { Authorization: `Bearer ${token}` } : {})
        }
      });

      if (!response.ok) {
        setErro(`Erro ao carregar página (${response.status})`);
        return;
      }

      const data = await response.json();
      setPagina(data);
    } catch (e) {
      setErro("Erro ao conectar ao servidor.");
    } finally {
      setLoading(false);
      setOpenBlockDropdownId(null); // Fecha dropdowns de bloco ao recarregar
    }
  }
  
  // ============================================
  // FUNÇÕES CRUD PÁGINA
  // ============================================

  async function renomearPagina() {
    if (!pagina) return;
    const novoNome = prompt(`Renomear "${pagina.nome}" para:`, pagina.nome);

    if (novoNome && novoNome.trim() !== pagina.nome) {
        try {
            const token = localStorage.getItem("token");
            
            const response = await fetch(`http://localhost:8080/api/pagina/${id}`, {
                method: "PUT",
                headers: { 
                    "Content-Type": "application/json",
                    ...(token ? { Authorization: `Bearer ${token}` } : {})
                },
                body: JSON.stringify({ nome: novoNome })
            });

            if (response.ok) {
                // Atualiza o estado local
                setPagina({ ...pagina, nome: novoNome });
            } else {
                alert("Falha ao renomear página.");
            }
        } catch (e) {
            alert("Erro ao conectar ao servidor para renomear.");
        }
    }
    setIsPageDropdownOpen(false);
  }

  async function excluirPagina() {
    if (!pagina) return;

    if (window.confirm(`Tem certeza que deseja EXCLUIR permanentemente a página "${pagina.nome}"? Esta ação não pode ser desfeita.`)) {
        try {
            const token = localStorage.getItem("token");

            const response = await fetch(`http://localhost:8080/api/pagina/${id}`, {
                method: "DELETE",
                headers: { 
                    ...(token ? { Authorization: `Bearer ${token}` } : {})
                }
            });

            if (response.ok) {
                alert(`Página "${pagina.nome}" excluída com sucesso.`);
                // Redireciona para a tela inicial após a exclusão
                navigate("/home"); 
            } else {
                alert("Falha ao excluir página.");
            }
        } catch (e) {
            alert("Erro ao conectar ao servidor para excluir.");
        }
    }
    setIsPageDropdownOpen(false);
  }

  async function criarBloco(estado: string) {
      if (!pagina) return;
      const nome = prompt(`Nome do novo Bloco (${estado}):`);
      if (!nome) return;

      try {
          const token = localStorage.getItem("token");
          
          const response = await fetch(`http://localhost:8080/api/bloco`, {
              method: "POST",
              headers: { 
                  "Content-Type": "application/json",
                  ...(token ? { Authorization: `Bearer ${token}` } : {})
              },
              body: JSON.stringify({
                  nome: nome,
                  estado: estado,
                  idPagina: pagina.idPagina 
              })
          });

          if (response.ok) {
              carregarPagina(); // Recarrega para ver o novo bloco
          } else {
              alert("Falha ao criar bloco.");
          }
      } catch (e) {
          alert("Erro ao conectar ao servidor para criar bloco.");
      }
  }

  async function renomearBloco(blocoId: number, nomeAtual: string) {
      if (!pagina) return;
      const novoNome = prompt(`Renomear bloco "${nomeAtual}" para:`, nomeAtual);
      if (!novoNome || novoNome === nomeAtual) return;

      try {
          const token = localStorage.getItem("token");
          
          const response = await fetch(`http://localhost:8080/api/bloco/${blocoId}`, {
              method: "PUT",
              headers: {
                  "Content-Type": "application/json",
                  ...(token ? { Authorization: `Bearer ${token}` } : {})
              },
              body: JSON.stringify({
                  nome: novoNome,
                  // O backend exige idPagina no DTO, mesmo para PUT (ajustado para ser enviado)
                  idPagina: pagina.idPagina 
              })
          });

          if (response.ok) {
              carregarPagina(); // Recarrega para ver o bloco atualizado
          } else {
              alert("Falha ao renomear bloco.");
          }
      } catch (e) {
          alert("Erro ao conectar ao servidor para renomear bloco.");
      }
  }

  async function excluirBloco(blocoId: number, nomeBloco: string) {
      if (!window.confirm(`Tem certeza que deseja EXCLUIR o bloco "${nomeBloco}"? Todas as tarefas nele serão removidas.`)) {
          return;
      }

      try {
          const token = localStorage.getItem("token");

          const response = await fetch(`http://localhost:8080/api/bloco/${blocoId}`, {
              method: "DELETE",
              headers: { 
                  ...(token ? { Authorization: `Bearer ${token}` } : {})
              }
          });

          if (response.ok) {
              carregarPagina(); // Recarrega após exclusão
          } else {
              alert("Falha ao excluir bloco.");
          }
      } catch (e) {
          alert("Erro ao conectar ao servidor para excluir bloco.");
      }
  }

  useEffect(() => {
    carregarPagina();
  }, [id]);

  async function atualizarBloco(blocoId: number, dados: { nome?: string; estado?: string; idPagina?: number }) {
    try {
        const token = localStorage.getItem("token");
        
        const response = await fetch(`http://localhost:8080/api/bloco/${blocoId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                ...(token ? { Authorization: `Bearer ${token}` } : {})
            },
            body: JSON.stringify(dados)
        });

        if (!response.ok) throw new Error("Falha ao atualizar bloco.");
        carregarPagina();
    } catch (e) {
        alert("Erro ao atualizar bloco.");
    }
}

  async function moverBloco(idBloco: number, novoEstado: string) {
        if (!pagina) return;
        // Chama a função genérica para fazer o PUT, alterando o estado
        await atualizarBloco(idBloco, { estado: novoEstado, idPagina: pagina.idPagina });
        setOpenBlockDropdownId(null);
    }

    // ===================== CRIAR TAREFA =====================
    async function criarTarefa(idBloco: number) {
    const texto = prompt("Texto da nova tarefa:");
    if (!texto) return;

    try {
        const token = localStorage.getItem("token");
        const res = await fetch("http://localhost:8080/api/tarefa", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            ...(token ? { Authorization: `Bearer ${token}` } : {})
        },
        body: JSON.stringify({ texto, estado: false, idBloco })
        });

        if (!res.ok) throw new Error("Falha ao criar tarefa");

        await carregarPagina();
    } catch (e) {
        alert("Erro ao criar tarefa.");
    }
    }

    // ===================== ATUALIZAR TAREFA =====================
    async function atualizarTarefa(
        idTarefa: number,
        dados: { texto?: string; estado?: boolean; idBloco?: number }
        ) {
        try {
            const token = localStorage.getItem("token");
            const res = await fetch(`http://localhost:8080/api/tarefa/${idTarefa}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                ...(token ? { Authorization: `Bearer ${token}` } : {})
            },
            body: JSON.stringify(dados)
            });

            if (!res.ok) throw new Error("Falha ao atualizar tarefa");

            await carregarPagina();
        } catch (e) {
            alert("Erro ao atualizar tarefa.");
        }
        }

        async function renomearTarefa(tarefa: Tarefa) {
    const novoTexto = prompt("Renomear tarefa:", tarefa.texto);
    if (!novoTexto || novoTexto === tarefa.texto) return;

    await atualizarTarefa(tarefa.idTarefa, { texto: novoTexto });
    setOpenDropdownId(null); // fecha o dropdown
    }

    // ===================== TOGGLE CONCLUÍDA =====================
    async function toggleConcluida(tarefa: Tarefa) {
    await atualizarTarefa(tarefa.idTarefa, { estado: !tarefa.estado });
    }

  // ===================== DELETAR TAREFA =====================
    async function deletarTarefa(idTarefa: number) {
    if (!window.confirm("Deseja realmente excluir esta tarefa?")) return;

    try {
        const token = localStorage.getItem("token");
        const res = await fetch(`http://localhost:8080/api/tarefa/${idTarefa}`, {
        method: "DELETE",
        headers: token ? { Authorization: `Bearer ${token}` } : {}
        });

        if (!res.ok) throw new Error("Falha ao deletar tarefa");

        await carregarPagina();
    } catch (e) {
        alert("Erro ao deletar tarefa.");
    }
    }

  if (loading) return <p>Carregando...</p>;
  if (erro) return <p>{erro}</p>;
  if (!pagina) return <p>Falha ao carregar.</p>;

  // ============================
  // AGRUPAR BLOCOS POR ESTADO
  // ============================
  const colTodo = pagina.blocos.filter((b) => b.estado === "todo");
  const colDoing = pagina.blocos.filter((b) => b.estado === "doing");
  const colDone = pagina.blocos.filter((b) => b.estado === "done");

  

  // ... (Dentro do componente KanbanBoard, antes do return) ...

    const colunasDisponiveis = [
        { nome: "To Do", estado: "todo" },
        { nome: "Doing", estado: "doing" },
        { nome: "Done", estado: "done" },
    ];
    
// ... (O resto das suas funções) ...

return (
    <div className="kanban-container">
      <header className="board-header">
        <div className="page-header-title-group"> {/* Container para o Título e Ações da Página */}
          <h2>{pagina.nome}</h2>
          
          {/* Botão e Dropdown de Ações da Página */}
          <div className="page-actions-dropdown">
            <button 
              className="btn-action-page"
              onClick={togglePageDropdown}
            >
              <i className="icon-dots-page"></i> 
            </button>
            
            {isPageDropdownOpen && (
              <div 
                className="dropdown-menu page-dropdown"
                onClick={(e) => e.stopPropagation()} 
              >
                <button 
                  className="btn-dropdown-item"
                  onClick={renomearPagina}
                >
                  Renomear Página
                </button>
                {/* Adicionando divisor para manter consistência no design do menu */}
                <hr className="dropdown-divider" /> 
                <button 
                  className="btn-dropdown-item btn-delete-page"
                  onClick={excluirPagina}
                >
                  Excluir Página
                </button>
              </div>
            )}
          </div>
        </div>
      </header>

      <div className="columns-wrapper">
        
        {/* ================= COLUNA TODO ================= */}
        <div className="kanban-column">
          <div className="column-header">
            <h3>To Do</h3>
            <span className="badge-count">{colTodo.length}</span>
          </div>

          <div className="task-list">
            {colTodo.map((bloco) => (
              <div key={bloco.idBloco}>
                
                {/* NOVO: Header do Bloco com Ações de CRUD */}
                <div className="block-header-with-actions">
                    <h4>{bloco.nome}</h4>
                    <div className="block-actions-dropdown">
                        <button 
                            className="btn-action-block"
                            onClick={(e) => {
                                e.stopPropagation();
                                toggleBlockDropdown(bloco.idBloco);
                            }}
                        >
                            <i className="icon-dots"></i> 
                        </button>
                        
                        {openBlockDropdownId === bloco.idBloco && (
                            <div 
                                className="dropdown-menu block-dropdown"
                                onClick={(e) => e.stopPropagation()} 
                            >
                                <button 
                                    className="btn-dropdown-item"
                                    onClick={() => renomearBloco(bloco.idBloco, bloco.nome)}
                                >
                                    Renomear Bloco
                                </button>
                                
                                {/* ADICIONADO: LÓGICA DE MOVER BLOCO AQUI */}
                                <hr className="dropdown-divider" />
                                <h6 className="dropdown-header">Mover Bloco para:</h6>
                                {colunasDisponiveis
                                    .filter(col => col.estado !== bloco.estado) // Filtra a coluna atual
                                    .map(colDestino => (
                                        <button 
                                            key={colDestino.estado}
                                            className="btn-dropdown-item" 
                                            onClick={() => {
                                                moverBloco(bloco.idBloco, colDestino.estado); 
                                                setOpenBlockDropdownId(null);
                                            }}
                                        >
                                            {colDestino.nome}
                                        </button>
                                    ))}
                                <hr className="dropdown-divider" />
                                {/* FIM: LÓGICA DE MOVER BLOCO */}

                                <button 
                                    className="btn-dropdown-item btn-delete-page"
                                    onClick={() => excluirBloco(bloco.idBloco, bloco.nome)}
                                >
                                    Excluir Bloco
                                </button>
                            </div>
                        )}
                    </div>
                </div>

                {bloco.tarefas.map((t) => (
                    <div key={t.idTarefa} className="kanban-card">
                        <div className="card-content">
                            {/* Checkbox para marcar como concluída */}
                            <input
                                type="checkbox"
                                checked={t.estado}
                                onChange={() => toggleConcluida(t)}
                            />

                            {/* Texto da tarefa */}
                            <div className={"card-text " + (t.estado ? "concluida" : "")}>
                                {t.texto}
                            </div>

                            {/* Ações do cartão */}
                            <div className="card-actions">
                                {/* Botão dos três pontinhos */}
                                <button
                                    className="btn-action"
                                    onClick={(e) => {
                                        e.stopPropagation();
                                        toggleDropdown(t.idTarefa); 
                                    }}
                                >
                                    <i className="icon-dots"></i>
                                </button>

                                {/* Dropdown de ações da tarefa (NÃO TEM A OPÇÃO MOVER) */}
                                {openDropdownId === t.idTarefa && (
                                    <div className="dropdown-menu" onClick={(e) => e.stopPropagation()}>
                                        {/* 1. Opção Renomear */}
                                        <button
                                            className="btn-dropdown-item"
                                            onClick={() => renomearTarefa(t)}
                                        >
                                            Renomear tarefa
                                        </button>

                                        <hr className="dropdown-divider" />

                                        {/* 2. Opção Excluir */}
                                        <button
                                            className="btn-dropdown-item btn-delete-task"
                                            onClick={() => deletarTarefa(t.idTarefa)}
                                        >
                                            Excluir tarefa
                                        </button>
                                    </div>
                                )}
                            </div>
                        </div>
                    </div>
                ))}




                <div className="add-task-container">
                  <button
                    className="btn-add-task"
                    onClick={() => criarTarefa(bloco.idBloco)}
                  >
                    + Nova tarefa
                  </button>
                </div>
              </div>
            ))}
            
            {/* NOVO: Botão para CRIAR BLOCO se a coluna estiver vazia */}
            {colTodo.length === 0 && (
                <div className="add-block-container">
                    <button
                        className="btn-add-block"
                        onClick={() => criarBloco("todo")}
                    >
                        + Adicionar Bloco
                    </button>
                </div>
            )}
          </div>
        </div>

        {/* ================= COLUNA DOING ================= */}
        <div className="kanban-column">
          <div className="column-header">
            <h3>Doing</h3>
            <span className="badge-count">{colDoing.length}</span>
          </div>

          <div className="task-list">
            {colDoing.map((bloco) => (
              <div key={bloco.idBloco}>
                
                {/* NOVO: Header do Bloco com Ações de CRUD */}
                <div className="block-header-with-actions">
                    <h4>{bloco.nome}</h4>
                    <div className="block-actions-dropdown">
                        <button 
                            className="btn-action-block"
                            onClick={(e) => {
                                e.stopPropagation();
                                toggleBlockDropdown(bloco.idBloco);
                            }}
                        >
                            <i className="icon-dots"></i> 
                        </button>
                        
                        {openBlockDropdownId === bloco.idBloco && (
                            <div 
                                className="dropdown-menu block-dropdown"
                                onClick={(e) => e.stopPropagation()} 
                            >
                                <button 
                                    className="btn-dropdown-item"
                                    onClick={() => renomearBloco(bloco.idBloco, bloco.nome)}
                                >
                                    Renomear Bloco
                                </button>
                                
                                {/* ADICIONADO: LÓGICA DE MOVER BLOCO AQUI */}
                                <hr className="dropdown-divider" />
                                <h6 className="dropdown-header">Mover Bloco para:</h6>
                                {colunasDisponiveis
                                    .filter(col => col.estado !== bloco.estado) // Filtra a coluna atual
                                    .map(colDestino => (
                                        <button 
                                            key={colDestino.estado}
                                            className="btn-dropdown-item" 
                                            onClick={() => {
                                                moverBloco(bloco.idBloco, colDestino.estado); 
                                                setOpenBlockDropdownId(null);
                                            }}
                                        >
                                            {colDestino.nome}
                                        </button>
                                    ))}
                                <hr className="dropdown-divider" />
                                {/* FIM: LÓGICA DE MOVER BLOCO */}

                                <button 
                                    className="btn-dropdown-item btn-delete-page"
                                    onClick={() => excluirBloco(bloco.idBloco, bloco.nome)}
                                >
                                    Excluir Bloco
                                </button>
                            </div>
                        )}
                    </div>
                </div>

                {bloco.tarefas.map((t) => (
                    <div key={t.idTarefa} className="kanban-card">
                        <div className="card-content">
                            {/* Checkbox para marcar como concluída */}
                            <input
                                type="checkbox"
                                checked={t.estado}
                                onChange={() => toggleConcluida(t)}
                            />

                            {/* Texto da tarefa */}
                            <div className={"card-text " + (t.estado ? "concluida" : "")}>
                                {t.texto}
                            </div>

                            {/* Ações do cartão */}
                            <div className="card-actions">
                                {/* Botão dos três pontinhos */}
                                <button
                                    className="btn-action"
                                    onClick={(e) => {
                                        e.stopPropagation();
                                        toggleDropdown(t.idTarefa); 
                                    }}
                                >
                                    <i className="icon-dots"></i>
                                </button>

                                {/* Dropdown de ações da tarefa (NÃO TEM A OPÇÃO MOVER) */}
                                {openDropdownId === t.idTarefa && (
                                    <div className="dropdown-menu" onClick={(e) => e.stopPropagation()}>
                                        {/* 1. Opção Renomear */}
                                        <button
                                            className="btn-dropdown-item"
                                            onClick={() => renomearTarefa(t)}
                                        >
                                            Renomear tarefa
                                        </button>

                                        <hr className="dropdown-divider" />

                                        {/* 2. Opção Excluir */}
                                        <button
                                            className="btn-dropdown-item btn-delete-task"
                                            onClick={() => deletarTarefa(t.idTarefa)}
                                        >
                                            Excluir tarefa
                                        </button>
                                    </div>
                                )}
                            </div>
                        </div>
                    </div>
                ))}


                <div className="add-task-container">
                  <button
                    className="btn-add-task"
                    onClick={() => criarTarefa(bloco.idBloco)}
                  >
                    + Nova tarefa
                  </button>
                </div>
              </div>
            ))}
            
            {/* NOVO: Botão para CRIAR BLOCO se a coluna estiver vazia */}
            {colDoing.length === 0 && (
                <div className="add-block-container">
                    <button
                        className="btn-add-block"
                        onClick={() => criarBloco("doing")}
                    >
                        + Adicionar Bloco
                    </button>
                </div>
            )}
          </div>
        </div>

        {/* ================= COLUNA DONE ================= */}
        <div className="kanban-column">
          <div className="column-header">
            <h3>Done</h3>
            <span className="badge-count">{colDone.length}</span>
          </div>

          <div className="task-list">
            {colDone.map((bloco) => (
              <div key={bloco.idBloco}>
                
                {/* NOVO: Header do Bloco com Ações de CRUD */}
                <div className="block-header-with-actions">
                    <h4>{bloco.nome}</h4>
                    <div className="block-actions-dropdown">
                        <button 
                            className="btn-action-block"
                            onClick={(e) => {
                                e.stopPropagation();
                                toggleBlockDropdown(bloco.idBloco);
                            }}
                        >
                            <i className="icon-dots"></i> 
                        </button>
                        
                        {openBlockDropdownId === bloco.idBloco && (
                            <div 
                                className="dropdown-menu block-dropdown"
                                onClick={(e) => e.stopPropagation()} 
                            >
                                <button 
                                    className="btn-dropdown-item"
                                    onClick={() => renomearBloco(bloco.idBloco, bloco.nome)}
                                >
                                    Renomear Bloco
                                </button>

                                {/* ADICIONADO: LÓGICA DE MOVER BLOCO AQUI */}
                                <hr className="dropdown-divider" />
                                <h6 className="dropdown-header">Mover Bloco para:</h6>
                                {colunasDisponiveis
                                    .filter(col => col.estado !== bloco.estado) // Filtra a coluna atual
                                    .map(colDestino => (
                                        <button 
                                            key={colDestino.estado}
                                            className="btn-dropdown-item" 
                                            onClick={() => {
                                                moverBloco(bloco.idBloco, colDestino.estado); 
                                                setOpenBlockDropdownId(null);
                                            }}
                                        >
                                            {colDestino.nome}
                                        </button>
                                    ))}
                                <hr className="dropdown-divider" />
                                {/* FIM: LÓGICA DE MOVER BLOCO */}
                                
                                <button 
                                    className="btn-dropdown-item btn-delete-page"
                                    onClick={() => excluirBloco(bloco.idBloco, bloco.nome)}
                                >
                                    Excluir Bloco
                                </button>
                            </div>
                        )}
                    </div>
                </div>

                {bloco.tarefas.map((t) => (
                    <div key={t.idTarefa} className="kanban-card">
                        <div className="card-content">
                            {/* Checkbox para marcar como concluída */}
                            <input
                                type="checkbox"
                                checked={t.estado}
                                onChange={() => toggleConcluida(t)}
                            />

                            {/* Texto da tarefa */}
                            <div className={"card-text " + (t.estado ? "concluida" : "")}>
                                {t.texto}
                            </div>

                            {/* Ações do cartão */}
                            <div className="card-actions">
                                {/* Botão dos três pontinhos */}
                                <button
                                    className="btn-action"
                                    onClick={(e) => {
                                        e.stopPropagation();
                                        toggleDropdown(t.idTarefa); 
                                    }}
                                >
                                    <i className="icon-dots"></i>
                                </button>

                                {/* Dropdown de ações da tarefa (NÃO TEM A OPÇÃO MOVER) */}
                                {openDropdownId === t.idTarefa && (
                                    <div className="dropdown-menu" onClick={(e) => e.stopPropagation()}>
                                        {/* 1. Opção Renomear */}
                                        <button
                                            className="btn-dropdown-item"
                                            onClick={() => renomearTarefa(t)}
                                        >
                                            Renomear tarefa
                                        </button>

                                        <hr className="dropdown-divider" />

                                        {/* 2. Opção Excluir */}
                                        <button
                                            className="btn-dropdown-item btn-delete-task"
                                            onClick={() => deletarTarefa(t.idTarefa)}
                                        >
                                            Excluir tarefa
                                        </button>
                                    </div>
                                )}
                            </div>
                        </div>
                    </div>
                ))}


                <div className="add-task-container">
                  <button
                    className="btn-add-task"
                    onClick={() => criarTarefa(bloco.idBloco)}
                  >
                    + Nova tarefa
                  </button>
                </div>
              </div>
            ))}
            
            {/* NOVO: Botão para CRIAR BLOCO se a coluna estiver vazia */}
            {colDone.length === 0 && (
                <div className="add-block-container">
                    <button
                        className="btn-add-block"
                        onClick={() => criarBloco("done")}
                    >
                        + Adicionar Bloco
                    </button>
                </div>
            )}
          </div>
        </div>
      </div>
      
      {/* Divs para fechar dropdowns ao clicar fora */}
      {openDropdownId !== null && (
        <div className="overlay-click-closer" onClick={() => setOpenDropdownId(null)} />
      )}
      {isPageDropdownOpen && (
        <div className="overlay-click-closer" onClick={() => setIsPageDropdownOpen(false)} />
      )}
      {openBlockDropdownId !== null && (
        <div className="overlay-click-closer" onClick={() => setOpenBlockDropdownId(null)} />
      )}
    </div>
  );
}