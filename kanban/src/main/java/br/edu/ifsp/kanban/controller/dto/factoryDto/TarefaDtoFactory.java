package br.edu.ifsp.kanban.controller.dto.factoryDto;

import br.edu.ifsp.kanban.controller.dto.request.TarefaRequestDto;
import br.edu.ifsp.kanban.controller.dto.response.TarefaResponseDto;
import br.edu.ifsp.kanban.model.canonical.TarefaCanonical;
public class TarefaDtoFactory {
    public static TarefaCanonical dtoToCanonical(TarefaRequestDto dto) {
        TarefaCanonical canonical = new TarefaCanonical();
        canonical.setTexto(dto.getTexto());
        canonical.setEstado(dto.isEstado());
        canonical.setIdBloco(dto.getIdBloco());
        return canonical;
    }

    public static TarefaResponseDto canonicalToResponseDto(TarefaCanonical canonical) {
        TarefaResponseDto dto = new TarefaResponseDto();
        dto.setIdTarefa(canonical.getIdTarefa());
        dto.setTexto(canonical.getTexto());
        dto.setEstado(canonical.isEstado());
        return dto;
    }
}
