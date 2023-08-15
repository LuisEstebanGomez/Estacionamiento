package ar.edu.unlp.estacionamiento.controllers;

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

import ar.edu.unlp.estacionamiento.models.AccountModel;
import ar.edu.unlp.estacionamiento.models.MovementModel;
import ar.edu.unlp.estacionamiento.models.ParkingModel;
import ar.edu.unlp.estacionamiento.models.UserModel;
import ar.edu.unlp.estacionamiento.models.VehicleModel;
import ar.edu.unlp.estacionamiento.security.dto.ApiResponse;
import ar.edu.unlp.estacionamiento.services.AccountService;
import ar.edu.unlp.estacionamiento.services.ParkingService;
import ar.edu.unlp.estacionamiento.services.UserService;
import ar.edu.unlp.estacionamiento.services.VehicleService;
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
	private AccountService ctacteService;
	@Autowired
	private ParkingService parkingService;
	
	@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserModel user) {
		UserModel aux = userService.getUserByPhoneNumber(user.getPhoneNumber());
		if (aux != null && aux.getPassword().equals(user.getPassword())) {
            return new ResponseEntity<>(new ApiResponse(true, "Inicio de sesión exitoso"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Inicio de sesión fallido"), HttpStatus.UNAUTHORIZED);
        }
    }
	
	@PostMapping("/add")
	@PermitAll 
	public ResponseEntity<ApiResponse> crearUsuario(@RequestBody UserModel user) {
        UserModel usuarioAux = userService.getUserByPhoneNumber(user.getPhoneNumber());
        if (usuarioAux == null) {
            UserModel createdUser = userService.crearUsuario(user);
            if (createdUser != null) {
                return new ResponseEntity<>(new ApiResponse(true, "Usuario creado exitosamente"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse(false, "Error: Usuario no pudo ser creado"), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Error: Usuario ya se encuentra registrado"), HttpStatus.BAD_REQUEST);
        }
    }

    
	public ResponseEntity<?> getAllUsers() {
        List<UserModel> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(false, "No se encontraron usuarios"), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }
    
    @GetMapping("/{phoneNumber}")
    public ResponseEntity<?> getUserByPhoneNumber(@PathVariable String phoneNumber) {

            UserModel user = userService.getUserByPhoneNumber(phoneNumber);

            if (user == null) {
                return new ResponseEntity<>(new ApiResponse(false, "Usuario no encontrado"), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        }
    
//    //METODO PARA GUARDAR UN VEHICULO , SE FIJA QUE LA PATENTE NO ESTE REGISTRADA EN EL SISTEMA Y CUMPLA CON EL PATRON  
//    @PostMapping("/{phoneNumber}/add") //CORREJIR METODO
//    public ResponseEntity<?> agregarVehiculo(@PathVariable("phoneNumber") String phoneNumber, @RequestBody VehicleModel vehiculo) { 	    	
//    	UserModel usuario = userService.getUserByPhoneNumber(phoneNumber);
//        String patente = vehiculo.getPatente();  
//        patente = patente.toUpperCase();
//        
//        //VERIFICA QUE CUMPLA CON EL PATRONN AAA111 O AA111AA
//        boolean cumple = patente.matches("[A-Z]{2}\\d{3}[A-Z]{2}") || patente.matches("[A-Z]{3}\\d{3}");    	
//               
//        if (usuario == null || !cumple || vehicleService.existePatente(patente)) { 
//            return ResponseEntity.notFound().build();
//        }
//        
//        userService.addVehicleToUser(phoneNumber,vehiculo);
//        return ResponseEntity.ok().build();
//    }

//    
    @PostMapping("/{phoneNumber}/add") //CORREJIR METODO
    public ResponseEntity<?> agregarVehiculo(@PathVariable("phoneNumber") String phoneNumber, @RequestBody VehicleModel vehiculo) { 
    	String msg="";
    	boolean ok=false;
    	UserModel usuario = userService.getUserByPhoneNumber(phoneNumber);
    	if(usuario != null) {
    		userService.addVehicleToUser(phoneNumber,vehiculo,ok,msg);
    		if(ok) {
    			return new ResponseEntity<>(new ApiResponse(true,msg),HttpStatus.OK);
    		}else {
    			return new ResponseEntity<>(new ApiResponse(false,msg),HttpStatus.NOT_FOUND);
    		}
    	}
    	else {
    		return new ResponseEntity<>(new ApiResponse(false,msg),HttpStatus.NOT_FOUND);
    	}
    } 
    
//    @PostMapping("/{phoneNumber}/add")
//    public ResponseEntity<ApiResponse> agregarVehiculo(@PathVariable("phoneNumber") String phoneNumber, @RequestBody VehicleModel vehiculo) {
//        UserModel usuario = userService.addVehicleToUser(phoneNumber, vehiculo);
//
//        if (usuario != null) {
//            return new ResponseEntity<>(new ApiResponse(true, "Vehiculo Agregado Exitosamente"), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(new ApiResponse(false, "No se pudo agregar el vehiculo"), HttpStatus.NOT_FOUND);
//        }
//    }
    
    @GetMapping("/{phoneNumber}/ListVehicles")
    public ResponseEntity<?> getUserVehicles(@PathVariable String phoneNumber) {
        List<VehicleModel> vehicles = userService.getUserVehicles(phoneNumber);

        if (vehicles.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(false, "No se encontraron vehículos"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(vehicles, HttpStatus.OK);
        }
    }
    
    @GetMapping("/{phoneNumbe}/movimientos")
    public ResponseEntity<?> getUserAccountMoviment(@PathVariable String phoneNumbe) {
        AccountModel cuentaCorriente = userService.getUserAccount(phoneNumbe);
        if (cuentaCorriente != null) {
            List<MovementModel> movimientos = ctacteService.getMoviments(cuentaCorriente.getId());
            if (movimientos.isEmpty()) {
                return new ResponseEntity<>(new ApiResponse(false, "No se encontraron movimientos de cuenta"), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>( movimientos, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Cuenta corriente no encontrada"), HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/{phoneNumbe}/cta")
    public ResponseEntity<?> getUserAccount(@PathVariable String phoneNumbe) {
        AccountModel cuentaCorriente = userService.getUserAccount(phoneNumbe);
        if (cuentaCorriente != null) {
            return new ResponseEntity<>(cuentaCorriente, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Cuenta corriente no encontrada"), HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/{phoneNumbe}/saldo")
    public ResponseEntity<?> getUserAccountBalance(@PathVariable String phoneNumbe) {
        AccountModel cuentaCorriente = userService.getUserAccount(phoneNumbe);
        if (cuentaCorriente != null) {
            return new ResponseEntity<>(cuentaCorriente.getSaldo(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Cuenta corriente no encontrada"), HttpStatus.NOT_FOUND);
        }
    }
         
    //METODOS PARA TRABAJAR CON LA CUENTA CORRIENTE , -CARGA DE SALDO- //CORREJIR METODO
    
    @PostMapping("/{phoneNumber}/sumar")
    public ResponseEntity<ApiResponse> sumarSaldoCuentaCorriente(@PathVariable("phoneNumber") String phoneNumber, @RequestBody double monto) {
        UserModel usuario = userService.getUserByPhoneNumber(phoneNumber);
        if ((usuario == null) || (monto < 100)) {
            return new ResponseEntity<>(new ApiResponse(false, "No se pudo realizar la carga - verificar usuario y monto (mínimo $100)"), HttpStatus.BAD_REQUEST);
        }

        ctacteService.cargarCuentaCorriente(usuario.getAccount().getId(), monto, "Ingreso");

        return new ResponseEntity<>(new ApiResponse(true, "Carga realizada exitosamente"), HttpStatus.OK);
    }

    //metodo para pruebas nomas
   @PostMapping("/{phoneNumber}/restar") //CORREJIR METODO
   public ResponseEntity<ApiResponse> restarCuentaCorriente(@PathVariable("phoneNumber") String phoneNumber, @RequestBody double monto) {
       UserModel usuario = userService.getUserByPhoneNumber(phoneNumber);
       if (usuario == null) {
           return new ResponseEntity<>(new ApiResponse(false, "Usuario no encontrado"), HttpStatus.NOT_FOUND);
       }

       ctacteService.cargarCuentaCorriente(usuario.getAccount().getId(), -monto, "Egreso");
       return new ResponseEntity<>(new ApiResponse(true, "Egreso realizado exitosamente"), HttpStatus.OK);
   }

   
  
    @GetMapping("/{phoneNumbe}/estacionamientoActivo") //CORREJIR METODO
    public ResponseEntity<?> getParking(@PathVariable String phoneNumbe) {
        ParkingModel estacionamiento = parkingService.getUserParkingA(phoneNumbe);
        if (estacionamiento != null) {
            return new ResponseEntity<>(estacionamiento, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "No tiene estacionamiento activo"), HttpStatus.NOT_FOUND);
        }
    }
   
   
}
