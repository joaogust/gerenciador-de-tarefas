package br.edu.ifsp.kanban.model.canonical.factoryCanonical;

import br.edu.ifsp.kanban.model.canonical.UsuarioCanonical;
import br.edu.ifsp.kanban.model.entity.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioCanonicalFactory {
    public static UsuarioCanonical entityToCanonico(Usuario entity) {
        if (entity == null) return null;

        UsuarioCanonical c = new UsuarioCanonical();
        c.setIdUsuario(entity.getIdUsuario());
        c.setNome(entity.getNome());
        c.setEmail(entity.getEmail());
        c.setSenha(entity.getSenha());

        if (entity.getPaginas() != null) {
            c.setPaginas(PaginaUsuarioCanonicalFactory.entityListToCanonicalList(entity.getPaginas()));
        }

        return c;
    }

    public static Usuario toEntity(UsuarioCanonical c) {
        if (c == null) return null;

        Usuario e = new Usuario();
        e.setIdUsuario(c.getIdUsuario());
        e.setNome(c.getNome());
        e.setEmail(c.getEmail());
        e.setSenha(c.getSenha());
        return e;
    }

    public static List<UsuarioCanonical> entityListToCanonicoList(List<Usuario> entities) {
        if (entities == null) return null;
        List<UsuarioCanonical> list = new ArrayList<>();
        for (Usuario e : entities) list.add(entityToCanonico(e));
        return list;
    }

    public static List<Usuario> canonicalListToEntityList(List<UsuarioCanonical> canonicals) {
        if (canonicals == null) return null;
        List<Usuario> list = new ArrayList<>();
        for (UsuarioCanonical c : canonicals) list.add(toEntity(c));
        return list;
    }
}
