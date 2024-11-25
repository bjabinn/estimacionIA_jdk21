package es.viewnext.estimacion.controller;

import es.viewnext.estimacion.model.Tarea;
import es.viewnext.estimacion.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    @GetMapping
    public List<Tarea> getAllTareas() {
        return tareaService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Tarea> getTareaById(@PathVariable Long id) {
        return tareaService.findById(id);
    }

    @PostMapping
    public Tarea createTarea(@RequestBody Tarea tarea) {
        return tareaService.save(tarea);
    }

    @PutMapping("/{id}")
    public Tarea updateTarea(@PathVariable Long id, @RequestBody Tarea tarea) {
        if (tareaService.findById(id).isPresent()) {
            tarea.setId(id);
            return tareaService.save(tarea);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteTarea(@PathVariable Long id) {
        tareaService.delete(id);
    }
}