package ar.edu.unlp.estacionamiento.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unlp.estacionamiento.models.ParkingModel;
import ar.edu.unlp.estacionamiento.security.dto.ApiResponse;
import ar.edu.unlp.estacionamiento.services.ParkingService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

@RestController
@RequestMapping("/api/estacionamientos")
public class ParkingController {

    private static final Logger logger = LogManager.getLogger(ParkingController.class);
    private static final Marker APP = MarkerManager.getMarker("APP");

    @Autowired
    private ParkingService parkingService;
    
    @Autowired
    MessageSource msg;

    @PostMapping("/{phoneNumber}/{patente}/start")
    public ResponseEntity<?> iniciarEstacionamiento(@PathVariable String phoneNumber, @PathVariable String patente) {
        logger.info(APP, "Iniciando estacionamiento para teléfono: {}, patente: {}", phoneNumber, patente);
        
        LocalDateTime currentDateTime = getCurrentDateTime(); // Obtiene la fecha y hora actual
        StringBuilder response = new StringBuilder();
        ParkingModel estacionamientoIniciado = parkingService.iniciarEstacionamiento(phoneNumber, patente, currentDateTime, response);
      
        if (estacionamientoIniciado != null) {
            logger.info(APP, "Estacionamiento iniciado con éxito");
            return new ResponseEntity<>(new ApiResponse(true, msg.getMessage("parking.create", null, LocaleContextHolder.getLocale())), HttpStatus.OK);
        } else {
            logger.warn(APP, "No se pudo iniciar el estacionamiento: {}", response.toString());
            return new ResponseEntity<>(new ApiResponse(true, msg.getMessage(response.toString(), null, LocaleContextHolder.getLocale())), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{phoneNumber}/finish")
    public ResponseEntity<?> finalizarEstacionamiento(@PathVariable String phoneNumber) {
        logger.info(APP, "Finalizando estacionamiento para teléfono: {}", phoneNumber);
        
        LocalDateTime currentDateTime = getCurrentDateTime(); // Obtiene la fecha y hora actual
        ParkingModel estacionamientoFinalizado = parkingService.finalizarEstacionamiento(phoneNumber, currentDateTime);
        
        if (estacionamientoFinalizado != null) {
            logger.info(APP, "Estacionamiento finalizado con éxito");
            return new ResponseEntity<>(new ApiResponse(true, msg.getMessage("parking.finish", null, LocaleContextHolder.getLocale())), HttpStatus.OK);
        } else {
            logger.warn(APP, "No se pudo finalizar el estacionamiento: {}", phoneNumber);
            return new ResponseEntity<>(new ApiResponse(false, msg.getMessage("parking.notFound", null, LocaleContextHolder.getLocale())), HttpStatus.NOT_FOUND);
        }
    }   

    private LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
}