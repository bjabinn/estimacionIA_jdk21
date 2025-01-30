package es.viewnext.estimacion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class MedicionPorPrompt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prompt_id")
    private Prompt prompt;

    private String usadaIa;
    private int calidadSalidaIa;
    private int estimacionSinIa;
    private int estimacionConIa;

    @ManyToOne
    @JoinColumn(name = "estimacion_id")
    private Estimacion estimacion;
}
