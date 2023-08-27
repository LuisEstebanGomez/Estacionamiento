package ar.edu.unlp.estacionamiento.controllers;

import java.util.List;
import java.util.Optional;

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

	 @GetMapping("/{phoneNumber}/movements")
	    public ResponseEntity<?> getMovimientos(@PathVariable String phoneNumber) {
	        UserModel user = userRepository.findByPhoneNumber(phoneNumber);
	        if (user == null) {
	            return new ResponseEntity<>(new ApiResponse(false, msg.getMessage("user.notFound", null, LocaleContextHolder.getLocale())), HttpStatus.NOT_FOUND);
	        }
	        
	        Long id = user.getId();
	        List<MovementModel> movimientos = ctaCteService.getMovimientosByCtaCteId(id);
	        
	        if (movimientos.isEmpty()) {
	            return new ResponseEntity<>(new ApiResponse(true, msg.getMessage("movement.notFound", null, LocaleContextHolder.getLocale())), HttpStatus.OK);
	        }
	        
	        return new ResponseEntity<>(movimientos, HttpStatus.OK);
	    }
}
