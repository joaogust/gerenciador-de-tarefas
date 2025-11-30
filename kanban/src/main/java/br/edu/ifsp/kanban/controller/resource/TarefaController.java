package br.edu.ifsp.kanban.controller.resource;

import br.edu.ifsp.kanban.controller.dto.factoryDto.TarefaDtoFactory;
import br.edu.ifsp.kanban.controller.dto.response.TarefaResponseDto;
import br.edu.ifsp.kanban.model.entity.Tarefa;
import br.edu.ifsp.kanban.service.TarefaService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tarefa")
public class TarefaController {
    private final TarefaService service;

    public TarefaController(TarefaService service) {
        this.service = service;
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TarefaResponseDto> getTarefaPorId(@PathVariable Integer id) {
        TarefaResponseDto tarefa = TarefaDtoFactory.canonicalToResponseDto(service.buscaTarefaPorId(id));
        if (tarefa != null) {
            return ResponseEntity.ok(tarefa);
        }
        return ResponseEntity.status(404).build();
    }
}
