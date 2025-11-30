package br.edu.ifsp.kanban.service;

import br.edu.ifsp.kanban.controller.dto.request.PaginaUsuarioRequestDto;
import br.edu.ifsp.kanban.controller.dto.response.PaginaUsuarioResponseDto;
import br.edu.ifsp.kanban.model.canonical.PaginaUsuarioCanonical;
import br.edu.ifsp.kanban.model.canonical.factoryCanonical.PaginaUsuarioCanonicalFactory;
import br.edu.ifsp.kanban.model.entity.PaginaUsuario;
import br.edu.ifsp.kanban.model.entity.PaginaUsuarioId;
import br.edu.ifsp.kanban.model.entity.Usuario;
import br.edu.ifsp.kanban.repository.PaginaRepository;
import br.edu.ifsp.kanban.repository.PaginaUsuarioRepository;
import br.edu.ifsp.kanban.repository.UsuarioRepository;
import br.edu.ifsp.kanban.controller.dto.factoryDto.PaginaUsuarioDtoFactory;
import br.edu.ifsp.kanban.utils.Validator;
import org.springframework.stereotype.Service;

@Service
public class PaginaUsuarioService {

    private final PaginaUsuarioRepository paginaUsuarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final PaginaRepository paginaRepository;

    public PaginaUsuarioService(PaginaUsuarioRepository paginaUsuarioRepository,
                                UsuarioRepository usuarioRepository,
                                PaginaRepository paginaRepository) {
        this.paginaUsuarioRepository = paginaUsuarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.paginaRepository = paginaRepository;
    }

    public PaginaUsuarioResponseDto adicionarUsuario(PaginaUsuarioRequestDto request, Integer idPagina) {
        Validator.erroSeNulo(request.getEmail(), "Email do usuário é obrigatório");
        Validator.erroSeNulo(idPagina, "Página é obrigatória");

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Validator.erroSeNulo(usuario.getIdUsuario(), "Usuário sem ID");

        var pagina = paginaRepository.findById(idPagina)
                .orElseThrow(() -> new RuntimeException("Página não encontrada"));

        PaginaUsuarioId id = new PaginaUsuarioId(idPagina, usuario.getIdUsuario());
        if (paginaUsuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário já vinculado a esta página");
        }

        PaginaUsuario pu = new PaginaUsuario();
        pu.setIdPaginaUsuario(id);
        pu.setUsuario(usuario);
        pu.setPagina(pagina);
        pu.setPapel(request.getPapel());

        PaginaUsuario salvo = paginaUsuarioRepository.save(pu);
        PaginaUsuarioCanonical canonical = PaginaUsuarioCanonicalFactory.entityToCanonical(salvo);

        return PaginaUsuarioDtoFactory.canonicoParaDto(canonical);
    }

    public PaginaUsuarioResponseDto alterarPapel(PaginaUsuarioRequestDto request, Integer idPagina) {
        Validator.erroSeNulo(request.getEmail(), "Email do usuário é obrigatório");
        Validator.erroSeNulo(idPagina, "Página é obrigatória");

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        PaginaUsuarioId id = new PaginaUsuarioId(idPagina, usuario.getIdUsuario());
        PaginaUsuario pu = paginaUsuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não vinculado a esta página"));

        pu.setPapel(request.getPapel());

        PaginaUsuario salvo = paginaUsuarioRepository.save(pu);
        PaginaUsuarioCanonical canonical = PaginaUsuarioCanonicalFactory.entityToCanonical(salvo);

        return PaginaUsuarioDtoFactory.canonicoParaDto(canonical);
    }

    public void removerUsuario(String email, Integer idPagina) {
        Validator.erroSeNulo(email, "Email do usuário é obrigatório");
        Validator.erroSeNulo(idPagina, "Página é obrigatória");

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        PaginaUsuarioId id = new PaginaUsuarioId(idPagina, usuario.getIdUsuario());
        PaginaUsuario pu = paginaUsuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não vinculado a esta página"));

        paginaUsuarioRepository.delete(pu);
    }

    public PaginaUsuarioResponseDto getPaginaUsuario(Integer idPagina, Integer idUsuario) {
        PaginaUsuarioId id = new PaginaUsuarioId(idPagina, idUsuario);
        PaginaUsuario pu = paginaUsuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não vinculado a esta página"));

        PaginaUsuarioCanonical canonical = PaginaUsuarioCanonicalFactory.entityToCanonical(pu);
        return PaginaUsuarioDtoFactory.canonicoParaDto(canonical);
    }
}
