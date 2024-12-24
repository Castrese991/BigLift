package com.big_lift.palestra.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;



@Component
@Slf4j
public class JwtUtils
{
	@Value("${jwt.secret}")
	private String secretkey;

	@Value("${jwt.expire}")
	private Long jwtExpire;

	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userName);
	}

	private String createToken(Map<String, Object> claims, String userName) {
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + jwtExpire);
		return Jwts.builder()
				.claims(claims)
				.subject(userName)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(expireDate) // Token valid for 30 minutes
				.signWith(getKey())
				.compact();
	}


//	private Key key(){
//		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretkey));
//	}

	private SecretKey getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretkey);
		return Keys.hmacShaKeyFor(keyBytes);
	}


	// Extract all claims from the token
	private Claims getClaimsFromToken(String token) {
		return
		Jwts.parser()
				.verifyWith(getKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	public boolean isTokenValid(String token) {
		try {
			Claims claims = getClaimsFromToken(token);
			return !claims.getExpiration().before(new Date());
		} catch (Exception e) {
			log.debug("JwtUtils class {}", String.valueOf(e));
			return false;
		}
	}

	public String getUsernameFromToken(String token) {
		return getClaimsFromToken(token).getSubject();
	}

}
