package es.viewnext.estimacion.mapper;

import es.viewnext.estimacion.dto.SprintDTO;
import es.viewnext.estimacion.model.Sprint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SprintMapper {
    SprintMapper INSTANCE = Mappers.getMapper(SprintMapper.class);

    @Mapping(source = "proyecto.id", target = "proyectoId")
    SprintDTO sprintToSprintDTO(Sprint sprint);

    List<SprintDTO> sprintsToSprintDTOs(List<Sprint> sprints);
}