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

@RestController
@RequestMapping("/api/estacionamientos")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;
    
    @Autowired
	MessageSource msg;

    @PostMapping("/{phoneNumber}/{patente}/start")
    public ResponseEntity<?> iniciarEstacionamiento(@PathVariable String phoneNumber, @PathVariable String patente) {
        LocalDateTime currentDateTime = getCurrentDateTime(); // Obtiene la fecha y hora actual
      
		StringBuilder response = new StringBuilder();
		ParkingModel estacionamientoIniciado = parkingService.iniciarEstacionamiento(phoneNumber, patente, currentDateTime, response);
      
        if (estacionamientoIniciado != null) {
            return new ResponseEntity<>(new ApiResponse(true, msg.getMessage("parking.create", null, LocaleContextHolder.getLocale())), HttpStatus.OK);
        } else {
 
            return new ResponseEntity<>(new ApiResponse(true, msg.getMessage(response.toString(), null, LocaleContextHolder.getLocale())), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{phoneNumber}/finish")
    public ResponseEntity<?> finalizarEstacionamiento(@PathVariable String phoneNumber) {
        LocalDateTime currentDateTime = getCurrentDateTime(); // Obtiene la fecha y hora actual
        ParkingModel estacionamientoFinalizado = parkingService.finalizarEstacionamiento(phoneNumber, currentDateTime);
        
        if (estacionamientoFinalizado != null) {
            return new ResponseEntity<>(new ApiResponse(true, msg.getMessage("parking.finish", null, LocaleContextHolder.getLocale())), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(false, msg.getMessage("parking.notFound", null, LocaleContextHolder.getLocale())), HttpStatus.NOT_FOUND);
        }
    }   

    private LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
}