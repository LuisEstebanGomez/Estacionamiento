package ar.edu.unlp.estacionamiento.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/{phoneNumber}/{patente}/start")
    public ResponseEntity<?> iniciarEstacionamiento(@PathVariable String phoneNumber, @PathVariable String patente) {
        LocalDateTime currentDateTime = getCurrentDateTime(); // Obtiene la fecha y hora actual
        String response="";
		ParkingModel estacionamientoIniciado = parkingService.iniciarEstacionamiento(phoneNumber, patente, currentDateTime, response);
      
        if (estacionamientoIniciado != null) {
            return new ResponseEntity<>(new ApiResponse(true, "Se inició el estacionamiento para la patente: " + patente + " del usuario: " + phoneNumber ), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "No se puede iniciar el estacionamiento."+ " -MOTIVO :" + response), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{phoneNumber}/finish")
    public ResponseEntity<?> finalizarEstacionamiento(@PathVariable String phoneNumber) {
        LocalDateTime currentDateTime = getCurrentDateTime(); // Obtiene la fecha y hora actual
        ParkingModel estacionamientoFinalizado = parkingService.finalizarEstacionamiento(phoneNumber, currentDateTime);
        
        if (estacionamientoFinalizado != null) {
            return new ResponseEntity<>(new ApiResponse(true, "Se finalizó el estacionamiento para el usuario: " + phoneNumber), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "No se puede finalizar el estacionamiento."), HttpStatus.NOT_FOUND);
        }
    }   

    private LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
}