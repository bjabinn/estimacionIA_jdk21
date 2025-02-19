package es.viewnext.estimacion.service;

import es.viewnext.estimacion.dto.EstimacionDTO;
import es.viewnext.estimacion.dto.MedicionPorPromptDTO;
import es.viewnext.estimacion.mapper.EstimacionMapper;
import es.viewnext.estimacion.model.Estimacion;
import es.viewnext.estimacion.model.MedicionPorPrompt;
import es.viewnext.estimacion.model.Proyecto;
import es.viewnext.estimacion.repository.EstimacionRepository;
import es.viewnext.estimacion.repository.ProyectoRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private EstimacionRepository estimacionRepository;

    public List<Proyecto> findAll() {
        return proyectoRepository.findAll();
    }

    public Optional<Proyecto> findById(Long id) {
        return proyectoRepository.findById(id);
    }

    public Proyecto save(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    public void delete(Long id) {
        proyectoRepository.deleteById(id);
    }


    public ByteArrayInputStream exportAllProyectosToExcel() throws IOException {
        List<Estimacion> estimaciones = estimacionRepository.findAll();
        List<EstimacionDTO> estimacionesDTO = EstimacionMapper.INSTANCE.estimacionesToEstimacionesDTO(estimaciones);
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Proyectos");

        // Meto la cabecera
        Row headerRow = sheet.createRow(0);
        String[] headers = {"UltimaModificacion", "Proyecto", "Sprint", "Tarea", "Owner", "Usada IA", "Prompt",
                "Calidad", "Estimacion con IA", "Estimación sin IA", "Comentario"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Y ahora por las mediciones
        int rowNum = 1;
        for (EstimacionDTO estimacionDTO : estimacionesDTO) {
            for (MedicionPorPromptDTO medicion : estimacionDTO.getMedicionesPorPrompt()) {
                Row row = sheet.createRow(rowNum++);
                //row.createCell(0).setCellValue(medicion.getFechaUltimaModificacion().toString());
                //row.createCell(1).setCellValue(estimacionDTO.getTarea().getProyecto().getNombre());
                //row.createCell(2).setCellValue(estimacionDTO.getTarea().getSprint().getNombre());
                //row.createCell(3).setCellValue(estimacionDTO.getTarea().getNombre());
                row.createCell(4).setCellValue(estimacionDTO.getOwner());
                row.createCell(5).setCellValue(medicion.isUsadaIa() ? "Si" : "No");
                //row.createCell(6).setCellValue(medicion.getPrompt().getDescripcion());
                row.createCell(7).setCellValue(medicion.getCalidadSalidaIa().toString());
                row.createCell(8).setCellValue(medicion.getEstimacionConIa().toString());
                row.createCell(9).setCellValue(medicion.getEstimacionSinIa().toString());
                row.createCell(10).setCellValue(estimacionDTO.getNotas());
            }
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
}
