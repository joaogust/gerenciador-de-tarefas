package br.edu.ifsp.kanban.repository;

import br.edu.ifsp.kanban.model.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Integer> {
    @Modifying
    @Query("DELETE FROM Tarefa t WHERE t.bloco.idBloco = :idBloco")
    void deleteByBlocoId(@Param("idBloco") Integer idBloco);
}