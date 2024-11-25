package es.viewnext.estimacion.controller;

import es.viewnext.estimacion.dto.EstimacionDTO;
import es.viewnext.estimacion.model.Estimacion;
import es.viewnext.estimacion.model.Proyecto;
import es.viewnext.estimacion.model.Sprint;
import es.viewnext.estimacion.model.Tarea;
import es.viewnext.estimacion.service.EstimacionService;
import es.viewnext.estimacion.service.ProyectoService;
import es.viewnext.estimacion.service.SprintService;
import es.viewnext.estimacion.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estimacion")
public class EstimacionController {

    @Autowired
    private EstimacionService estimacionService;

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private SprintService sprintService;

    @Autowired
    private TareaService tareaService;

    @GetMapping
    public List<Estimacion> getAllEstimaciones() {
        return estimacionService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Estimacion> getEstimacionById(@PathVariable Long id) {
        return estimacionService.findById(id);
    }

    @PostMapping
    public Estimacion createEstimacion(@RequestBody EstimacionDTO estimacionDTO) {
        Estimacion estimacion = new Estimacion();
        estimacion.setHistoriaJira(estimacionDTO.getHistoriaJira());
        estimacion.setOwner(estimacionDTO.getOwner());

        Optional<Proyecto> proyecto = proyectoService.findById(estimacionDTO.getProyectoId());
        Optional<Sprint> sprint = sprintService.findById(estimacionDTO.getSprintId());
        Optional<Tarea> tarea = tareaService.findById(estimacionDTO.getTareaId());

        proyecto.ifPresent(estimacion::setProyecto);
        sprint.ifPresent(estimacion::setSprint);
        tarea.ifPresent(estimacion::setTarea);

        return estimacionService.save(estimacion);
    }

    @PutMapping("/{id}")
    public Estimacion updateEstimacion(@PathVariable Long id, @RequestBody Estimacion estimacion) {
        if (estimacionService.findById(id).isPresent()) {
            estimacion.setId(id);
            return estimacionService.save(estimacion);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteEstimacion(@PathVariable Long id) {
        estimacionService.delete(id);
    }
}
