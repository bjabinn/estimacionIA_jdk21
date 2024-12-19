package es.viewnext.estimacion.dto;

import lombok.Data;

@Data
public class MedicionPorPromptDTO {
    //private Long promptId;
    private String aplicaIa;
    private String usadaIa;
    private int calidadSalidaIa;
    private int estimacionSinIa;
    private int estimacionConIa;
}