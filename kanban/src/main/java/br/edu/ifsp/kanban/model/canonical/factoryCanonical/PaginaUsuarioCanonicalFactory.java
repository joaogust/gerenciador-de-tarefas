package br.edu.ifsp.kanban.model.canonical.factoryCanonical;

import br.edu.ifsp.kanban.model.canonical.PaginaCanonical;
import br.edu.ifsp.kanban.model.canonical.PaginaUsuarioCanonical;
import br.edu.ifsp.kanban.model.canonical.UsuarioCanonical;
import br.edu.ifsp.kanban.model.entity.PaginaUsuario;
import br.edu.ifsp.kanban.model.entity.PaginaUsuarioId;

import java.util.ArrayList;
import java.util.List;

public class PaginaUsuarioCanonicalFactory {
    public static PaginaUsuarioCanonical entityToCanonical(PaginaUsuario entity) {
        if (entity == null) return null;

        PaginaUsuarioCanonical c = new PaginaUsuarioCanonical();
        c.setIdPagina(entity.getIdPaginaUsuario().getIdPagina());
        c.setIdUsuario(entity.getIdPaginaUsuario().getIdUsuario());
        c.setPapel(entity.getPapel());

        if (entity.getUsuario() != null) {
            UsuarioCanonical u = new UsuarioCanonical();
            u.setIdUsuario(entity.getUsuario().getIdUsuario());
            u.setNome(entity.getUsuario().getNome());
            c.setUsuario(u);
        }

        if (entity.getPagina() != null) {
            PaginaCanonical p = new PaginaCanonical();
            p.setIdPagina(entity.getPagina().getIdPagina());
            p.setNome(entity.getPagina().getNome());
            c.setPagina(p);
        }

        return c;
    }

    public static PaginaUsuario canonicalToEntity(PaginaUsuarioCanonical c) {
        if (c == null) return null;

        PaginaUsuario entity = new PaginaUsuario();
        PaginaUsuarioId id = new PaginaUsuarioId(c.getIdPagina(), c.getIdUsuario());
        entity.setIdPaginaUsuario(id);
        entity.setPapel(c.getPapel());
        return entity;
    }

    public static List<PaginaUsuarioCanonical> entityListToCanonicalList(List<PaginaUsuario> entities) {
        if (entities == null) return null;
        List<PaginaUsuarioCanonical> list = new ArrayList<>();
        for (PaginaUsuario e : entities) list.add(entityToCanonical(e));
        return list;
    }

    public static List<PaginaUsuario> toEntityList(List<PaginaUsuarioCanonical> canonical) {
        if (canonical == null) return null;
        List<PaginaUsuario> list = new ArrayList<>();
        for (PaginaUsuarioCanonical c : canonical) list.add(canonicalToEntity(c));
        return list;
    }
}
