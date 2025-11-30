package br.edu.ifsp.kanban.service;

import br.edu.ifsp.kanban.model.canonical.TarefaCanonical;
import br.edu.ifsp.kanban.model.canonical.BlocoCanonical;
import br.edu.ifsp.kanban.model.canonical.factoryCanonical.TarefaCanonicalFactory;
import br.edu.ifsp.kanban.model.entity.Tarefa;
import br.edu.ifsp.kanban.repository.TarefaRepository;
import br.edu.ifsp.kanban.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository repository;

    @Autowired
    private BlocoService blocoService;

    public TarefaCanonical buscaTarefaPorId(Integer idTarefa) {
        return repository.findById(idTarefa)
                .map(TarefaCanonicalFactory::entityToCanonical)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada."));
    }

    public TarefaCanonical criarTarefa(TarefaCanonical nova) {

        Validator.erroSeNulo(nova.getTexto(), "Texto é obrigatório.");
        Validator.erroSeNulo(nova.getIdBloco(), "Bloco é obrigatório.");

        blocoService.buscaBlocoPorId(nova.getIdBloco());

        Tarefa saved = repository.save(TarefaCanonicalFactory.canonicalToEntity(nova));
        return TarefaCanonicalFactory.entityToCanonical(saved);
    }

    public TarefaCanonical atualizarTarefa(TarefaCanonical atualizada) {

        Validator.erroSeNulo(atualizada.getIdTarefa(), "ID da tarefa é obrigatório.");

        TarefaCanonical existente = buscaTarefaPorId(atualizada.getIdTarefa());

        if (atualizada.getTexto() != null)
            existente.setTexto(atualizada.getTexto());

        // apenas se front enviar estado explicitamente
        existente.setEstado(atualizada.isEstado());

        if (atualizada.getIdBloco() != null) {
            blocoService.buscaBlocoPorId(atualizada.getIdBloco());
            existente.setIdBloco(atualizada.getIdBloco());
        }

        Tarefa entitySaved = repository.save(TarefaCanonicalFactory.canonicalToEntity(existente));
        return TarefaCanonicalFactory.entityToCanonical(entitySaved);
    }

    public void deletarTarefa(Integer idTarefa) {
        buscaTarefaPorId(idTarefa); // valida
        repository.deleteById(idTarefa);
    }
}
