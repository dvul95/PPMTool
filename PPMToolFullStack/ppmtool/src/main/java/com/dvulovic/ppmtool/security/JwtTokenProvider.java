package com.dvulovic.ppmtool.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import static com.dvulovic.ppmtool.security.SecurityConstants.EXPIRATION_TIME;
import static com.dvulovic.ppmtool.security.SecurityConstants.SECRET;

import com.dvulovic.ppmtool.domain.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
	
	//Method to generate the token

	public String generateToken(Authentication authentication) {
		User user = (User) authentication.getPrincipal();

		Date now = new Date(System.currentTimeMillis());
		Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME);

		String userId = Long.toString(user.getId());

		Map<String, Object> claims = new HashMap<>();
		claims.put("id", (Long.toString(user.getId())));
		claims.put("username", user.getUsername());
		claims.put("fullName", user.getFullName());

		return Jwts.builder().setSubject(userId).setClaims(claims).setIssuedAt(now).setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
	}
	// --------------------------------------------------------------
}
