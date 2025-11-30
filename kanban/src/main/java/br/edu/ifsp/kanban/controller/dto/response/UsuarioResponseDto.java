package br.edu.ifsp.kanban.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDto {
    // Atributos que podem ser enviados em uma resposta
    private Integer idUsuario;
    private String nome;
    private String email;
    private List<PaginaResumoResponseDto> paginas;

}
