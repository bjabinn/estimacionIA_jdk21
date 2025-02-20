package es.viewnext.estimacion.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class MedicionPorPrompt extends Auditable{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @JsonIgnore
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prompt_id") @JsonIgnore @JsonBackReference
    private Prompt prompt;

    private boolean usadaIa;
    private BigDecimal calidadSalidaIa;
    private BigDecimal estimacionSinIa;
    private BigDecimal estimacionConIa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estimacion_id") @JsonIgnore @JsonBackReference
    private Estimacion estimacion;
}
