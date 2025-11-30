package br.edu.ifsp.kanban.model.canonical.factoryCanonical;

import br.edu.ifsp.kanban.model.canonical.BlocoCanonical;
import br.edu.ifsp.kanban.model.canonical.PaginaCanonical;
import br.edu.ifsp.kanban.model.entity.Bloco;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BlocoCanonicalFactory {
    public static BlocoCanonical entityToCanonical(Bloco entity) {
        if (entity == null) return null;

        BlocoCanonical c = new BlocoCanonical();
        c.setIdBloco(entity.getIdBloco());
        c.setIdPagina(entity.getIdPagina());
        c.setNome(entity.getNome());
        c.setEstado(entity.getEstado());

        if (entity.getPagina() != null) {
            c.setPagina(new PaginaCanonical());
            c.getPagina().setIdPagina(entity.getPagina().getIdPagina());
            c.getPagina().setNome(entity.getPagina().getNome());
        }

        if (entity.getTarefas() != null) {
            c.setTarefas(TarefaCanonicalFactory.entityListToCanonicalList(entity.getTarefas()));
        }

        return c;
    }

    public static Bloco canonicalToEntity(BlocoCanonical c) {
        if (c == null) return null;

        Bloco b = new Bloco();
        b.setIdBloco(c.getIdBloco());
        b.setIdPagina(c.getIdPagina());
        b.setNome(c.getNome());
        b.setEstado(c.getEstado());
        return b;
    }

    public static List<BlocoCanonical> entityListToCanonicalList(Collection<Bloco> entities) {
        if (entities == null) return null;
        List<BlocoCanonical> list = new ArrayList<>();
        for (Bloco e : entities) list.add(entityToCanonical(e));
        return list;
    }

    public static List<Bloco> canonicalListToEntityList(List<BlocoCanonical> canonicals) {
        if (canonicals == null) return null;
        List<Bloco> list = new ArrayList<>();
        for (BlocoCanonical c : canonicals) list.add(canonicalToEntity(c));
        return list;
    }
}
