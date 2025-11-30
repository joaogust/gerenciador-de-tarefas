package br.edu.ifsp.kanban.service;
/*
import br.edu.ifsp.kanban.controller.dto.factoryDto.UsuarioDtoFactory;
import br.edu.ifsp.kanban.controller.dto.request.LoginRequestDto;
import br.edu.ifsp.kanban.controller.dto.response.TokenResponseDto;
import br.edu.ifsp.kanban.controller.dto.response.UsuarioResponseDto;
import br.edu.ifsp.kanban.model.canonical.UsuarioCanonical;
import br.edu.ifsp.kanban.model.canonical.factoryCanonical.UsuarioCanonicalFactory;
import br.edu.ifsp.kanban.model.entity.Usuario;
import br.edu.ifsp.kanban.repository.UsuarioRepository;
import br.edu.ifsp.kanban.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;

    public TokenResponseDto login(LoginRequestDto dto) {

        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        UsuarioCanonical canonical = UsuarioCanonicalFactory.entityToCanonico(usuario);

        if (!canonical.getSenha().equals(dto.getSenha())) {
            throw new RuntimeException("Senha incorreta");
        }

        String token = jwtUtil.gerarToken(canonical.getEmail());

        return new TokenResponseDto(token);
    }

    public UsuarioResponseDto getMe(String email) {
        System.out.println("Teste de email nulo" + email);
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        UsuarioCanonical canonical = UsuarioCanonicalFactory.entityToCanonico(usuario);

        return UsuarioDtoFactory.canonicoParaDto(canonical);
    }
}
*/