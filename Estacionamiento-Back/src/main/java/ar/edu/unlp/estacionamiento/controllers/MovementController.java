package ar.edu.unlp.estacionamiento.controllers;

import ar.edu.unlp.estacionamiento.models.MovementModel;
import ar.edu.unlp.estacionamiento.security.dto.ApiResponse;
import ar.edu.unlp.estacionamiento.services.MovementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

@RestController
@RequestMapping("/movements")
public class MovementController {

    private static final Logger logger = LogManager.getLogger(MovementController.class);
    private static final Marker APP = MarkerManager.getMarker("APP");

    private final MovementService movementService;

    @Autowired
    public MovementController(MovementService movimientoService) {
        this.movementService = movimientoService;
    }
    
    @Autowired
	MessageSource msg;

    @PostMapping
    public ResponseEntity<?> crearMovimiento(@RequestBody MovementModel movimiento) {
        logger.info(APP, "Creando nuevo movimiento");
        MovementModel mov = movementService.crearMovimiento(movimiento);
        logger.info(APP, "Movimiento creado con éxito: {}", mov.getId());
        return new ResponseEntity<>(mov, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovimientoById(@PathVariable Long id) {
        logger.info(APP, "Obteniendo movimiento por ID: {}", id);
        MovementModel movimiento = movementService.getMovimientoById(id);
        
        if (movimiento == null) {
            logger.warn(APP, "Movimiento no encontrado para ID: {}", id);
            return new ResponseEntity<>(new ApiResponse(true, msg.getMessage("movement.notFound", null, LocaleContextHolder.getLocale())), HttpStatus.NOT_FOUND);
        }
        
        logger.info(APP, "Movimiento encontrado para ID: {}", id);
        return new ResponseEntity<>(movimiento, HttpStatus.OK);
    }
}