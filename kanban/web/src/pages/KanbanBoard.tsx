import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
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
  const [pagina, setPagina] = useState<Pagina | null>(null);
  const [erro, setErro] = useState<string | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  
  // NOVO: Estado para rastrear o ID da tarefa com o dropdown de mover aberto
  const [openDropdownId, setOpenDropdownId] = useState<number | null>(null); 

  // NOVO: Função para alternar o dropdown
  const toggleDropdown = (idTarefa: number) => {
    setOpenDropdownId(openDropdownId === idTarefa ? null : idTarefa);
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
    }
  }

  useEffect(() => {
    carregarPagina();
  }, [id]);

  async function criarTarefa(idBloco: number) {
    const texto = prompt("Texto da nova tarefa:");
    if (!texto) return;

    await fetch("http://localhost:8080/api/tarefa", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        texto,
        estado: false,
        idBloco
      })
    });

    carregarPagina();
  }

  async function moverTarefa(idTarefa: number, idBlocoDestino: number) {
    const tarefa = pagina?.blocos
      .flatMap((b) => b.tarefas)
      .find((t) => t.idTarefa === idTarefa);

    if (!tarefa) return;

    await fetch(`http://localhost:8080/api/tarefa/${idTarefa}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        texto: tarefa.texto,
        estado: tarefa.estado,
        idBloco: idBlocoDestino
      })
    });

    carregarPagina();
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

  return (
    <div className="kanban-container">
      <header className="board-header">
        <h2>{pagina.nome}</h2>
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
                <h4>{bloco.nome}</h4>

                {bloco.tarefas.map((t) => (
                  <div key={t.idTarefa} className="kanban-card">
                    <div className="card-content">
                      <input type="checkbox" checked={t.estado} readOnly />
                      <div
                        className={
                          "card-text " + (t.estado ? "concluida" : "")
                        }
                      >
                        {t.texto}
                      </div>
                      {/* NOVO: Botões de Ação no Canto Superior Direito */}
                      <div className="card-actions"> 
                        
                        {/* Botão de Três Pontinhos (CRUD Placeholder) */}
                        <button className="btn-action">
                          <i className="icon-dots"></i> 
                        </button>

                        {/* Dropdown de Mover Tarefa */}
                        <div className="move-dropdown">
                          <button 
                            className="btn-action" 
                            onClick={(e) => { 
                                e.stopPropagation(); // Evita clique no card
                                toggleDropdown(t.idTarefa);
                            }}
                          >
                            <i 
                                className={`icon-chevron-down ${openDropdownId === t.idTarefa ? 'is-open' : ''}`}
                            ></i>
                          </button>

                          {/* Conteúdo do Dropdown de Mover */}
                          {openDropdownId === t.idTarefa && (
                            <div 
                                className="dropdown-menu"
                                // Adicionado onclick para fechar se clicar fora do menu (opcional)
                                onClick={(e) => e.stopPropagation()} 
                            >
                              <h6>Mover para:</h6>
                              {[...colTodo, ...colDoing, ...colDone]
                                .filter((b) => b.idBloco !== bloco.idBloco)
                                .map((dest) => (
                                  <button
                                    key={dest.idBloco}
                                    className="btn-dropdown-item"
                                    onClick={() => {
                                      moverTarefa(t.idTarefa, dest.idBloco);
                                      setOpenDropdownId(null); // Fecha após mover
                                    }}
                                  >
                                    {dest.nome}
                                  </button>
                                ))}
                            </div>
                          )}
                        </div>
                      </div>
                      {/* FIM: Botões de Ação */}
                    </div>

                    {/* REMOVIDO: O bloco antigo de botões de mover ficava aqui */}
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
                <h4>{bloco.nome}</h4>

                {bloco.tarefas.map((t) => (
                  <div key={t.idTarefa} className="kanban-card">
                    <div className="card-content">
                      <input type="checkbox" checked={t.estado} readOnly />
                      <div
                        className={
                          "card-text " + (t.estado ? "concluida" : "")
                        }
                      >
                        {t.texto}
                      </div>

                      <div className="card-actions"> 
                        <button className="btn-action">
                          <i className="icon-dots"></i> 
                        </button>
                        
                        <div className="move-dropdown">
                          <button 
                            className="btn-action" 
                            onClick={(e) => { 
                                e.stopPropagation();
                                toggleDropdown(t.idTarefa);
                            }}
                          >
                            <i 
                                className={`icon-chevron-down ${openDropdownId === t.idTarefa ? 'is-open' : ''}`}
                            ></i>
                          </button>

                          {openDropdownId === t.idTarefa && (
                            <div 
                                className="dropdown-menu"
                                onClick={(e) => e.stopPropagation()} 
                            >
                              <h6>Mover para:</h6>
                              {[...colTodo, ...colDoing, ...colDone]
                                .filter((b) => b.idBloco !== bloco.idBloco)
                                .map((dest) => (
                                  <button
                                    key={dest.idBloco}
                                    className="btn-dropdown-item"
                                    onClick={() => {
                                      moverTarefa(t.idTarefa, dest.idBloco);
                                      setOpenDropdownId(null);
                                    }}
                                  >
                                    {dest.nome}
                                  </button>
                                ))}
                            </div>
                          )}
                        </div>
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
                <h4>{bloco.nome}</h4>

                {bloco.tarefas.map((t) => (
                  <div key={t.idTarefa} className="kanban-card">
                    <div className="card-content">
                      <input type="checkbox" checked={t.estado} readOnly />
                      <div
                        className={
                          "card-text " + (t.estado ? "concluida" : "")
                        }
                      >
                        {t.texto}
                      </div>

                      <div className="card-actions"> 
                        <button className="btn-action">
                          <i className="icon-dots"></i> 
                        </button>
                        
                        <div className="move-dropdown">
                          <button 
                            className="btn-action" 
                            onClick={(e) => { 
                                e.stopPropagation();
                                toggleDropdown(t.idTarefa);
                            }}
                          >
                            <i 
                                className={`icon-chevron-down ${openDropdownId === t.idTarefa ? 'is-open' : ''}`}
                            ></i>
                          </button>

                          {openDropdownId === t.idTarefa && (
                            <div 
                                className="dropdown-menu"
                                onClick={(e) => e.stopPropagation()} 
                            >
                              <h6>Mover para:</h6>
                              {[...colTodo, ...colDoing, ...colDone]
                                .filter((b) => b.idBloco !== bloco.idBloco)
                                .map((dest) => (
                                  <button
                                    key={dest.idBloco}
                                    className="btn-dropdown-item"
                                    onClick={() => {
                                      moverTarefa(t.idTarefa, dest.idBloco);
                                      setOpenDropdownId(null);
                                    }}
                                  >
                                    {dest.nome}
                                  </button>
                                ))}
                            </div>
                          )}
                        </div>
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
          </div>
        </div>
      </div>
      {/* NOVO: Div para fechar o dropdown ao clicar em qualquer lugar fora dele */}
      {openDropdownId !== null && (
        <div className="overlay-click-closer" onClick={() => setOpenDropdownId(null)} />
      )}
    </div>
  );
}