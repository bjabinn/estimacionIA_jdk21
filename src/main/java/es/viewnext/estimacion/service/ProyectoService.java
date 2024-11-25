package es.viewnext.estimacion.service;

import es.viewnext.estimacion.model.Proyecto;
import es.viewnext.estimacion.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    public List<Proyecto> findAll() {
        return proyectoRepository.findAll();
    }

    public Optional<Proyecto> findById(Long id) {
        return proyectoRepository.findById(id);
    }

    public Proyecto save(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    public void delete(Long id) {
        proyectoRepository.deleteById(id);
    }
}
