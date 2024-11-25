package es.viewnext.estimacion.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Sprint {
    @Id
    @GeneratedValue (strategy =GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToOne
    @JoinColumn (name = "proyecto_id", nullable = false)
    private Proyecto proyecto;

    @OneToMany (mappedBy = "sprint", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarea> tareas = new ArrayList<>();

}
