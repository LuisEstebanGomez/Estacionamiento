package com.example.estacionamiento.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.estacionamiento.models.CtaCteModel;
import com.example.estacionamiento.models.UserModel;

public interface CtaCteRepository extends JpaRepository<CtaCteModel, Long> {

	@Query("SELECT u FROM UserModel u WHERE u.phoneNumber = :phoneNumber")
    UserModel findByPhoneNumber(@Param("phoneNumber") Long phoneNumber);
	
}
