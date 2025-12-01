package br.edu.ifsp.kanban.controller.resource;

import br.edu.ifsp.kanban.controller.dto.request.UsuarioRequestDto;
import br.edu.ifsp.kanban.controller.dto.response.UsuarioResponseDto;
import br.edu.ifsp.kanban.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
// 1. Adiciona a Tag para agrupar e nomear o Controller no Swagger UI
@Tag(name = "Usuários", description = "Operações de CRUD e consultas específicas para usuários.")
public class UsuarioController {

    private final UsuarioService usuarioService;

    // --- 1. CRIAR USUÁRIO ---
    @Operation(summary = "Criar novo Usuário", description = "Registra um novo usuário no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou e-mail já cadastrado")
    })
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> criar(@RequestBody UsuarioRequestDto dto) {
        // Retorna 201 Created para criação bem-sucedida (boa prática)
        return new ResponseEntity<>(
                usuarioService.criar(dto),
                HttpStatus.CREATED
        );
    }

    // --- 2. BUSCAR POR ID ---
    @Operation(summary = "Buscar Usuário por ID", description = "Retorna os detalhes de um usuário específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    // --- 3. BUSCAR POR EMAIL ---
    @Operation(summary = "Buscar Usuário por E-mail", description = "Retorna os detalhes de um usuário buscando pelo seu endereço de e-mail.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioResponseDto> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(usuarioService.buscarPorEmail(email));
    }

    // --- 4. ATUALIZAR USUÁRIO ---
    @Operation(summary = "Atualizar Usuário", description = "Atualiza informações (como nome ou senha) de um usuário existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> atualizar(@PathVariable Integer id,
                                                        @RequestBody UsuarioRequestDto dto) {
        return ResponseEntity.ok(usuarioService.atualizar(id, dto));
    }

    // --- 5. DELETAR USUÁRIO ---
    @Operation(summary = "Excluir Usuário", description = "Remove permanentemente um usuário do sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso (No Content)"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        usuarioService.deletar(id);
        // Retorna 204 No Content para exclusão bem-sucedida (boa prática)
        return ResponseEntity.noContent().build();
    }
}