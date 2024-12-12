package es.viewnext.estimacion.dto;

import lombok.Data;

@Data
public class PromptDTO {
    private Long id;
    private String prompt;
    private Long proyectoId;
}
