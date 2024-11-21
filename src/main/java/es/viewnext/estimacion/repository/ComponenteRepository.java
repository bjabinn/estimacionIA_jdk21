package es.viewnext.estimacion.repository;

import es.viewnext.estimacion.model.Componente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponenteRepository extends JpaRepository<Componente, Long> {
}
