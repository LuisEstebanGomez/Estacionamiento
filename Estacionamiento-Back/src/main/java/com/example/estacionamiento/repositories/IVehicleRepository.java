package com.example.estacionamiento.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.estacionamiento.models.VehicleModel;

public interface IVehicleRepository extends JpaRepository<VehicleModel, Long> {

	boolean existsByPatente(String patente);
	
}


