package com.example.estacionamiento.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.estacionamiento.models.ParkingModel;
import com.example.estacionamiento.services.ParkingService;

@RestController
@RequestMapping("/api/estacionamientos")
public class ParkingController {

	@Autowired
    private ParkingService estacionamientoService;

	@PostMapping("/{phoneNumber}/{patente}/iniciar")
	public ResponseEntity<?> iniciarEstacionamiento(@PathVariable String phoneNumber, @PathVariable String patente) {
	    ParkingModel estacionamientoIniciado = estacionamientoService.iniciarEstacionamiento(phoneNumber, patente);
	    if (estacionamientoIniciado != null) {
	        return ResponseEntity.ok(estacionamientoIniciado);
	    } else {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede iniciar el estacionamiento.");
	    }
	}

	@PostMapping("/{phoneNumber}/finalizar")
	public ResponseEntity<?> finalizarEstacionamiento(@PathVariable String phoneNumber) {
	    ParkingModel estacionamientoFinalizado = estacionamientoService.finalizarEstacionamiento(phoneNumber);
	    if (estacionamientoFinalizado != null) {
	        return ResponseEntity.ok(estacionamientoFinalizado);
	    } else {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede finalizar el estacionamiento.");
	    }
	}	
}
