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

    /*
    public TarefaCanonical() { }

    public TarefaCanonical(Integer idTarefa, String texto, boolean estado, Integer idBloco) {
        this.idTarefa = idTarefa;
        this.texto = texto;
        this.estado = estado;
        this.idBloco = idBloco;
    }

    public Integer getIdTarefa() {
        return idTarefa;
    }

    public void setIdTarefa(Integer idTarefa) {
        this.idTarefa = idTarefa;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Integer getIdBloco() {
        return idBloco;
    }

    public void setIdBloco(Integer idBloco) {
        this.idBloco = idBloco;
    }
     */
}
