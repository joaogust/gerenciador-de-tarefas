package br.edu.ifsp.kanban.controller.dto.factoryDto;

import br.edu.ifsp.kanban.controller.dto.response.PaginaResumoResponseDto;
import br.edu.ifsp.kanban.controller.dto.response.UsuarioResponseDto;
import br.edu.ifsp.kanban.model.canonical.PaginaUsuarioCanonical;
import br.edu.ifsp.kanban.model.canonical.UsuarioCanonical;
import br.edu.ifsp.kanban.model.entity.Usuario;

import java.util.List;

public class UsuarioDtoFactory {

    public static UsuarioResponseDto canonicoParaDto(UsuarioCanonical canonical) {
        UsuarioResponseDto dto = new UsuarioResponseDto();
        dto.setIdUsuario(canonical.getIdUsuario());
        dto.setNome(canonical.getNome());
        dto.setEmail(canonical.getEmail());

        List<PaginaUsuarioCanonical> paginasDoUsuario = canonical.getPaginas();
        dto.setPaginas(
                paginasDoUsuario.stream()
                        .map(pu -> new PaginaResumoResponseDto(
                                pu.getPagina().getIdPagina(),
                                pu.getPagina().getNome()
                        ))
                        .toList()
        );

        return dto;
    }
}
