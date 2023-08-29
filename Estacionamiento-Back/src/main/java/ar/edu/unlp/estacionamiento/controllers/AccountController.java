package ar.edu.unlp.estacionamiento.controllers;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unlp.estacionamiento.models.AccountModel;
import ar.edu.unlp.estacionamiento.models.MovementModel;
import ar.edu.unlp.estacionamiento.models.UserModel;
import ar.edu.unlp.estacionamiento.models.VehicleModel;
import ar.edu.unlp.estacionamiento.repositories.IUserRepository;
import ar.edu.unlp.estacionamiento.security.dto.ApiResponse;
import ar.edu.unlp.estacionamiento.services.AccountService;

@RestController
@RequestMapping("/api/account")
public class AccountController {
	
	 @Autowired
	 private AccountService ctaCteService;
	 @Autowired
	 private IUserRepository userRepository;
	 
	 @Autowired
	 MessageSource msg;
	 
	 private static final Logger logger = LogManager.getLogger(AccountController.class);
	 public static final Marker APP = MarkerManager.getMarker("APP");

	 @GetMapping("/{phoneNumber}/movements")
	    public ResponseEntity<?> getMovimientos(@PathVariable String phoneNumber) {
		 	logger.info(APP, "Obteniendo movimientos para el número de teléfono: {}", phoneNumber);
		
	        UserModel user = userRepository.findByPhoneNumber(phoneNumber);
	        if (user == null) {
	        	logger.warn(APP, "Usuario no encontrado para el número de teléfono: {}", phoneNumber);
	            return new ResponseEntity<>(new ApiResponse(false, msg.getMessage("user.notFound", null, LocaleContextHolder.getLocale())), HttpStatus.NOT_FOUND);
	        }
	        
	        Long id = user.getId();
	        List<MovementModel> movimientos = ctaCteService.getMovimientosByCtaCteId(id);
	        
	        if (movimientos.isEmpty()) {
	        	logger.info(APP, "No se encontraron movimientos para el número de teléfono: {}", phoneNumber);
	            return new ResponseEntity<>(new ApiResponse(true, msg.getMessage("movement.notFound", null, LocaleContextHolder.getLocale())), HttpStatus.OK);
	        }
	        logger.info(APP, "Se encontraron movimientos para el número de teléfono: {}", phoneNumber);
	        return new ResponseEntity<>(movimientos, HttpStatus.OK);
	    }
}
