package br.edu.ifsp.kanban.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TarefaRequestDto {
    private String texto;
    private boolean estado;
    private Integer idBloco;
}
