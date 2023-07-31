package com.example.estacionamiento.controllers;

import com.example.estacionamiento.models.MovimentModel;
import com.example.estacionamiento.services.MovimentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movimientos")
public class MovimentController {

    private final MovimentService movimientoService;

    @Autowired
    public MovimentController(MovimentService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @PostMapping
    public MovimentModel crearMovimiento(@RequestBody MovimentModel movimiento) {
        return movimientoService.crearMovimiento(movimiento);
    }

    @GetMapping("/{id}")
    public MovimentModel getMovimientoById(@PathVariable Long id) {
        return movimientoService.getMovimientoById(id);
    }
}