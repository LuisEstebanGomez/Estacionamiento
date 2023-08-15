package ar.edu.unlp.estacionamiento.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.edu.unlp.estacionamiento.models.UserModel;

//Clase que permite hacer querys a la BD .
public interface IUserRepository extends JpaRepository<UserModel, Long> {

	@Query("SELECT u FROM UserModel u WHERE u.phoneNumber = :phoneNumber")
    UserModel findByPhoneNumber(@Param("phoneNumber") String phoneNumber);

}