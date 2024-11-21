package es.viewnext.estimacion.repository;

import es.viewnext.estimacion.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
}
