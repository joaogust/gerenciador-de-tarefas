package br.edu.ifsp.kanban.controller.resource;

import br.edu.ifsp.kanban.controller.dto.factoryDto.BlocoDtoFactory;
import br.edu.ifsp.kanban.controller.dto.request.BlocoRequestDto;
import br.edu.ifsp.kanban.controller.dto.response.BlocoResponseDto;
import br.edu.ifsp.kanban.model.canonical.BlocoCanonical;
import br.edu.ifsp.kanban.service.BlocoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bloco")
public class BlocoController {

    private final BlocoService service;

    public BlocoController(BlocoService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlocoResponseDto> getBloco(@PathVariable Integer id) {
        BlocoResponseDto dto = BlocoDtoFactory
                .canonicoParaDto(service.buscaBlocoPorId(id));
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<BlocoResponseDto> criar(@RequestBody BlocoRequestDto dto) {
        BlocoCanonical criado = service.criarBloco(BlocoDtoFactory.dtoToCanonical(dto));
        BlocoResponseDto resposta = BlocoDtoFactory.canonicoParaDto(criado);
        return ResponseEntity.ok(resposta);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletarBloco(id);
        return ResponseEntity.noContent().build();
    }
}

