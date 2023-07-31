package com.example.estacionamiento.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.estacionamiento.models.VehicleModel;
import com.example.estacionamiento.repositories.IVehicleRepository;
@Service
public class VehicleService {

	@Autowired
    private IVehicleRepository vehiculoRepository;

    public VehicleModel crearVehiculo(VehicleModel vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

	public List<VehicleModel> getVehicles() {
		return vehiculoRepository.findAll();
		
	}

	public boolean existePatente(String patente) {
		return vehiculoRepository.existsByPatente(patente);
	}



   

	

}
