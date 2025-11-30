package br.edu.ifsp.kanban.controller.resource;

import br.edu.ifsp.kanban.controller.dto.factoryDto.TarefaDtoFactory;
import br.edu.ifsp.kanban.controller.dto.request.TarefaRequestDto;
import br.edu.ifsp.kanban.controller.dto.response.TarefaResponseDto;
import br.edu.ifsp.kanban.model.canonical.TarefaCanonical;
import br.edu.ifsp.kanban.model.canonical.factoryCanonical.TarefaCanonicalFactory;
import br.edu.ifsp.kanban.model.entity.Tarefa;
import br.edu.ifsp.kanban.service.TarefaService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tarefa")
public class TarefaController {

    private final TarefaService service;

    public TarefaController(TarefaService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TarefaResponseDto> getTarefaPorId(@PathVariable Integer id) {
        TarefaResponseDto tarefa = TarefaDtoFactory.canonicoParaDto(service.buscaTarefaPorId(id));
        return ResponseEntity.ok(tarefa);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TarefaResponseDto> criarTarefa(@RequestBody TarefaRequestDto dto) {
        TarefaCanonical created = service.criarTarefa(TarefaDtoFactory.dtoToCanonical(dto));
        return ResponseEntity.ok(TarefaDtoFactory.canonicoParaDto(created));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TarefaResponseDto> atualizarTarefa(@PathVariable Integer id,
                                                             @RequestBody TarefaRequestDto dto) {
        TarefaCanonical canonical = TarefaDtoFactory.dtoToCanonical(dto);
        canonical.setIdTarefa(id);

        TarefaCanonical atualizado = service.atualizarTarefa(canonical);
        return ResponseEntity.ok(TarefaDtoFactory.canonicoParaDto(atualizado));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Integer id) {
        service.deletarTarefa(id);
        return ResponseEntity.noContent().build();
    }
}
