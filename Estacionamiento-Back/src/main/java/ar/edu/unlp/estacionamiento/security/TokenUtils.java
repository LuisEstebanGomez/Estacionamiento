package ar.edu.unlp.estacionamiento.security;

import java.sql.Date;



import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenUtils {
	
		private static final Logger logger = LogManager.getLogger(JWTAuthenticationFilter.class);
		private static final Marker APP = MarkerManager.getMarker("APP");
	
	   private static String ACCESS_TOKEN_SECRET;
	    private static long ACCESS_TOKEN_VALIDITY_SECONDS;

	    @Value("${jwt.secret}")
	    public void setAccess_token_secret(String access_token_secret) {
	        ACCESS_TOKEN_SECRET = access_token_secret;
	    }

	    @Value("${jwt.expiration}")
	    public void setAccess_token_validity_seconds(long access_token_validity_seconds) {
	        ACCESS_TOKEN_VALIDITY_SECONDS = access_token_validity_seconds;
	    }

	    public static String getAccess_token_secret() {
	        return ACCESS_TOKEN_SECRET;
	    }

	    public static long getAccess_token_validity_seconds() {
	        return ACCESS_TOKEN_VALIDITY_SECONDS;
	    }

	public static String createToken(String phoneNumber, String email ) {
		logger.info(APP, "Creando token para phoneNumber: {}", phoneNumber);
		long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1000;
		Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);
        Claims claims = Jwts.claims().setSubject(phoneNumber);
		
		Map<String, Object> extra = new HashMap<>();
		extra.put("PhoneNumber", phoneNumber);
		
		return Jwts.builder()
				.setClaims(claims)
                .setSubject(phoneNumber)
                .setExpiration(expirationDate)
                .addClaims(extra)
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .compact();
	}
	
	public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
		try {
			Claims claims = Jwts.parserBuilder()
					.setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
					.build()
					.parseClaimsJws(token)
					.getBody();
			
			String phoneNumber = claims.getSubject();	
			logger.info(APP, "Obtenida autenticación para phoneNumber: {}", phoneNumber);
			return new UsernamePasswordAuthenticationToken(phoneNumber, null, Collections.emptyList());
			
	}
		catch(JwtException e) {
			logger.error(APP, "Error al obtener autenticación desde el token: {}", e.getMessage());
			return null;
		}
}
}