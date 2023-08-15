package ar.edu.unlp.estacionamiento.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ar.edu.unlp.estacionamiento.models.AccountModel;
import ar.edu.unlp.estacionamiento.models.UserModel;
import ar.edu.unlp.estacionamiento.models.VehicleModel;
import ar.edu.unlp.estacionamiento.repositories.IUserRepository;

import java.util.Collections;
import java.util.List;
@Service
public class UserService {
	
	@Autowired
    private IUserRepository userRepository;
	
	@Autowired
	private AccountService CtaCteService;
	
	@Autowired
	private VehicleService vehicleService;
	
	public UserService(IUserRepository userRepository, AccountService ctaCteService, VehicleService vehicleService) {
	    this.userRepository = userRepository;
	    this.CtaCteService = ctaCteService;
	    this.vehicleService= vehicleService;
	 }
	
    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }
    
    public UserModel getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public List<VehicleModel> getUserVehicles(String phoneNumber) {
    	UserModel user = getUserByPhoneNumber(phoneNumber);
        if (user != null) {
            return user.getVehiculos();
        } else {
            return Collections.emptyList();
        }
    }

    public UserModel addVehicleToUser(String phoneNumber, VehicleModel vehicle, boolean ok, String msg) {
	    UserModel user = getUserByPhoneNumber(phoneNumber);

	    String patente = vehicle.getPatente();  
	    patente = patente.toUpperCase();
     
        //VERIFICA QUE CUMPLA CON EL PATRONN AAA111 O AA111AA
        boolean cumple = patente.matches("[A-Z]{2}\\d{3}[A-Z]{2}") || patente.matches("[A-Z]{3}\\d{3}");    	
             
        if (user != null) {
        	if(cumple) {
        		if(!vehicleService.existePatente(patente)) {
        			 vehicle.setUser(user);
        	            user.getVehiculos().add(vehicle);
        	            userRepository.save(user);
        	            ok=true;
        	            msg="Vehiculo Agregado Exitosamente";
        		}else {
        			msg="El vehiculo ya se encuentra registrado";
        		}
        }else {
        	msg="El usuario no se encuentra en el sistema";
        	return null;
        }
        }
		return user;
    }
 



	public UserModel agregarCuentaCorriente(String phoneNumber, AccountModel cuentaCorriente) {
		UserModel user = getUserByPhoneNumber(phoneNumber);
		if(user != null) {
			user.setCuentaCorriente(cuentaCorriente);
			//cuentaCorriente.setUser(user);
			userRepository.save(user);
			return user;
		}else {
			return null;
		}
		
	}
	
	public UserModel crearUsuario(UserModel user) {
    
	    AccountModel cuentaCorriente = CtaCteService.createCtaCte();
	    String userPhone = user.getPhoneNumber();
		boolean isValid = userPhone.matches("^\\d{10}$");
	
		if(isValid) {
		    // Asignar la cuenta corriente al usuario
		    user.setCuentaCorriente(cuentaCorriente);
		    String pass = user.getPassword();
		    String passw = new BCryptPasswordEncoder().encode(pass);
		    user.setPassword(passw);			
		}
		 // Guardar el usuario en la base de datos
	    return userRepository.save(user);
	}

	public void saveUser(UserModel user) {
		userRepository.save(user);
	}
	
	public AccountModel getUserAccount(String phoneNumbe) {
		UserModel user = userRepository.findByPhoneNumber(phoneNumbe);
		if (user != null) {
	        return user.getAccount();
	    }
	    return null;
	}

	
	
}
