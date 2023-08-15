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

@RestController
@RequestMapping("/api/vehiculos")
public class VehicleController {
	
	@Autowired
    private VehicleService vehicleService;

	@GetMapping("/list")
	 public ResponseEntity<?> getAllVehicles() {
        List<VehicleModel> vehicles = vehicleService.getVehicles();
       
        if (vehicles.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(false, "No hay vehículos disponibles"),HttpStatus.NOT_FOUND);
        } else {
        	return new ResponseEntity<>(vehicles,HttpStatus.OK);

        }
        }
	}