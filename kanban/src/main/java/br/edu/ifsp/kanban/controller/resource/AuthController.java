package br.edu.ifsp.kanban.controller.resource;

import br.edu.ifsp.kanban.controller.dto.request.AuthRequestDto;
import br.edu.ifsp.kanban.controller.dto.response.AuthResponseDto;
import br.edu.ifsp.kanban.model.entity.Usuario;
import br.edu.ifsp.kanban.repository.UsuarioRepository;
import br.edu.ifsp.kanban.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Operações de Login para gerar o Token JWT.") // Adiciona o Tag/Nome da seção
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;

    @Operation(summary = "Realizar Login", description = "Autentica o usuário com email e senha e retorna um Token JWT.") // Descrição do método
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login bem-sucedido. Retorna o Token JWT."),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas (Usuário ou senha inválidos).") // 401 é mais apropriado para credenciais inválidas
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto dto) {
        // ... (Sua lógica de autenticação) ...
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário ou senha inválidos"));

        if (!usuario.getSenha().equals(dto.getSenha())) {
            // Em um ambiente de produção, esta exceção deveria ser tratada para retornar 401
            throw new RuntimeException("Usuário ou senha inválidos");
        }

        String token = jwtUtil.gerarToken(usuario.getEmail());
        return ResponseEntity.ok(new AuthResponseDto(token));
    }
}