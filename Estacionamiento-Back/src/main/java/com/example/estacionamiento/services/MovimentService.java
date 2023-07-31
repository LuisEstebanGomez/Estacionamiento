package com.example.estacionamiento.services;

import com.example.estacionamiento.models.MovimentModel;
import com.example.estacionamiento.repositories.IMovimentRepository;

import org.springframework.stereotype.Service;

@Service
public class MovimentService {

    private final IMovimentRepository movimientoRepository;

    public MovimentService(IMovimentRepository movimientoRepository) {
        this.movimientoRepository = movimientoRepository;
    }

    public MovimentModel crearMovimiento(MovimentModel movimiento) {
        return movimientoRepository.save(movimiento);
    }

    public MovimentModel getMovimientoById(Long id) {
        return movimientoRepository.findById(id).orElse(null);
    }
}