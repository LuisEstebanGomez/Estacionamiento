package com.example.estacionamiento.security;

import java.sql.Date;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class TokenUtils {

	private final static String ACCESS_TOKEN_SECRET = "4d6d1b7e570edb4497a1416f153db1f58b23423d566a5c3aef0c73990d2c142e";
	private final static Long ACCESS_TOKEN_VALIDITY_SECONDS= 2_592_00L;
	
	public static String createToken(String phoneNumber, String email ) {
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
			return new UsernamePasswordAuthenticationToken(phoneNumber, null, Collections.emptyList());
			
	}
		catch(JwtException e) {
			return null;
		}
}
}