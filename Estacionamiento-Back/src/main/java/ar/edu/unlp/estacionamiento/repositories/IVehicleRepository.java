package ar.edu.unlp.estacionamiento.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unlp.estacionamiento.models.VehicleModel;

public interface IVehicleRepository extends JpaRepository<VehicleModel, Long> {

	boolean existsByPatente(String patente);
	
}


