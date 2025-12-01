package br.edu.ifsp.kanban.controller.resource;

import br.edu.ifsp.kanban.controller.dto.factoryDto.PaginaDtoFactory;
import br.edu.ifsp.kanban.controller.dto.request.PaginaRequestDto;
import br.edu.ifsp.kanban.controller.dto.response.PaginaResponseDto;
import br.edu.ifsp.kanban.model.canonical.PaginaCanonical;
import br.edu.ifsp.kanban.service.PaginaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagina")
public class PaginaController {

    private final PaginaService service;

    public PaginaController(PaginaService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaginaResponseDto> getPagina(@PathVariable Integer id) {
        PaginaResponseDto dto =
                PaginaDtoFactory.canonicoParaDto(service.buscaPaginaPorId(id));

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PaginaResponseDto> criar(@RequestBody PaginaRequestDto dto) {
        var canonical = PaginaDtoFactory.dtoToCanonical(dto);
        var criado = service.criarPagina(canonical);

        var response = PaginaDtoFactory.canonicoParaDto(criado);

        return ResponseEntity
                .created(null)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaginaResponseDto> atualizar(
            @PathVariable Integer id,
            @RequestBody PaginaRequestDto dto
    ) {
        var canonical = PaginaDtoFactory.dtoToCanonical(dto);
        canonical.setIdPagina(id);

        var atualizado = service.atualizarPagina(canonical);

        var response = PaginaDtoFactory.canonicoParaDto(atualizado);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletarPagina(id);
        return ResponseEntity.noContent().build();
    }
}