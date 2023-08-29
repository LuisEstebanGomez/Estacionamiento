package ar.edu.unlp.estacionamiento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unlp.estacionamiento.models.VehicleModel;
import ar.edu.unlp.estacionamiento.security.dto.ApiResponse;
import ar.edu.unlp.estacionamiento.services.VehicleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

@RestController
@RequestMapping("/api/vehiculos")
public class VehicleController {
    
    @Autowired
    private VehicleService vehicleService;

    private static final Logger logger = LogManager.getLogger(VehicleController.class);
    private static final Marker APP = MarkerManager.getMarker("APP");

    @GetMapping("/list")
     public ResponseEntity<?> getAllVehicles() {
        logger.info(APP, "Obteniendo lista de todos los vehículos");

        List<VehicleModel> vehicles = vehicleService.getVehicles();

        if (vehicles.isEmpty()) {
            logger.warn(APP, "No se encontraron vehículos disponibles");
            return new ResponseEntity<>(new ApiResponse(false, "No hay vehículos disponibles"), HttpStatus.NOT_FOUND);
        } else {
            logger.info(APP, "Vehículos obtenidos con éxito");
            return new ResponseEntity<>(vehicles, HttpStatus.OK);
        }
    }
}