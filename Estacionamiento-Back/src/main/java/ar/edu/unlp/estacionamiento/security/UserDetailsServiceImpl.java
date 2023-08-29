package ar.edu.unlp.estacionamiento.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ar.edu.unlp.estacionamiento.models.UserModel;
import ar.edu.unlp.estacionamiento.repositories.IUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private IUserRepository userRepository;

	private static final Logger logger = LogManager.getLogger(JWTAuthenticationFilter.class);
    private static final Marker APP = MarkerManager.getMarker("APP");
	
	@Override
	public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
		
		UserModel user = userRepository.findByPhoneNumber(phoneNumber);
		if (user == null) {
			logger.warn(APP, "Usuario no encontrado para phoneNumber: {}", phoneNumber);
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        // Crea y devuelve tu implementación UserDetailsImpl aquí
       
		return new UserDetailsImpl(user);
	}
	
}
