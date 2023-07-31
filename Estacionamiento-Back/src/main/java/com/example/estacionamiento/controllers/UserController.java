package com.example.estacionamiento.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.estacionamiento.models.CtaCteModel;
import com.example.estacionamiento.models.MovimentModel;
import com.example.estacionamiento.models.ParkingModel;
import com.example.estacionamiento.models.UserModel;
import com.example.estacionamiento.models.VehicleModel;
import com.example.estacionamiento.services.CtaCteService;
import com.example.estacionamiento.services.ParkingService;
import com.example.estacionamiento.services.UserService;
import com.example.estacionamiento.services.VehicleService;

import jakarta.annotation.security.PermitAll;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
    private UserService userService;
	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private CtaCteService ctacteService;
	@Autowired
	private ParkingService parkingService;
	
	@PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserModel user) {
		UserModel aux = userService.getUserByPhoneNumber(user.getPhoneNumber());
        if (aux != null && aux.getPassword().equals(user.getPassword()) ) {
            return ResponseEntity.ok().body(null);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
	
	@PostMapping("/add")
	@PermitAll
    public ResponseEntity<String> crearUsuario(@RequestBody UserModel user) {
			String userPhone = user.getPhoneNumber();
			boolean isValid = userPhone.matches("^\\d{10}$");
	   		UserModel usuarioAux= userService.getUserByPhoneNumber(user.getPhoneNumber());
      		System.out.print(user);
      		//Verifica que el usuario no exista y que cumpla con que sean 10 numeros
    		if (usuarioAux == null && isValid) {
    			   UserModel createdUser = userService.crearUsuario(user);
    	           return ResponseEntity.ok("USUARIO CREADO EXITOSAMENTE");
    		}
    		else {
    			return ResponseEntity.badRequest().body("Error: Usuario no pudo ser creado.");
    		}      
    }
    
    @GetMapping("/list")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(users);
        }
    }
    
    @GetMapping("/{phoneNumber}")
    public ResponseEntity<UserModel> getUserByPhoneNumber(@PathVariable String phoneNumber) {
        UserModel user = userService.getUserByPhoneNumber(phoneNumber);

        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(user);
        }
    }
    
    //METODO PARA GUARDAR UN VEHICULO , SE FIJA QUE LA PATENTE NO ESTE REGISTRADA EN EL SISTEMA Y CUMPLA CON EL PATRON  
    @PostMapping("/{phoneNumber}/add")
    public ResponseEntity<?> agregarVehiculo(@PathVariable("phoneNumber") String phoneNumber, @RequestBody VehicleModel vehiculo) { 	    	
    	UserModel usuario = userService.getUserByPhoneNumber(phoneNumber);
        String patente = vehiculo.getPatente();  
        patente = patente.toUpperCase();
        
        //VERIFICA QUE CUMPLA CON EL PATRONN AAA111 O AA111AA
        boolean cumple = patente.matches("[A-Z]{2}\\d{3}[A-Z]{2}") || patente.matches("[A-Z]{3}\\d{3}");    	
               
        if (usuario == null || !cumple || vehicleService.existePatente(patente)) { 
            return ResponseEntity.notFound().build();
        }
        
        userService.addVehicleToUser(phoneNumber,vehiculo);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{phoneNumber}/ListVehicles")
    public ResponseEntity<List<VehicleModel>> getUserVehicles(@PathVariable String phoneNumber) {
        List<VehicleModel> vehicles = userService.getUserVehicles(phoneNumber);

        if (vehicles.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(vehicles);
        }
    }
    
    @GetMapping("/{phoneNumbe}/movimientos")
    public ResponseEntity<List<MovimentModel>> getUserAccountMoviment(@PathVariable String phoneNumbe) {
    	
        CtaCteModel cuentaCorriente = userService.getUserAccount(phoneNumbe);
        List<MovimentModel> movimientos = ctacteService.getMoviments(cuentaCorriente.getId());
        
        if (cuentaCorriente != null) {
            return ResponseEntity.ok(cuentaCorriente.getMovimientos());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{phoneNumbe}/cta")
    public ResponseEntity<CtaCteModel> getUserAccount(@PathVariable String phoneNumbe) {
        CtaCteModel cuentaCorriente = userService.getUserAccount(phoneNumbe);
        if (cuentaCorriente != null) {
            return ResponseEntity.ok(cuentaCorriente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{phoneNumbe}/saldo")
    public ResponseEntity<Double> getUserAccountBalance(@PathVariable String phoneNumbe) {
        CtaCteModel cuentaCorriente = userService.getUserAccount(phoneNumbe);
        if (cuentaCorriente != null) {
            return ResponseEntity.ok(cuentaCorriente.getSaldo());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
         
    //METODOS PARA TRABAJAR CON LA CUENTA CORRIENTE , -CARGA DE SALDO- 
    
    @PostMapping("/{phoneNumber}/sumar")
    public ResponseEntity<?> sumarSaldoCuentaCorriente(@PathVariable("phoneNumber")String phoneNumber, @RequestBody double monto) {
        UserModel usuario = userService.getUserByPhoneNumber(phoneNumber);
        if ((usuario == null) || (monto < 100)) {
        	Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "No se pudo realizar la carga - verificar usuario y monto (minimo $100)");
            return ((BodyBuilder) ResponseEntity.notFound()).body(response);
            
        }

        ctacteService.cargarCuentaCorriente(usuario.getCuentaCorriente().getId(), monto,"Ingreso");
        
        return ResponseEntity.ok().build();
    }
    
    
    //metodo para pruebas nomas
    @PostMapping("/{phoneNumber}/restar")
    public ResponseEntity<?> restarCuentaCorriente(@PathVariable("phoneNumber") String phoneNumber, @RequestBody double monto) {
    	UserModel usuario = userService.getUserByPhoneNumber(phoneNumber);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        ctacteService.cargarCuentaCorriente(usuario.getCuentaCorriente().getId(), -monto,"Egreso");      
        return ResponseEntity.ok().build(); 
    }
    
  
    @GetMapping("/{phoneNumbe}/estacionamientoActivo")
    public ResponseEntity<?> getParking(@PathVariable String phoneNumbe) {
        ParkingModel estacionamiento = parkingService.getUserParkingA(phoneNumbe);
        if (estacionamiento != null) {
            return ResponseEntity.ok(estacionamiento);
        } else {
        	Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "No tiene estacionamiento ACTIVO");
            return ((BodyBuilder) ResponseEntity.notFound()).body(response);
        }
    }
   
   
}
