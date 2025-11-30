package br.edu.ifsp.kanban.controller.dto.response;

import br.edu.ifsp.kanban.model.canonical.BlocoCanonical;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TarefaResponseDto {
    private Integer idTarefa;
    private String texto;
    private boolean estado;
}
