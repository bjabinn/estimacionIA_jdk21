package es.viewnext.estimacion.repository;

import es.viewnext.estimacion.model.MedicionPorPrompt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicionPorPromptRepository extends JpaRepository<MedicionPorPrompt, Long> {
}
