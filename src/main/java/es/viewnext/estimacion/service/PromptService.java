package es.viewnext.estimacion.service;

import es.viewnext.estimacion.model.Prompt;
import es.viewnext.estimacion.repository.PromptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromptService {

    @Autowired
    private PromptRepository promptRepository;

    public List<Prompt> findAll() {
        return promptRepository.findAll();
    }

    public Optional<Prompt> findById(Long id) {
        return promptRepository.findById(id);
    }

    public Prompt save(Prompt prompt) {
        return promptRepository.save(prompt);
    }

    public void delete(Long id) {
        promptRepository.deleteById(id);
    }
}
