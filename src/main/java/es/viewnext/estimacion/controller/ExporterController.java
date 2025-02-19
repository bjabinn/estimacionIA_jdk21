package es.viewnext.estimacion.controller;

import es.viewnext.estimacion.service.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/export")
public class ExporterController {

    @Autowired
    private ProyectoService proyectoService;

    @GetMapping("/proyectos")
    public ResponseEntity<byte[]> exportAllProyectosToExcel() throws IOException {
        ByteArrayInputStream in = proyectoService.exportAllProyectosToExcel();
        HttpHeaders headers = new HttpHeaders();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        headers.add("Content-Disposition", "attachment; filename=proyectos_" +
                formattedDateTime + ".xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(in.readAllBytes());

    }
}
