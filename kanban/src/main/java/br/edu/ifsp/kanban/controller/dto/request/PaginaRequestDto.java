package br.edu.ifsp.kanban.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginaRequestDto {
    private Integer idPagina;
    private String nome;
}
