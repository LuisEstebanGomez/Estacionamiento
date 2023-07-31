package com.example.estacionamiento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.estacionamiento.models.VehicleModel;
import com.example.estacionamiento.services.VehicleService;

@RestController
@RequestMapping("/api/vehiculos")
public class VehicleController {
	
	@Autowired
    private VehicleService vehicleService;

	@GetMapping("/list")
	public ResponseEntity<List<VehicleModel>> getAllVehicles() {
	      List<VehicleModel> v = vehicleService.getVehicles();
	      if (v.isEmpty()) {
	           return ResponseEntity.noContent().build();
	      } else {
	           return ResponseEntity.ok(v);
	      }
	}
}
