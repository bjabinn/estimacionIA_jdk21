package es.viewnext.estimacion.service;

import es.viewnext.estimacion.model.Componente;
import es.viewnext.estimacion.repository.ComponenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComponenteService {

    @Autowired
    private ComponenteRepository componenteRepository;

    public List<Componente> findAll() {
        return componenteRepository.findAll();
    }

    public Optional<Componente> findById(Long id) {
        return componenteRepository.findById(id);
    }

    public Componente save(Componente componente) {
        return componenteRepository.save(componente);
    }

    public void delete(Long id) {
        componenteRepository.deleteById(id);
    }
}