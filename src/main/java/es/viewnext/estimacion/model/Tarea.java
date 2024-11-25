package es.viewnext.estimacion.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "sprint_id", nullable = false)
    private Sprint sprint;
}
