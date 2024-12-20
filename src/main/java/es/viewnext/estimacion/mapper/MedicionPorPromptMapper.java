package es.viewnext.estimacion.mapper;

import es.viewnext.estimacion.dto.MedicionPorPromptDTO;
import es.viewnext.estimacion.model.MedicionPorPrompt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MedicionPorPromptMapper {
    MedicionPorPromptMapper INSTANCE = Mappers.getMapper(MedicionPorPromptMapper.class);

    @Mapping( source = "promptId", target = "prompt.id")
    MedicionPorPrompt medicionPorPromptDTOToMedicionPorPrompt(MedicionPorPromptDTO medicionPorPromptDTO);

    MedicionPorPromptDTO medicionPorPromptToMedicionPorPromptDTO(MedicionPorPrompt medicionPorPrompt);
}
