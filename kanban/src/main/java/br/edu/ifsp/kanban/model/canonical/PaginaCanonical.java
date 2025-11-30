package br.edu.ifsp.kanban.model.canonical;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginaCanonical {
    private Integer idPagina;
    private String nome;
    private List<BlocoCanonical> blocos;
    private List<PaginaUsuarioCanonical> usuarios;
/*
    public PaginaCanonical() {}

    public PaginaCanonical(Integer idPagina, String nome) {
        this.idPagina = idPagina;
        this.nome = nome;
    }

    public Integer getIdPagina() {
        return idPagina;
    }

    public void setIdPagina(Integer idPagina) {
        this.idPagina = idPagina;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
 */
}
