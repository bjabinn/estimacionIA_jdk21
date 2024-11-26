package es.viewnext.estimacion.controller;

import es.viewnext.estimacion.dto.EstimacionDTO;
import es.viewnext.estimacion.dto.MedicionPorPromptDTO;
import es.viewnext.estimacion.mapper.EstimacionMapper;
import es.viewnext.estimacion.model.*;
import es.viewnext.estimacion.service.*;
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

    @Autowired
    private MedicionPorPromptService medicionPorPromptService;

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
        Estimacion estimacion = EstimacionMapper.INSTANCE.estimacionDTOToEstimacion(estimacionDTO);
        Estimacion savedEstimacion = estimacionService.save(estimacion);

        if (estimacionDTO.getMedicionesPorPrompt() != null) {
            for (MedicionPorPromptDTO medicionDTO : estimacionDTO.getMedicionesPorPrompt()) {
                MedicionPorPrompt medicion = EstimacionMapper.INSTANCE.medicionPorPromptDTOToMedicionPorPrompt(medicionDTO);
                medicion.setEstimacion(savedEstimacion);
                medicionPorPromptService.save(medicion);
            }
        }

        return savedEstimacion;
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
