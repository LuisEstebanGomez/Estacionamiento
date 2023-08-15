package ar.edu.unlp.estacionamiento.services;

import ar.edu.unlp.estacionamiento.models.MovementModel;
import ar.edu.unlp.estacionamiento.repositories.IMovementRepository;

import org.springframework.stereotype.Service;

@Service
public class MovementService {

    private final IMovementRepository movimientoRepository;

    public MovementService(IMovementRepository movimientoRepository) {
        this.movimientoRepository = movimientoRepository;
    }

    public MovementModel crearMovimiento(MovementModel movimiento) {
        return movimientoRepository.save(movimiento);
    }

    public MovementModel getMovimientoById(Long id) {
        return movimientoRepository.findById(id).orElse(null);
    }
}