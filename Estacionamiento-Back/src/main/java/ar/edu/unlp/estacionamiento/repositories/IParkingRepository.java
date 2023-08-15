package ar.edu.unlp.estacionamiento.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.edu.unlp.estacionamiento.models.ParkingModel;
import ar.edu.unlp.estacionamiento.models.UserModel;


public interface IParkingRepository extends JpaRepository<ParkingModel, Long> {

	ParkingModel save(Optional<ParkingModel> estacionamiento);

	 @Query("SELECT p FROM ParkingModel p WHERE p.user.phoneNumber = :phoneNumber AND p.activo=true")
	 ParkingModel findByUserPhoneNumber(@Param("phoneNumber") String phoneNumber);
	 
}




