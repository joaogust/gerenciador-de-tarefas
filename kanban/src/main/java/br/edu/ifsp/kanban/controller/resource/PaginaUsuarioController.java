package br.edu.ifsp.kanban.controller.resource;

import br.edu.ifsp.kanban.controller.dto.request.PaginaUsuarioRequestDto;
import br.edu.ifsp.kanban.controller.dto.response.PaginaUsuarioResponseDto;
import br.edu.ifsp.kanban.service.PaginaUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paginas/{idPagina}/usuarios")
@RequiredArgsConstructor
public class PaginaUsuarioController {

    private final PaginaUsuarioService service;

    @GetMapping(value = "/{idUsuario}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaginaUsuarioResponseDto> getPaginaUsuario(
            @PathVariable Integer idPagina,
            @PathVariable Integer idUsuario) {

        PaginaUsuarioResponseDto dto = service.getPaginaUsuario(idPagina, idUsuario);
        return ResponseEntity.ok(dto);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaginaUsuarioResponseDto> adicionarUsuario(
            @PathVariable Integer idPagina,
            @RequestBody PaginaUsuarioRequestDto request) {

        PaginaUsuarioResponseDto dto = service.adicionarUsuario(request, idPagina);
        return ResponseEntity.ok(dto);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaginaUsuarioResponseDto> alterarPapel(
            @PathVariable Integer idPagina,
            @RequestBody PaginaUsuarioRequestDto request) {

        PaginaUsuarioResponseDto dto = service.alterarPapel(request, idPagina);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> removerUsuario(
            @PathVariable Integer idPagina,
            @RequestBody PaginaUsuarioRequestDto request) {

        service.removerUsuario(request.getEmail(), idPagina);
        return ResponseEntity.noContent().build();
    }
}
