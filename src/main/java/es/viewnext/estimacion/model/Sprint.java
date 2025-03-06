package es.viewnext.estimacion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Sprint extends Auditable{
    @Id
    @GeneratedValue (strategy =GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "proyecto_id")
    private Proyecto proyecto;

    @OneToMany (cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "sprint_id")
    private List<Tarea> tareas;

}
