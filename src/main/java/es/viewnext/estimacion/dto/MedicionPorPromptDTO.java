package es.viewnext.estimacion.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MedicionPorPromptDTO {
    private Long id;
    private boolean usadaIa;
    private BigDecimal calidadSalidaIa;
    private BigDecimal estimacionSinIa;
    private BigDecimal estimacionConIa;

    private Long promptId;
    private String promptNombre;
}