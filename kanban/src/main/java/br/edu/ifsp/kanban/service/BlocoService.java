package br.edu.ifsp.kanban.service;

import br.edu.ifsp.kanban.model.canonical.BlocoCanonical;
import br.edu.ifsp.kanban.model.entity.Bloco;
import br.edu.ifsp.kanban.model.canonical.factoryCanonical.BlocoCanonicalFactory;
import br.edu.ifsp.kanban.model.entity.Pagina;
import br.edu.ifsp.kanban.repository.BlocoRepository;
import br.edu.ifsp.kanban.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlocoService {

    private final BlocoRepository repository;
    private final PaginaService paginaService;

    public BlocoService(BlocoRepository repository, PaginaService paginaService) {
        this.repository = repository;
        this.paginaService = paginaService;
    }

    public Bloco buscaPorId(Integer idBloco) {
        try {
            return repository.findById(idBloco).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar bloco.", e);
        }
    }

    public BlocoCanonical buscaBlocoPorId(Integer idBloco) {
        Validator.erroSeNulo(idBloco, "Um id deve ser informado");

        Bloco canonical = buscaPorId(idBloco);
        Validator.erroSeNulo(canonical, "Bloco não encontrado.");

        return BlocoCanonicalFactory.entityToCanonical(canonical);
    }

    public Bloco save(Bloco bloco) {
        try {
            return repository.save(bloco);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar bloco.", e);
        }
    }

    public void delete(Integer idBloco) {
        try {
            repository.deleteById(idBloco);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar bloco.", e);
        }
    }
    public BlocoCanonical criarBloco(BlocoCanonical novoBloco) {

        Validator.erroSeNulo(novoBloco, "Dados insuficientes para criar um bloco.");
        Validator.erroSeNulo(novoBloco.getNome(), "Nome é obrigatório.");
        Validator.erroSeNulo(novoBloco.getEstado(), "Estado é obrigatório.");
        Validator.erroSeNulo(novoBloco.getIdPagina(), "Uma página deve ser informada.");

        Pagina pagina = paginaService.buscaPorId(novoBloco.getIdPagina());
        Validator.erroSeNulo(pagina, "Página não encontrada.");

        Bloco blocoEntity = BlocoCanonicalFactory.canonicalToEntity(novoBloco);

        blocoEntity.setPagina(pagina);

        Bloco salvo = save(blocoEntity);

        return BlocoCanonicalFactory.entityToCanonical(salvo);
    }


    public BlocoCanonical atualizarBloco(BlocoCanonical atualizado) {

        Validator.erroSeNulo(atualizado, "Nenhum campo foi enviado para atualização.");
        Validator.erroSeNulo(atualizado.getIdBloco(), "Id do bloco é obrigatório.");

        BlocoCanonical bloco = buscaBlocoPorId(atualizado.getIdBloco());

        Validator.atribuirSeNaoNulo(atualizado.getNome(), bloco::setNome);
        Validator.atribuirSeNaoNulo(atualizado.getEstado(), bloco::setEstado);

        Bloco salvo = save(BlocoCanonicalFactory.canonicalToEntity(bloco));

        return BlocoCanonicalFactory.entityToCanonical(salvo);
    }


    public void deletarBloco(Integer idBloco) {
        BlocoCanonical bloco = buscaBlocoPorId(idBloco);
        if (bloco != null) {
            delete(idBloco);
        }
    }
}

