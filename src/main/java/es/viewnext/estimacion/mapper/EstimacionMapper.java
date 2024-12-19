package es.viewnext.estimacion.mapper;

import es.viewnext.estimacion.dto.EstimacionDTO;
import es.viewnext.estimacion.dto.MedicionPorPromptDTO;
import es.viewnext.estimacion.model.Estimacion;
import es.viewnext.estimacion.model.MedicionPorPrompt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EstimacionMapper {
    EstimacionMapper INSTANCE = Mappers.getMapper(EstimacionMapper.class);

    @Mapping(source = "proyectoId", target = "proyecto.id")
    @Mapping(source = "sprintId", target = "sprint.id")
    @Mapping(source = "tareaId", target = "tarea.id")
    //@Mapping( source = "promptId", target = "prompt.id")

    Estimacion estimacionDTOToEstimacion(EstimacionDTO estimacionDTO);

    EstimacionDTO estimacionToEstimacionDTO(Estimacion estimacion);

    MedicionPorPrompt medicionPorPromptDTOToMedicionPorPrompt(MedicionPorPromptDTO medicionPorPromptDTO);

    MedicionPorPromptDTO medicionPorPromptToMedicionPorPromptDTO(MedicionPorPrompt medicionPorPrompt);
}