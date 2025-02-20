package es.viewnext.estimacion.repository;

import es.viewnext.estimacion.dto.MedicionesExcelDTO;
import es.viewnext.estimacion.model.MedicionPorPrompt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MedicionesExcelRepository extends JpaRepository<MedicionPorPrompt, Long> {
    @Query("SELECT mpp.fechaCreacion AS fechaCreacion, " +
            "p.nombre AS nombreProyecto, s.nombre AS nombreSprint, " +
            "t.descripcion AS descripcionTarea, e.owner AS owner, mpp.usadaIa AS usadaIa, prmpt.prompt AS prompt, " +
            "mpp.calidadSalidaIa AS calidadSalidaIa, mpp.estimacionConIa AS estimacionConIa, " +
            "mpp.estimacionSinIa AS estimacionSinIa, e.notas AS notas " +
            "FROM MedicionPorPrompt mpp " +
            "INNER JOIN Prompt prmpt ON prmpt.id = mpp.prompt.id " +
            "INNER JOIN Proyecto p ON p.id = prmpt.proyecto.id " +
            "INNER JOIN Sprint s ON s.proyecto.id = p.id " +
            "INNER JOIN Tarea t ON t.sprint.id = s.id " +
            "INNER JOIN Estimacion e ON e.tarea.id = t.id")
    List<MedicionesExcelDTO> findAllMedicionesForExcel();
}
