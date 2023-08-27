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

@RestController
@RequestMapping("/movements")
public class MovementController {

    private final MovementService movementService;

    @Autowired
    public MovementController(MovementService movimientoService) {
        this.movementService = movimientoService;
    }
    
    @Autowired
	MessageSource msg;


    @PostMapping
    public ResponseEntity<?> crearMovimiento(@RequestBody MovementModel movimiento) {
        MovementModel mov= movementService.crearMovimiento(movimiento);
        return new ResponseEntity<>(mov, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovimientoById(@PathVariable Long id) {
    	MovementModel movimiento = movementService.getMovimientoById(id);
    	if (movimiento == null) {
    		return new ResponseEntity<>(new ApiResponse(true, msg.getMessage("movement.notFound", null, LocaleContextHolder.getLocale())),HttpStatus.NOT_FOUND);
    	}
        return new ResponseEntity<>(movimiento,HttpStatus.OK);
    }
}