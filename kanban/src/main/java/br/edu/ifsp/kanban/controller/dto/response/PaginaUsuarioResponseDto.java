package br.edu.ifsp.kanban.controller.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginaUsuarioResponseDto {
    private Integer idPagina;
    private Integer idUsuario;
    private String papel;
    private PaginaResponseDto pagina;
    private UsuarioResponseDto usuario;
}
