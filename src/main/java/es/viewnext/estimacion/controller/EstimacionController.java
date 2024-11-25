package es.viewnext.estimacion.controller;

import es.viewnext.estimacion.model.Estimacion;
import es.viewnext.estimacion.service.EstimacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estimacion")
public class EstimacionController {

    @Autowired
    private EstimacionService estimacionService;

    @GetMapping
    public List<Estimacion> getAllEstimaciones() {
        return estimacionService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Estimacion> getEstimacionById(@PathVariable Long id) {
        return estimacionService.findById(id);
    }

    @PostMapping
    public Estimacion createEstimacion(@RequestBody Estimacion estimacion) {
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
