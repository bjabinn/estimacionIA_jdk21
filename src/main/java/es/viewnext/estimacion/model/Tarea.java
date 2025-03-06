package es.viewnext.estimacion.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Tarea extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    @OneToOne(mappedBy = "tarea", cascade = CascadeType.ALL, orphanRemoval = true)
    private Estimacion estimacion;

    @ManyToOne
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;
}
