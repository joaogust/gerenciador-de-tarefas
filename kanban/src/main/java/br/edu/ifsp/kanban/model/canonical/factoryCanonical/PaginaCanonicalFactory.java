package br.edu.ifsp.kanban.model.canonical.factoryCanonical;

import br.edu.ifsp.kanban.model.canonical.PaginaCanonical;
import br.edu.ifsp.kanban.model.entity.Pagina;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PaginaCanonicalFactory {
    public static PaginaCanonical entityToCanonical(Pagina entity) {
        if (entity == null) return null;

        PaginaCanonical c = new PaginaCanonical();
        c.setIdPagina(entity.getIdPagina());
        c.setNome(entity.getNome());

        if (entity.getBlocos() != null) {
            c.setBlocos(BlocoCanonicalFactory.entityListToCanonicalList(entity.getBlocos()));
        }

        return c;
    }

    public static Pagina canonicalToEntity(PaginaCanonical c) {
        if (c == null) return null;

        Pagina e = new Pagina();
        e.setIdPagina(c.getIdPagina());
        e.setNome(c.getNome());
        return e;
    }

    public static List<PaginaCanonical> entityListTooCanonicalList(Collection<Pagina> entities) {
        if (entities == null) return null;
        List<PaginaCanonical> list = new ArrayList<>();
        for (Pagina e : entities) list.add(entityToCanonical(e));
        return list;
    }

    public static List<Pagina> canonicalListToEntityList(List<PaginaCanonical> canonicals) {
        if (canonicals == null) return null;
        List<Pagina> list = new ArrayList<>();
        for (PaginaCanonical c : canonicals) list.add(canonicalToEntity(c));
        return list;
    }
}
