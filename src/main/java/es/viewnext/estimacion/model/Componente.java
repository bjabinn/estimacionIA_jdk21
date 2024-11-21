package es.viewnext.estimacion.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Componente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tarea;
    private String aplicaIa;
    private String usadaIa;
    private String calidadSalidaIa;
    private int estimacionSinIa;
    private int estimacionConIa;

    @ManyToOne
    @JoinColumn(name = "proyecto_id")
    private Proyecto proyecto;
}
