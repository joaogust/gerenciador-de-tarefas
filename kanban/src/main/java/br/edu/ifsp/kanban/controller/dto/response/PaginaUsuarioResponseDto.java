package br.edu.ifsp.kanban.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginaUsuarioResponseDto {
    private String papel; // do relacionamento
    private PaginaResponseDto pagina;
/*
    public PaginaUsuarioResponseDto() { }

    public PaginaUsuarioResponseDto(String papel, PaginaResponseDto pagina) {
        this.papel = papel;
        this.pagina = pagina;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    public PaginaResponseDto getPagina() {
        return pagina;
    }

    public void setPagina(PaginaResponseDto pagina) {
        this.pagina = pagina;
    }

 */
}
