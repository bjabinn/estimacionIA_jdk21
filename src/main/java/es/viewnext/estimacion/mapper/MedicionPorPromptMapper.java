package es.viewnext.estimacion.mapper;

import es.viewnext.estimacion.dto.MedicionPorPromptDTO;
import es.viewnext.estimacion.model.MedicionPorPrompt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MedicionPorPromptMapper {
    MedicionPorPromptMapper INSTANCE = Mappers.getMapper(MedicionPorPromptMapper.class);

    @Mapping( source = "promptId", target = "prompt.id")
    @Mapping( source = "promptNombre", target = "prompt.prompt")
    MedicionPorPrompt medicionPorPromptDTOToMedicionPorPrompt(MedicionPorPromptDTO medicionPorPromptDTO);

    @Mapping( source = "prompt.id", target = "promptId")
    @Mapping( source = "prompt.prompt", target = "promptNombre")
    MedicionPorPromptDTO medicionPorPromptToMedicionPorPromptDTO(MedicionPorPrompt medicionPorPrompt);

    @Mapping( source = "prompt.id", target = "promptId")
    @Mapping( source = "prompt.prompt", target = "promptNombre")
    List<MedicionPorPromptDTO> medicionesPorPromptToMedicionesPorPromptDTO(List<MedicionPorPrompt> medicionPorPrompt);
}
