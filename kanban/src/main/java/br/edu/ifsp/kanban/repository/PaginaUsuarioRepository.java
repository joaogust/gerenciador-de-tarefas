package br.edu.ifsp.kanban.repository;

import br.edu.ifsp.kanban.model.entity.PaginaUsuario;
import br.edu.ifsp.kanban.model.entity.PaginaUsuarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaginaUsuarioRepository extends JpaRepository<PaginaUsuario, PaginaUsuarioId> {
    //List<PaginaUsuario> findByPaginaId(Integer paginaId);

    //List<PaginaUsuario> findByUsuarioId(Integer usuarioId);
}
