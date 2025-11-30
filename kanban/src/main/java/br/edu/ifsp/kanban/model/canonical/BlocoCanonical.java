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
public class BlocoCanonical {
    private Integer idBloco;
    private String nome;
    private String estado;
    private Integer idPagina;
    private PaginaCanonical pagina;
    private List<TarefaCanonical> tarefas;
/*
    public BlocoCanonical() { }

    public BlocoCanonical(Integer idBloco, String nome, String estado, Integer idPagina) {
        this.idBloco = idBloco;
        this.nome = nome;
        this.estado = estado;
        this.idPagina = idPagina;
    }

    public Integer getIdBloco() {
        return idBloco;
    }

    public void setIdBloco(Integer idBloco) {
        this.idBloco = idBloco;
    }

    public Integer getIdPagina() {
        return idPagina;
    }

    public void setIdPagina(Integer idPagina) {
        this.idPagina = idPagina;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
 */
}
