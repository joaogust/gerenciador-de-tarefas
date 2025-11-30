package br.edu.ifsp.kanban.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlocoResponseDto {
    private Integer idBloco;
    private String nome;
    private String estado;
    private List<TarefaResponseDto> tarefas;
}
