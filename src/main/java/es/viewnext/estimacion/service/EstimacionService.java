package es.viewnext.estimacion.service;

import es.viewnext.estimacion.model.Estimacion;
import es.viewnext.estimacion.repository.EstimacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstimacionService {

    @Autowired
    private EstimacionRepository estimacionRepository;

    public List<Estimacion> findAll() {
        return estimacionRepository.findAll();
    }

    public Optional<Estimacion> findById(Long id) {
        return estimacionRepository.findById(id);
    }

    public Estimacion save(Estimacion estimacion) {
        return estimacionRepository.save(estimacion);
    }

    public void delete(Long id) {
        estimacionRepository.deleteById(id);
    }
}
