package br.edu.ifsp.kanban.model.canonical;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarefaCanonical {
    private Integer idTarefa;
    private String texto;
    private boolean estado;
    private Integer idBloco;
    private BlocoCanonical bloco;

}
