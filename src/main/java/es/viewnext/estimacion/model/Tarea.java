package es.viewnext.estimacion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "sprint_id", nullable = false)
    @JsonIgnore
    private Sprint sprint;
}
