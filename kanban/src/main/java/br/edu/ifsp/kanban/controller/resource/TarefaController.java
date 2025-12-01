package br.edu.ifsp.kanban.controller.resource;

import br.edu.ifsp.kanban.controller.dto.factoryDto.TarefaDtoFactory;
import br.edu.ifsp.kanban.controller.dto.request.TarefaRequestDto;
import br.edu.ifsp.kanban.controller.dto.response.TarefaResponseDto;
import br.edu.ifsp.kanban.model.canonical.TarefaCanonical;
import br.edu.ifsp.kanban.model.canonical.factoryCanonical.TarefaCanonicalFactory;
import br.edu.ifsp.kanban.model.entity.Tarefa;
import br.edu.ifsp.kanban.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tarefa")
// 1. Tag: Define o nome do grupo no Swagger UI.
@Tag(name = "Tarefas", description = "Operações para gerenciamento e manipulação das tarefas dentro dos blocos.")
public class TarefaController {

    private final TarefaService service;

    public TarefaController(TarefaService service) {
        this.service = service;
    }

    // --- 1. BUSCAR POR ID ---
    @Operation(summary = "Buscar Tarefa por ID", description = "Retorna os detalhes de uma tarefa específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TarefaResponseDto> getTarefaPorId(@PathVariable Integer id) {
        TarefaResponseDto tarefa = TarefaDtoFactory.canonicoParaDto(service.buscaTarefaPorId(id));
        return ResponseEntity.ok(tarefa);
    }

    // --- 2. CRIAR TAREFA ---
    @Operation(summary = "Criar nova Tarefa", description = "Cria uma nova tarefa e a associa a um bloco existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Bloco associado não encontrado")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TarefaResponseDto> criarTarefa(@RequestBody TarefaRequestDto dto) {
        TarefaCanonical created = service.criarTarefa(TarefaDtoFactory.dtoToCanonical(dto));
        return ResponseEntity.ok(TarefaDtoFactory.canonicoParaDto(created));
    }

    // --- 3. ATUALIZAR TAREFA ---
    @Operation(summary = "Atualizar Tarefa", description = "Atualiza os detalhes (título, descrição, etc.) de uma tarefa existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TarefaResponseDto> atualizarTarefa(@PathVariable Integer id,
                                                             @RequestBody TarefaRequestDto dto) {
        TarefaCanonical canonical = TarefaDtoFactory.dtoToCanonical(dto);
        canonical.setIdTarefa(id);

        TarefaCanonical atualizado = service.atualizarTarefa(canonical);
        return ResponseEntity.ok(TarefaDtoFactory.canonicoParaDto(atualizado));
    }

    // --- 4. DELETAR TAREFA ---
    @Operation(summary = "Excluir Tarefa", description = "Remove permanentemente uma tarefa pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tarefa excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Integer id) {
        service.deletarTarefa(id);
        return ResponseEntity.noContent().build();
    }
}