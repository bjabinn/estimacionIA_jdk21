package es.viewnext.estimacion.controller;

import es.viewnext.estimacion.dto.SprintDTO;
import es.viewnext.estimacion.dto.TareaDTO;
import es.viewnext.estimacion.mapper.SprintMapper;
import es.viewnext.estimacion.mapper.TareaMapper;
import es.viewnext.estimacion.model.Proyecto;
import es.viewnext.estimacion.model.Sprint;
import es.viewnext.estimacion.model.Tarea;
import es.viewnext.estimacion.service.ProyectoService;
import es.viewnext.estimacion.service.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sprints")
public class SprintController {

    @Autowired
    private SprintService sprintService;

    @Autowired
    private ProyectoService proyectoService;

    @GetMapping
    public List<SprintDTO> getAllSprints() {
        List<Sprint> sprints = sprintService.findAll();
        return SprintMapper.INSTANCE.sprintsToSprintDTOs(sprints);
    }

    @GetMapping("/{id}")
    public Optional<Sprint> getSprintById(@PathVariable Long id) {
        return sprintService.findById(id);
    }

//    @PostMapping
//    public Sprint createSprint(@RequestBody Sprint sprint) {
//        return sprintService.save(sprint);
//    }

    @PostMapping
    public Sprint createSprint(@RequestBody Sprint sprint, @RequestParam Long proyectoId) {
        Optional<Proyecto> proyecto = proyectoService.findById(proyectoId);
        if (proyecto.isPresent()) {
            sprint.setProyecto(proyecto.get());
            return sprintService.save(sprint);
        } else {
            throw new RuntimeException("Proyecto no encontrado");
        }
    }

    @PutMapping("/{id}")
    public Sprint updateSprint(@PathVariable Long id, @RequestBody Sprint sprint) {
        if (sprintService.findById(id).isPresent()) {
            sprint.setId(id);
            return sprintService.save(sprint);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteSprint(@PathVariable Long id) {
        sprintService.delete(id);
    }

    @GetMapping("/{id}/tareas")
    public List<TareaDTO> getTareasBySprintId(@PathVariable Long id) {
        Optional<Sprint> sprint = sprintService.findById(id);
        if (sprint.isPresent()) {
            List<Tarea> tareas = sprint.get().getTareas();
            return TareaMapper.INSTANCE.tareasToTareaDTOs(tareas);
        } else {
            return new ArrayList<>();
        }
    }
}
