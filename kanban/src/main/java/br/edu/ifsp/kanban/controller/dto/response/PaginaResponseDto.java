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
public class PaginaResponseDto {
    private Integer idPagina;
    private String nome;
    private List<BlocoResponseDto> blocos;
/*
    public PaginaResponseDto() {}

    public PaginaResponseDto(Integer idPagina, String nome, List<BlocoResponseDto> blocos) {
        this.idPagina = idPagina;
        this.nome = nome;
        this.blocos = blocos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdPagina() {
        return idPagina;
    }

    public void setIdPagina(Integer idPagina) {
        this.idPagina = idPagina;
    }

    public List<BlocoResponseDto> getBlocos() {
        return blocos;
    }

    public void setBlocos(List<BlocoResponseDto> blocos) {
        this.blocos = blocos;
    }

 */
}
