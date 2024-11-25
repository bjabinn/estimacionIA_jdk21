package es.viewnext.estimacion.repository;

import es.viewnext.estimacion.model.Estimacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstimacionRepository extends JpaRepository<Estimacion, Long> {
}
