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
    private List<PaginaUsuarioResponseDto> paginas;
/*
    // Construtores
    public UsuarioResponseDto() {}

    public UsuarioResponseDto(Integer idUsuario, String nome, List<PaginaUsuarioResponseDto> paginas, String email) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.paginas = paginas;
        this.email = email;
    }

    // Getters e Setters
    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<PaginaUsuarioResponseDto> getPaginas() {
        return paginas;
    }

    public void setPaginas(List<PaginaUsuarioResponseDto> paginas) {
        this.paginas = paginas;
    }
 */
}
