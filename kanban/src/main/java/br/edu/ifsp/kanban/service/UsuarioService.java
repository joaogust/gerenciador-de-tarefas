package br.edu.ifsp.kanban.service;

import br.edu.ifsp.kanban.controller.dto.factoryDto.UsuarioDtoFactory;
import br.edu.ifsp.kanban.controller.dto.request.UsuarioRequestDto;
import br.edu.ifsp.kanban.controller.dto.response.UsuarioResponseDto;
import br.edu.ifsp.kanban.model.canonical.UsuarioCanonical;
import br.edu.ifsp.kanban.model.canonical.factoryCanonical.UsuarioCanonicalFactory;
import br.edu.ifsp.kanban.model.entity.Usuario;
import br.edu.ifsp.kanban.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioResponseDto criar(UsuarioRequestDto dto) {

        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email já está em uso");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());

        usuarioRepository.save(usuario);

        return UsuarioDtoFactory.canonicoParaDto(
                UsuarioCanonicalFactory.entityToCanonico(usuario)
        );
    }

    public UsuarioResponseDto buscarPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return UsuarioDtoFactory.canonicoParaDto(
                UsuarioCanonicalFactory.entityToCanonico(usuario)
        );
    }

    public UsuarioResponseDto atualizar(Integer id, UsuarioRequestDto dto) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (dto.getEmail() != null && !dto.getEmail().equals(usuario.getEmail())) {
            if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
                throw new RuntimeException("Email já está em uso");
            }
            usuario.setEmail(dto.getEmail());
        }

        if (dto.getNome() != null) usuario.setNome(dto.getNome());
        if (dto.getSenha() != null) usuario.setSenha(dto.getSenha());

        usuarioRepository.save(usuario);

        return UsuarioDtoFactory.canonicoParaDto(
                UsuarioCanonicalFactory.entityToCanonico(usuario)
        );
    }

    public void deletar(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}
