package es.viewnext.estimacion.mapper;

import es.viewnext.estimacion.dto.PromptDTO;
import es.viewnext.estimacion.model.Prompt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PromptMapper {
    PromptMapper INSTANCE = Mappers.getMapper(PromptMapper.class);

    @Mapping(source = "proyecto.id", target = "proyectoId")
    PromptDTO promptToPromptDTO(Prompt prompt);

    List<PromptDTO> promptsToPromptDTOs(List<Prompt> prompts);
}
