package br.edu.ifsp.kanban.controller.dto.factoryDto;

import br.edu.ifsp.kanban.controller.dto.response.PaginaUsuarioResponseDto;
import br.edu.ifsp.kanban.model.canonical.PaginaUsuarioCanonical;

public class PaginaUsuarioDtoFactory {

    public static PaginaUsuarioResponseDto canonicoParaDto(PaginaUsuarioCanonical canonical) {
        PaginaUsuarioResponseDto dto = new PaginaUsuarioResponseDto();

        dto.setIdPagina(canonical.getIdPagina());
        dto.setIdUsuario(canonical.getIdUsuario());
        dto.setPapel(canonical.getPapel());

        if (canonical.getPagina() != null) {
            dto.setPagina(PaginaDtoFactory.canonicoParaDto(canonical.getPagina()));
        }

        if (canonical.getUsuario() != null) {
            dto.setUsuario(UsuarioDtoFactory.canonicoParaDto(canonical.getUsuario()));
        }

        return dto;
    }
}
