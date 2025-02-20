package es.viewnext.estimacion.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface MedicionesExcelDTO {
    LocalDateTime getFechaCreacion();
    String getNombreProyecto();
    String getNombreSprint();
    String getDescripcionTarea();
    String getOwner();
    boolean isUsadaIa();
    String getPrompt();
    BigDecimal getCalidadSalidaIa();
    BigDecimal getEstimacionConIa();
    BigDecimal getEstimacionSinIa();
    String getNotas();
}
