package br.edu.ifsp.kanban.service;

import br.edu.ifsp.kanban.model.canonical.PaginaCanonical;
import br.edu.ifsp.kanban.model.canonical.factoryCanonical.PaginaCanonicalFactory;
import br.edu.ifsp.kanban.model.entity.Pagina;
import br.edu.ifsp.kanban.repository.PaginaRepository;
import br.edu.ifsp.kanban.utils.Validator;
import org.springframework.stereotype.Service;

@Service
public class PaginaService {

    private final PaginaRepository repository;

    public PaginaService(PaginaRepository repository) {
        this.repository = repository;
    }
    public Pagina buscaPorId(Integer idPagina) {
        return repository.findById(idPagina).orElse(null);
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

    public void deletarPagina(Integer id) {
        Pagina pagina = buscaPorId(id);
        Validator.erroSeNulo(pagina, "Página não encontrada");
        repository.deleteById(id);
    }
}
