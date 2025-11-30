package br.edu.ifsp.kanban.repository;

import br.edu.ifsp.kanban.model.entity.Pagina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaginaRepository extends JpaRepository<Pagina, Integer> {

    @Query("select distinct p from Pagina p " +
            "left join fetch p.blocos b " +
            "left join fetch b.tarefas t " +
            "where p.idPagina = :id")
    Optional<Pagina> findByIdWithBlocosAndTarefas(@Param("id") Integer id);
}
