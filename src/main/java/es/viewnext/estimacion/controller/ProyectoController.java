package es.viewnext.estimacion.controller;

import es.viewnext.estimacion.model.Proyecto;
import es.viewnext.estimacion.service.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    @GetMapping
    public List<Proyecto> getAllProyectos() {
        return proyectoService.findAll();
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
}
