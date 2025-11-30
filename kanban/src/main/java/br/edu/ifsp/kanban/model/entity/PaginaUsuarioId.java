package br.edu.ifsp.kanban.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PaginaUsuarioId implements Serializable {
    @Column(name = "id_pagina")
    private Integer idPagina;
    @Column(name = "id_usuario")
    private Integer idUsuario;

}
