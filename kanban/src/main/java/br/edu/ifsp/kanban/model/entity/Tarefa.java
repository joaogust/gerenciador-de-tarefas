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
}
