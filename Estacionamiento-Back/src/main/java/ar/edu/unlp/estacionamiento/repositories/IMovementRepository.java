package ar.edu.unlp.estacionamiento.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unlp.estacionamiento.models.MovementModel;

@Repository
public interface IMovementRepository extends JpaRepository<MovementModel, Long> {
 
}