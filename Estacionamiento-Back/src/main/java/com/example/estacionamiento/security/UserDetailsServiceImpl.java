package com.example.estacionamiento.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.estacionamiento.models.UserModel;
import com.example.estacionamiento.repositories.IUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private IUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
		
		UserModel user = userRepository.findByPhoneNumber(phoneNumber);
		if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        // Crea y devuelve tu implementación UserDetailsImpl aquí
       
		return new UserDetailsImpl(user);
	}
	
}
