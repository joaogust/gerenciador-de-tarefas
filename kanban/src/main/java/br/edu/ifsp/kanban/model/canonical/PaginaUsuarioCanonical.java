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
/*
    // Construtores
    public PaginaUsuarioCanonical() {}

    public PaginaUsuarioCanonical(Integer idPagina, Integer idUsuario, String papel) {
        this.idPagina = idPagina;
        this.idUsuario = idUsuario;
        this.papel = papel;
    }

    // Getters e Setters

    public Integer getIdPagina() {
        return idPagina;
    }

    public void setIdPagina(Integer idPagina) {
        this.idPagina = idPagina;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }
 */
}
