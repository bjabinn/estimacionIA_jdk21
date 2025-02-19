package es.viewnext.estimacion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Estimacion extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tarea_id")
    @JsonIgnore
    private Tarea tarea;

    private String owner;

    @Column(columnDefinition = "text")
    private String notas;

    @OneToMany(mappedBy = "estimacion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MedicionPorPrompt> medicionPorPrompt;
}
