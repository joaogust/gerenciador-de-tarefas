package br.edu.ifsp.kanban.model.canonical;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginaCanonical {
    private Integer idPagina;
    private String nome;
    private List<BlocoCanonical> blocos = new ArrayList<>();
    private List<PaginaUsuarioCanonical> usuarios = new ArrayList<>();
}
