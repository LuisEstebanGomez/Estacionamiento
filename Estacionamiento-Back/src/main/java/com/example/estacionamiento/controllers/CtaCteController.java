package com.example.estacionamiento.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.estacionamiento.models.CtaCteModel;
import com.example.estacionamiento.models.MovimentModel;
import com.example.estacionamiento.models.UserModel;
import com.example.estacionamiento.models.VehicleModel;
import com.example.estacionamiento.repositories.IUserRepository;
import com.example.estacionamiento.services.CtaCteService;

@RestController
@RequestMapping("/api/ctacte")
public class CtaCteController {
	
	 @Autowired
	 private CtaCteService ctaCteService;
	 @Autowired
	 private IUserRepository userRepository;

	    @GetMapping("/{phoneNumber}/movimientos")
	    public List<MovimentModel> getMovimientos(@PathVariable String phoneNumber) {
	    	UserModel user = userRepository.findByPhoneNumber(phoneNumber);
	    	Long id = user.getId();
	    	return ctaCteService.getMovimientosByCtaCteId(id);
	    }
}
