package br.edu.ifsp.kanban.service;

import br.edu.ifsp.kanban.model.canonical.TarefaCanonical;
import br.edu.ifsp.kanban.model.canonical.factoryCanonical.TarefaCanonicalFactory;
import br.edu.ifsp.kanban.model.entity.Tarefa;
import br.edu.ifsp.kanban.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TarefaService {
    private final TarefaRepository repository;

    public TarefaService(TarefaRepository repository) {
        this.repository = repository;
    }

    public Tarefa buscaPorId(Integer idTarefa) {
        Optional<Tarefa> tarefa = repository.findById(idTarefa);
        return tarefa.orElse(null);
    }

    public TarefaCanonical buscaTarefaPorId(Integer idTarefa) {
        return TarefaCanonicalFactory.entityToCanonical(buscaPorId(idTarefa));
    }
}
