package br.edu.ifsp.kanban.repository;

import br.edu.ifsp.kanban.model.entity.Bloco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlocoRepository extends JpaRepository<Bloco, Integer> {
    //List<Bloco> findByPaginaId(Integer paginaId);
}
