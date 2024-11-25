package es.viewnext.estimacion.mapper;

import es.viewnext.estimacion.dto.SprintDTO;
import es.viewnext.estimacion.model.Sprint;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-26T00:06:58+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 23.0.1 (IBM Corporation)"
)
public class SprintMapperImpl implements SprintMapper {

    @Override
    public SprintDTO sprintToSprintDTO(Sprint sprint) {
        if ( sprint == null ) {
            return null;
        }

        SprintDTO sprintDTO = new SprintDTO();

        sprintDTO.setId( sprint.getId() );
        sprintDTO.setNombre( sprint.getNombre() );

        return sprintDTO;
    }

    @Override
    public List<SprintDTO> sprintsToSprintDTOs(List<Sprint> sprints) {
        if ( sprints == null ) {
            return null;
        }

        List<SprintDTO> list = new ArrayList<SprintDTO>( sprints.size() );
        for ( Sprint sprint : sprints ) {
            list.add( sprintToSprintDTO( sprint ) );
        }

        return list;
    }
}
