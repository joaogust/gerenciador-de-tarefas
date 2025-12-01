package br.edu.ifsp.kanban.controller.resource;

import br.edu.ifsp.kanban.controller.dto.request.PaginaUsuarioRequestDto;
import br.edu.ifsp.kanban.controller.dto.response.PaginaUsuarioResponseDto;
import br.edu.ifsp.kanban.service.PaginaUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paginas/{idPagina}/usuarios")
@RequiredArgsConstructor
@Tag(name = "Página Usuário (Permissões)", description = "Gerenciamento de usuários e suas permissões em páginas (quadros) específicos.")
public class PaginaUsuarioController {

    private final PaginaUsuarioService service;

    // --- 1. BUSCAR USUÁRIO NA PÁGINA ---
    @Operation(summary = "Buscar Usuário na Página", description = "Retorna os detalhes de um usuário específico em uma determinada página, incluindo seu papel.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário na página encontrado"),
            @ApiResponse(responseCode = "404", description = "Página ou usuário não encontrado, ou usuário não associado à página")
    })
    @GetMapping(value = "/{idUsuario}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaginaUsuarioResponseDto> getPaginaUsuario(
            @PathVariable Integer idPagina,
            @PathVariable Integer idUsuario) {

        PaginaUsuarioResponseDto dto = service.getPaginaUsuario(idPagina, idUsuario);
        return ResponseEntity.ok(dto);
    }

    // --- 2. ADICIONAR NOVO USUÁRIO ---
    @Operation(summary = "Adicionar Usuário", description = "Adiciona um novo usuário (via email) a uma página, concedendo o papel inicial.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário adicionado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos, usuário já na página ou sem permissão para adicionar"),
            @ApiResponse(responseCode = "404", description = "Página ou email de usuário não encontrado")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaginaUsuarioResponseDto> adicionarUsuario(
            @PathVariable Integer idPagina,
            @RequestBody PaginaUsuarioRequestDto request) {

        PaginaUsuarioResponseDto dto = service.adicionarUsuario(request, idPagina);
        return ResponseEntity.ok(dto);
    }

    // --- 3. ALTERAR PAPEL/PERMISSÃO ---
    @Operation(summary = "Alterar Papel do Usuário", description = "Modifica o papel (permissão) de um usuário já associado à página.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Papel do usuário alterado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Tentativa de alterar papel de forma inválida (ex: owner)"),
            @ApiResponse(responseCode = "404", description = "Página ou usuário não encontrado")
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaginaUsuarioResponseDto> alterarPapel(
            @PathVariable Integer idPagina,
            @RequestBody PaginaUsuarioRequestDto request) {

        PaginaUsuarioResponseDto dto = service.alterarPapel(request, idPagina);
        return ResponseEntity.ok(dto);
    }

    // --- 4. REMOVER USUÁRIO ---
    @Operation(summary = "Remover Usuário", description = "Remove um usuário da página, revogando todas as suas permissões.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Página ou usuário não encontrado")
    })
    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> removerUsuario(
            @PathVariable Integer idPagina,
            @RequestBody PaginaUsuarioRequestDto request) {

        service.removerUsuario(request.getEmail(), idPagina);
        return ResponseEntity.noContent().build();
    }
}