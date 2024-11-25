package es.viewnext.estimacion.controller;

import es.viewnext.estimacion.model.Prompt;
import es.viewnext.estimacion.service.PromptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/prompts")
public class PromptController {

    @Autowired
    private PromptService promptService;

    @GetMapping
    public List<Prompt> getAllPrompts() {
        return promptService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Prompt> getPromptById(@PathVariable Long id) {
        return promptService.findById(id);
    }

    @PostMapping
    public Prompt createPrompt(@RequestBody Prompt prompt) {
        return promptService.save(prompt);
    }

    @PutMapping("/{id}")
    public Prompt updatePrompt(@PathVariable Long id, @RequestBody Prompt prompt) {
        if (promptService.findById(id).isPresent()) {
            prompt.setId(id);
            return promptService.save(prompt);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deletePrompt(@PathVariable Long id) {
        promptService.delete(id);
    }
}