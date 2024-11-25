package es.viewnext.estimacion.mapper;

import es.viewnext.estimacion.dto.TareaDTO;
import es.viewnext.estimacion.model.Tarea;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TareaMapper {
    TareaMapper INSTANCE = Mappers.getMapper(TareaMapper.class);

    TareaDTO tareaToTareaDTO(Tarea tarea);
    List<TareaDTO> tareasToTareaDTOs(List<Tarea> tareas);
}