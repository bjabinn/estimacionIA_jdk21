package es.viewnext.estimacion.service;

import es.viewnext.estimacion.model.Sprint;
import es.viewnext.estimacion.repository.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SprintService {

    @Autowired
    private SprintRepository sprintRepository;

    public List<Sprint> findAll() {
        return sprintRepository.findAll();
    }

    public Optional<Sprint> findById(Long id) {
        return sprintRepository.findById(id);
    }

    public Sprint save(Sprint sprint) {
        return sprintRepository.save(sprint);
    }

    public void delete(Long id) {
        sprintRepository.deleteById(id);
    }
}
