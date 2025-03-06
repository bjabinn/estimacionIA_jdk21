package es.viewnext.estimacion.dto;

import lombok.Data;

import java.util.List;

@Data
public class EstimacionDTO {
    private Long proyectoId;
    private String proyecto;

    private Long sprintId;
    private String sprint;

    private Long tareaId;
    private String tarea;

    private Long id;
    private String owner;
    private String notas;

    private List<MedicionPorPromptDTO> medicionesPorPrompt;




}
