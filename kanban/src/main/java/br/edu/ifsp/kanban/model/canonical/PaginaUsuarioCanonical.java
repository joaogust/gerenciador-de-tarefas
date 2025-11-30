package br.edu.ifsp.kanban.model.canonical;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginaUsuarioCanonical {
    private Integer idPagina;
    private Integer idUsuario;
    private String papel;
    private PaginaCanonical pagina;
    private UsuarioCanonical usuario;
}
