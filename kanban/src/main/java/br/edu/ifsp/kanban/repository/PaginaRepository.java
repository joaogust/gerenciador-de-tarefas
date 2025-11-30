package br.edu.ifsp.kanban.repository;

import br.edu.ifsp.kanban.model.entity.Pagina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaginaRepository extends JpaRepository<Pagina, Integer> {
}
