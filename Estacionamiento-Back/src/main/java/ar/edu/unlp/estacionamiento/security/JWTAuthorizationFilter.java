package ar.edu.unlp.estacionamiento.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component 
public class JWTAuthorizationFilter extends OncePerRequestFilter {
	

   
	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain filterChain) throws ServletException, IOException {
	
	
		//aca se pueden agregar los roles o los servicios
		
		String bearerToken = request.getHeader("Authorization");
		
		if(bearerToken != null && bearerToken.startsWith("Bearer")) {
			String token = bearerToken.replace("Bearer", "");
			UsernamePasswordAuthenticationToken usernamePat = TokenUtils.getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(usernamePat);
		}
		filterChain.doFilter(request, response);
	}
	
//  Para probar 	
//	public static void main(String[] args) {
//		System.out.println("pass: " + new BCryptPasswordEncoder().encode("1234"));
//		
//	}

}
