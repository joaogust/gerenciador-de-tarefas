package br.edu.ifsp.kanban.controller.resource;

import br.edu.ifsp.kanban.controller.dto.factoryDto.BlocoDtoFactory;
import br.edu.ifsp.kanban.controller.dto.request.BlocoRequestDto;
import br.edu.ifsp.kanban.controller.dto.response.BlocoResponseDto;
import br.edu.ifsp.kanban.model.canonical.BlocoCanonical;
import br.edu.ifsp.kanban.service.BlocoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bloco")
@Tag(name = "Blocos", description = "Operações para gerenciamento de colunas/blocos do Kanban")
public class BlocoController {

    private final BlocoService service;

    public BlocoController(BlocoService service) {
        this.service = service;
    }

    // --- 1. BUSCAR POR ID ---
    @Operation(summary = "Buscar Bloco por ID", description = "Retorna os detalhes de um bloco específico, incluindo suas tarefas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bloco encontrado"),
            @ApiResponse(responseCode = "404", description = "Bloco não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BlocoResponseDto> getBloco(@PathVariable Integer id) {
        BlocoResponseDto dto = BlocoDtoFactory
                .canonicoParaDto(service.buscaBlocoPorId(id));
        return ResponseEntity.ok(dto);
    }

    // --- 2. CRIAR BLOCO ---
    @Operation(summary = "Criar novo Bloco", description = "Cria uma nova coluna em uma página específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bloco criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Página associada não encontrada")
    })
    @PostMapping
    public ResponseEntity<BlocoResponseDto> criar(@RequestBody BlocoRequestDto dto) {
        BlocoCanonical criado = service.criarBloco(BlocoDtoFactory.dtoToCanonical(dto));
        BlocoResponseDto resposta = BlocoDtoFactory.canonicoParaDto(criado);
        return ResponseEntity.ok(resposta);
    }

    // --- 3. ATUALIZAR BLOCO ---
    @Operation(summary = "Atualizar Bloco", description = "Atualiza o nome ou estado de um bloco existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bloco atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Bloco não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BlocoResponseDto> atualizar(
            @PathVariable Integer id,
            @RequestBody BlocoRequestDto dto
    ) {
        BlocoCanonical canonical = BlocoDtoFactory.dtoToCanonical(dto);
        canonical.setIdBloco(id);

        BlocoCanonical atualizado = service.atualizarBloco(canonical);

        return ResponseEntity.ok(
                BlocoDtoFactory.canonicoParaDto(atualizado)
        );
    }

    // --- 4. DELETAR BLOCO ---
    @Operation(summary = "Excluir Bloco", description = "Remove permanentemente um bloco e suas tarefas associadas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Bloco excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Bloco não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletarBloco(id);
        return ResponseEntity.noContent().build();
    }
}