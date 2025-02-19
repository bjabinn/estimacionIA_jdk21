package es.viewnext.estimacion.controller;

import es.viewnext.estimacion.dto.*;
import es.viewnext.estimacion.mapper.*;
import es.viewnext.estimacion.model.*;
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
        List<ProyectoDTO> listaProyectos = ProyectoMapper.INSTANCE.proyectosToProyectoDTOs(proyectoService.findAll());
        return listaProyectos;
    }

    @GetMapping("/{id}")
    public ProyectoDTO getProyectoById(@PathVariable Long id) {
        Optional<Proyecto> proyecto = proyectoService.findById(id);
        if (proyecto.isPresent()){
            ProyectoDTO proyectoDTO = ProyectoMapper.INSTANCE.proyectoToProyectoDTO(proyecto.get());
            return proyectoDTO;
        }
        return null;
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

    @GetMapping("/{id}/mediciones")
    public List<MedicionPorPromptDTO> getEstimacionesByProyectoId(@PathVariable Long id) {
        Optional<Proyecto> proyecto = proyectoService.findById(id);
        if (proyecto.isPresent()) {
            List<MedicionPorPrompt> mediciones = new ArrayList<>();
            for (Sprint sprint : proyecto.get().getSprints()) {
                for (Tarea tarea : sprint.getTareas()) {
                    Estimacion estimacion = tarea.getEstimacion();
                    if (estimacion != null) {
                        mediciones.addAll(estimacion.getMedicionPorPrompt());
                    }
                }
            }
            return MedicionPorPromptMapper.INSTANCE.medicionesPorPromptToMedicionesPorPromptDTP(mediciones);
        } else {
            return new ArrayList<>();
        }
    }


}