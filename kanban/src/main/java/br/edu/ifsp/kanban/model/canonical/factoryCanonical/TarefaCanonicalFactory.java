package br.edu.ifsp.kanban.model.canonical.factoryCanonical;

import br.edu.ifsp.kanban.model.canonical.BlocoCanonical;
import br.edu.ifsp.kanban.model.canonical.TarefaCanonical;
import br.edu.ifsp.kanban.model.entity.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaCanonicalFactory {
    public static TarefaCanonical entityToCanonical(Tarefa entity) {
        if (entity == null) return null;

        TarefaCanonical canonical = new TarefaCanonical();
        canonical.setIdTarefa(entity.getIdTarefa());
        canonical.setIdBloco(entity.getIdBloco());
        canonical.setTexto(entity.getTexto());
        canonical.setEstado(entity.isEstado());

        // Evitar recursão infinita: só carrega o bloco se necessário
        if (entity.getBloco() != null) {
            canonical.setBloco(new BlocoCanonical());
            canonical.getBloco().setIdBloco(entity.getBloco().getIdBloco());
            canonical.getBloco().setNome(entity.getBloco().getNome());
        }
        return canonical;
    }

    public static Tarefa canonicalToEntity(TarefaCanonical canonical) {
        if (canonical == null) return null;

        Tarefa tarefa = new Tarefa();
        tarefa.setIdTarefa(canonical.getIdTarefa());
        tarefa.setIdBloco(canonical.getIdBloco());
        tarefa.setTexto(canonical.getTexto());
        tarefa.setEstado(canonical.isEstado());
        return tarefa;
    }

    public static List<TarefaCanonical> entityListToCanonicalList(List<Tarefa> entities) {
        if (entities == null) return null;

        List<TarefaCanonical> list = new ArrayList<>();
        for (Tarefa entity : entities) {
            list.add(entityToCanonical(entity));
        }
        return list;
    }

    public static List<Tarefa> canonicalListToEntityList(List<TarefaCanonical> canonicals){
        if (canonicals == null) return null;

        List<Tarefa> list = new ArrayList<>();
        for (TarefaCanonical c : canonicals) {
            list.add(canonicalToEntity(c));
        }
        return list;
    }
}
