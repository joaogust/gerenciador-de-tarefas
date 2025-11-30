package br.edu.ifsp.kanban.repository;

import br.edu.ifsp.kanban.model.entity.PaginaUsuario;
import br.edu.ifsp.kanban.model.entity.PaginaUsuarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaginaUsuarioRepository extends JpaRepository<PaginaUsuario, PaginaUsuarioId> {
    Optional<PaginaUsuario> findByIdPaginaAndIdUsuario(Integer idPagina, Integer idUsuario);

    List<PaginaUsuario> findByIdPagina(Integer idPagina);
}

