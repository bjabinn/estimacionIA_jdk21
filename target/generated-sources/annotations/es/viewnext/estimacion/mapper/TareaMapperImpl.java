package es.viewnext.estimacion.mapper;

import es.viewnext.estimacion.dto.TareaDTO;
import es.viewnext.estimacion.model.Tarea;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-10T14:27:08+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
public class TareaMapperImpl implements TareaMapper {

    @Override
    public TareaDTO tareaToTareaDTO(Tarea tarea) {
        if ( tarea == null ) {
            return null;
        }

        TareaDTO tareaDTO = new TareaDTO();

        tareaDTO.setId( tarea.getId() );
        tareaDTO.setDescripcion( tarea.getDescripcion() );

        return tareaDTO;
    }

    @Override
    public List<TareaDTO> tareasToTareaDTOs(List<Tarea> tareas) {
        if ( tareas == null ) {
            return null;
        }

        List<TareaDTO> list = new ArrayList<TareaDTO>( tareas.size() );
        for ( Tarea tarea : tareas ) {
            list.add( tareaToTareaDTO( tarea ) );
        }

        return list;
    }
}
