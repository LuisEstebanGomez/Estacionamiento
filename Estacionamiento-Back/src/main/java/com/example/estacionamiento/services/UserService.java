package com.example.estacionamiento.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.estacionamiento.models.CtaCteModel;
import com.example.estacionamiento.models.UserModel;
import com.example.estacionamiento.models.VehicleModel;
import com.example.estacionamiento.repositories.IUserRepository;
import java.util.Collections;
import java.util.List;
@Service
public class UserService {
	
	@Autowired
    private IUserRepository userRepository;
	private CtaCteService CtaCteService;
	
	public UserService(IUserRepository userRepository, CtaCteService ctaCteService) {
	    this.userRepository = userRepository;
	    this.CtaCteService = ctaCteService;
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

    public UserModel addVehicleToUser(String phoneNumber, VehicleModel vehicle) {
	    UserModel user = getUserByPhoneNumber(phoneNumber);
        if (user!=null) {
            vehicle.setUser(user);
            user.getVehiculos().add(vehicle);
            userRepository.save(user);
            return user;
        } else {
            return null;
        }
    }

	public UserModel agregarCuentaCorriente(String phoneNumber, CtaCteModel cuentaCorriente) {
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
    
	    CtaCteModel cuentaCorriente = CtaCteService.createCtaCte();
	
	    // Asignar la cuenta corriente al usuario
	    user.setCuentaCorriente(cuentaCorriente);
	    String pass = user.getPassword();
	    String passw = new BCryptPasswordEncoder().encode(pass);
	    user.setPassword(passw);
	    // Guardar el usuario en la base de datos
	    return userRepository.save(user);
	}

	public void saveUser(UserModel user) {
		userRepository.save(user);
	}
	
	public CtaCteModel getUserAccount(String phoneNumbe) {
		UserModel user = userRepository.findByPhoneNumber(phoneNumbe);
		if (user != null) {
	        return user.getCuentaCorriente();
	    }
	    return null;
	}
	
}
