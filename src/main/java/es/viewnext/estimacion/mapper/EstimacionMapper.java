package es.viewnext.estimacion.mapper;

import es.viewnext.estimacion.dto.EstimacionDTO;
import es.viewnext.estimacion.model.Estimacion;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = MedicionPorPromptMapper.class)
public interface EstimacionMapper {
    EstimacionMapper INSTANCE = Mappers.getMapper(EstimacionMapper.class);

    @Mapping(source = "tareaId", target = "tarea.id")
    @Mapping(source = "tarea", target = "tarea.descripcion")
    Estimacion estimacionDTOToEstimacion(EstimacionDTO estimacionDTO);

    @Mapping(source = "tarea.id", target = "tareaId")
    @Mapping(source = "tarea.descripcion", target = "tarea")
    EstimacionDTO estimacionToEstimacionDTO(Estimacion estimacion);

    List<EstimacionDTO> estimacionesToEstimacionesDTO(List<Estimacion> estimaciones);

    @AfterMapping
    default void afterMapping(@MappingTarget EstimacionDTO estimacionDTO, Estimacion estimacion) {
        if (estimacion.getMedicionPorPrompt() != null) {
            MedicionPorPromptMapper mapper = MedicionPorPromptMapper.INSTANCE;
            estimacionDTO.setMedicionesPorPrompt(mapper.medicionesPorPromptToMedicionesPorPromptDTO(estimacion.getMedicionPorPrompt()));
        }
    }
}