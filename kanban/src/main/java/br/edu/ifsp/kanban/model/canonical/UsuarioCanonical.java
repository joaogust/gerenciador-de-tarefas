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
public class UsuarioCanonical {
    private Integer idUsuario;
    private String nome;
    private String email;
    private String senha;
    private List<PaginaUsuarioCanonical> paginas = new ArrayList<>();

}
