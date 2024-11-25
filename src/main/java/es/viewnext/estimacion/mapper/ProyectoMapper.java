package es.viewnext.estimacion.mapper;

import es.viewnext.estimacion.dto.ProyectoDTO;
import es.viewnext.estimacion.model.Proyecto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProyectoMapper {
    ProyectoMapper INSTANCE = Mappers.getMapper(ProyectoMapper.class);

    ProyectoDTO proyectoToProyectoDTO(Proyecto proyecto);
    List<ProyectoDTO> proyectosToProyectoDTOs(List<Proyecto> proyectos);
}
