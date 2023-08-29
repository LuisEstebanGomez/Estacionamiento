package ar.edu.unlp.estacionamiento.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private static final Logger logger = LogManager.getLogger(JWTAuthenticationFilter.class);
    private static final Marker APP = MarkerManager.getMarker("APP");
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		logger.info(APP, "Intento de autenticación con JWT");

		AuthCredentials authCredentials = new AuthCredentials();

		try {
			authCredentials = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
		} catch (StreamReadException e) {
			// TODO Auto-generated catch block
			logger.error(APP, "Error al leer el flujo de entrada: {}", e.getMessage());
		} catch (DatabindException e) {
			// TODO Auto-generated catch block
			 logger.error(APP, "Error al convertir los datos: {}", e.getMessage());
		} catch (java.io.IOException e) {
			// TODO Auto-generated catch block
			logger.error(APP, "Error de E/S: {}", e.getMessage());
		}
	
		
		UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
				authCredentials.getPhoneNumber(),
				authCredentials.getPassword(),
				Collections.emptyList());
	
		return getAuthenticationManager().authenticate(usernamePAT);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, 
											HttpServletResponse response, 
											FilterChain chain,
											Authentication authResult) throws java.io.IOException, ServletException {
		UserDetailsImpl userDetails = (UserDetailsImpl)authResult.getPrincipal();
		// TODO Auto-generated method stub
		String token = TokenUtils.createToken(userDetails.getUsername(), userDetails.getEmail());
		
		response.addHeader("Authorization", "Bearer " + token);//28
		response.getWriter().flush();
		
		super.successfulAuthentication(request, response, chain, authResult);
	}

}
