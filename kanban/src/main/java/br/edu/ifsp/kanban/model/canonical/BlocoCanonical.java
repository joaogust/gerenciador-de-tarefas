package br.edu.ifsp.kanban.model.canonical;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlocoCanonical {
    private Integer idBloco;
    private String nome;
    private String estado;
    private Integer idPagina;
    private PaginaCanonical pagina;
    private List<TarefaCanonical> tarefas = new ArrayList<>();
}
