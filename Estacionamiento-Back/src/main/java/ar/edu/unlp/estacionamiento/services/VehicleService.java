package ar.edu.unlp.estacionamiento.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ar.edu.unlp.estacionamiento.models.VehicleModel;
import ar.edu.unlp.estacionamiento.repositories.IVehicleRepository;
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
	
	 public Optional<VehicleModel> getVehicleById(Long id) {
	        return vehiculoRepository.findById(id);
	}


	public boolean existePatente(String patente) {
		return vehiculoRepository.existsByPatente(patente);
	}
	
}
