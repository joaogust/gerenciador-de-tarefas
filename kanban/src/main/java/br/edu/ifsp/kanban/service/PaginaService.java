package br.edu.ifsp.kanban.service;

import br.edu.ifsp.kanban.model.canonical.PaginaCanonical;
import br.edu.ifsp.kanban.model.canonical.factoryCanonical.PaginaCanonicalFactory;
import br.edu.ifsp.kanban.model.entity.Pagina;
import br.edu.ifsp.kanban.repository.PaginaRepository;
import br.edu.ifsp.kanban.repository.PaginaUsuarioRepository; // NOVO: Import necessário
import br.edu.ifsp.kanban.repository.BlocoRepository; // ASSUMIDO: Novo import
import br.edu.ifsp.kanban.repository.TarefaRepository; // ASSUMIDO: Novo import
import br.edu.ifsp.kanban.utils.Validator;
import jakarta.transaction.Transactional; // NOVO: Import necessário
import org.springframework.stereotype.Service;

@Service
public class PaginaService {

    private final PaginaRepository repository;
    private final PaginaUsuarioRepository paginaUsuarioRepository; // NOVO: Repositório para dependência
    private final BlocoRepository blocoRepository; // ASSUMIDO: Para remover Blocos e Tarefas
    private final TarefaRepository tarefaRepository; // ASSUMIDO: Para remover Tarefas

    public PaginaService(
            PaginaRepository repository,
            PaginaUsuarioRepository paginaUsuarioRepository,
            BlocoRepository blocoRepository,
            TarefaRepository tarefaRepository
    ) {
        this.repository = repository;
        this.paginaUsuarioRepository = paginaUsuarioRepository;
        this.blocoRepository = blocoRepository;
        this.tarefaRepository = tarefaRepository;
    }

    public Pagina buscaPorId(Integer idPagina) {
        // Assume-se que este método existe no PaginaRepository para carregar tudo
        return repository.findByIdWithBlocosAndTarefas(idPagina)
                .orElse(null);
    }

    public PaginaCanonical buscaPaginaPorId(Integer idPagina) {
        Validator.erroSeNulo(idPagina, "Um id deve ser informado");

        Pagina pagina = buscaPorId(idPagina);
        Validator.erroSeNulo(pagina, "Página não encontrada");

        return PaginaCanonicalFactory.entityToCanonical(pagina);
    }

    public PaginaCanonical criarPagina(PaginaCanonical novaPagina) {
        Validator.erroSeNulo(novaPagina, "Dados insuficientes");
        Validator.erroSeNulo(novaPagina.getNome(), "Nome é obrigatório");

        Pagina entity = PaginaCanonicalFactory.canonicalToEntity(novaPagina);
        Pagina salvo = repository.save(entity);

        return PaginaCanonicalFactory.entityToCanonical(salvo);
    }

    public PaginaCanonical atualizarPagina(PaginaCanonical atualizada) {
        Validator.erroSeNulo(atualizada, "Nenhum campo enviado");
        Validator.erroSeNulo(atualizada.getIdPagina(), "Id é obrigatório");

        Pagina pagina = buscaPorId(atualizada.getIdPagina());
        Validator.erroSeNulo(pagina, "Página não encontrada");

        Validator.atribuirSeNaoNulo(atualizada.getNome(), pagina::setNome);

        Pagina salvo = repository.save(pagina);

        return PaginaCanonicalFactory.entityToCanonical(salvo);
    }

    @Transactional // Garante que as operações sejam atômicas (ou tudo, ou nada)
    public void deletarPagina(Integer id) {
        Pagina pagina = buscaPorId(id);
        Validator.erroSeNulo(pagina, "Página não encontrada");

        paginaUsuarioRepository.deleteByIdPagina(id);

        // 3. Excluir a Página principal
        repository.delete(pagina);
    }
}