package br.edu.ifsp.kanban.controller.resource;

import br.edu.ifsp.kanban.controller.dto.request.AuthRequestDto;
import br.edu.ifsp.kanban.controller.dto.response.AuthResponseDto;
import br.edu.ifsp.kanban.model.entity.Usuario;
import br.edu.ifsp.kanban.repository.UsuarioRepository;
import br.edu.ifsp.kanban.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário ou senha inválidos"));

        if (!usuario.getSenha().equals(dto.getSenha())) {
            throw new RuntimeException("Usuário ou senha inválidos");
        }

        String token = jwtUtil.gerarToken(usuario.getEmail());
        return ResponseEntity.ok(new AuthResponseDto(token));
    }
}
