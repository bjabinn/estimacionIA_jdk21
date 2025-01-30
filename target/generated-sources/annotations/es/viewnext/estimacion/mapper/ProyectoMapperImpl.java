package es.viewnext.estimacion.mapper;

import es.viewnext.estimacion.dto.ProyectoDTO;
import es.viewnext.estimacion.model.Proyecto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-29T09:00:39+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
public class ProyectoMapperImpl implements ProyectoMapper {

    @Override
    public ProyectoDTO proyectoToProyectoDTO(Proyecto proyecto) {
        if ( proyecto == null ) {
            return null;
        }

        ProyectoDTO proyectoDTO = new ProyectoDTO();

        proyectoDTO.setId( proyecto.getId() );
        proyectoDTO.setNombre( proyecto.getNombre() );

        return proyectoDTO;
    }

    @Override
    public List<ProyectoDTO> proyectosToProyectoDTOs(List<Proyecto> proyectos) {
        if ( proyectos == null ) {
            return null;
        }

        List<ProyectoDTO> list = new ArrayList<ProyectoDTO>( proyectos.size() );
        for ( Proyecto proyecto : proyectos ) {
            list.add( proyectoToProyectoDTO( proyecto ) );
        }

        return list;
    }
}
