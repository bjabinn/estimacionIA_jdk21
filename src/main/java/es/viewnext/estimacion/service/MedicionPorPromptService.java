package es.viewnext.estimacion.service;

import es.viewnext.estimacion.model.MedicionPorPrompt;
import es.viewnext.estimacion.repository.MedicionPorPromptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicionPorPromptService {

    @Autowired
    private MedicionPorPromptRepository medicionPorPromptRepository;

    public List<MedicionPorPrompt> findAll() {
        return medicionPorPromptRepository.findAll();
    }

    public Optional<MedicionPorPrompt> findById(Long id) {
        return medicionPorPromptRepository.findById(id);
    }

    public MedicionPorPrompt save(MedicionPorPrompt componente) {
        return medicionPorPromptRepository.save(componente);
    }

    public void delete(Long id) {
        medicionPorPromptRepository.deleteById(id);
    }
}