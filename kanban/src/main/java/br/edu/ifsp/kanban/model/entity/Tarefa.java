package br.edu.ifsp.kanban.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tarefa")
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarefa")
    private Integer idTarefa;

    @Column(name = "texto")
    private String texto;
    @Column(name = "estado")
    private boolean estado;

    // Relacionamentos
    @Column(name = "id_bloco")
    private Integer idBloco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_bloco", insertable = false, updatable = false)
    private Bloco bloco;
/*
    // Construtor vazio
    public Tarefa() {}

    // Getters e Setters

    public Integer getIdTarefa() {
        return idTarefa;
    }

    public void setIdTarefa(Integer idTarefa) {
        this.idTarefa = idTarefa;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Integer getIdBloco() {
        return idBloco;
    }

    public void setIdBloco(Integer idBloco) {
        this.idBloco = idBloco;
    }

    public Bloco getBloco() {
        return bloco;
    }

    public void setBloco(Bloco bloco) {
        this.bloco = bloco;
    }
    */
}
