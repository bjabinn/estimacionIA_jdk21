package es.viewnext.estimacion.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class MedicionPorPrompt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "prompt_id")
    private Prompt prompt;

    private String aplicaIa;
    private String usadaIa;
    private String calidadSalidaIa;
    private int estimacionSinIa;
    private int estimacionConIa;

    @ManyToOne
    @JoinColumn(name = "estimacion_id")
    private Estimacion estimacion;
}
