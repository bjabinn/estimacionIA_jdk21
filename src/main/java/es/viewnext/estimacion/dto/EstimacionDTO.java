package es.viewnext.estimacion.dto;

import lombok.Data;

import java.util.List;

@Data
public class EstimacionDTO {
    private Long proyectoId;
    private Long sprintId;
    private Long tareaId;
    private String owner;
    private String notas;
    //private Long promptId;
    private List<MedicionPorPromptDTO> medicionesPorPrompt;
}
