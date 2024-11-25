package es.viewnext.estimacion.controller;

import es.viewnext.estimacion.dto.SprintDTO;
import es.viewnext.estimacion.mapper.SprintMapper;
import es.viewnext.estimacion.model.Sprint;
import es.viewnext.estimacion.model.Tarea;
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

    @GetMapping
    public List<SprintDTO> getAllSprints() {
        List<Sprint> sprints = sprintService.findAll();
        return SprintMapper.INSTANCE.sprintsToSprintDTOs(sprints);
    }

    @GetMapping("/{id}")
    public Optional<Sprint> getSprintById(@PathVariable Long id) {
        return sprintService.findById(id);
    }

    @PostMapping
    public Sprint createSprint(@RequestBody Sprint sprint) {
        return sprintService.save(sprint);
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
    public List<Tarea> getTareasBySprintId(@PathVariable Long id) {
        Optional<Sprint> sprint = sprintService.findById(id);
        if (sprint.isPresent()) {
            return sprint.get().getTareas();
        } else {
            return new ArrayList<>();
        }
    }
}
