package com.r2s.java_backend_06.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.r2s.java_backend_06.model.Role;
import com.r2s.java_backend_06.security.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.duration}")
	private long jwtDuration;

	public String generateToken(final CustomUserDetails user) {
		Map<String, Object> claims = new HashMap<>();
		var expirationMillis = new Date(System.currentTimeMillis() + 1000 * jwtDuration); // 7 days

		return Jwts.builder()
				.setClaims(claims)
				.setSubject(user.getUsername())
				.claim("role", user.getRoles().stream().map(Role::getName).toList())
				.setIssuedAt(new Date())
				.setExpiration(expirationMillis)
				.signWith(getSignKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
}

