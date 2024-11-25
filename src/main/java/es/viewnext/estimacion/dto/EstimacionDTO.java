package es.viewnext.estimacion.dto;

import lombok.Data;

@Data
public class EstimacionDTO {
    private Long proyectoId;
    private Long sprintId;
    private Long tareaId;
    private String historiaJira;
    private String owner;
}
