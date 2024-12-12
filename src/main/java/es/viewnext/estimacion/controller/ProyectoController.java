package es.viewnext.estimacion.controller;

import es.viewnext.estimacion.dto.PromptDTO;
import es.viewnext.estimacion.dto.ProyectoDTO;
import es.viewnext.estimacion.dto.SprintDTO;
import es.viewnext.estimacion.mapper.PromptMapper;
import es.viewnext.estimacion.mapper.ProyectoMapper;
import es.viewnext.estimacion.mapper.SprintMapper;
import es.viewnext.estimacion.model.Prompt;
import es.viewnext.estimacion.model.Proyecto;
import es.viewnext.estimacion.model.Sprint;
import es.viewnext.estimacion.service.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    @GetMapping
    public List<ProyectoDTO> getAllProyectos() {
        List<Proyecto> proyectos = proyectoService.findAll();
        return ProyectoMapper.INSTANCE.proyectosToProyectoDTOs(proyectos);
    }

    @GetMapping("/{id}")
    public Optional<Proyecto> getProyectoById(@PathVariable Long id) {
        return proyectoService.findById(id);
    }

    @PostMapping
    public Proyecto createProyecto(@RequestBody Proyecto proyecto) {
        return proyectoService.save(proyecto);
    }

    @PutMapping("/{id}")
    public Proyecto updateProyecto(@PathVariable Long id, @RequestBody Proyecto proyecto) {
        if (proyectoService.findById(id).isPresent()) {
            proyecto.setId(id);
            return proyectoService.save(proyecto);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteProyecto(@PathVariable Long id) {
        proyectoService.delete(id);
    }

    @GetMapping("/{id}/sprints")
    public List<SprintDTO> getSprintsByProyectoId(@PathVariable Long id) {
        Optional<Proyecto> proyecto = proyectoService.findById(id);
        if (proyecto.isPresent()) {
            List<Sprint> sprints = proyecto.get().getSprints();
            return SprintMapper.INSTANCE.sprintsToSprintDTOs(sprints);
        } else {
            return new ArrayList<>();
        }
    }

    @GetMapping("/{id}/prompts")
    public List<PromptDTO> getPromptsByProyectoId(@PathVariable Long id) {
        Optional<Proyecto> proyecto = proyectoService.findById(id);
        if (proyecto.isPresent()) {
            List<Prompt> prompts = proyecto.get().getPrompts();
            return PromptMapper.INSTANCE.promptsToPromptDTOs(prompts);
        } else {
            return new ArrayList<>();
        }
    }
}