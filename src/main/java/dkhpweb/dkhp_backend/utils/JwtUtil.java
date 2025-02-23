package dkhpweb.dkhp_backend.utils;

import java.util.Date;

import dkhpweb.dkhp_backend.constants.TokenType;
import dkhpweb.dkhp_backend.configs.JwtConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
@RequiredArgsConstructor
public class JwtUtil {
	private final JwtConfig jwtConfig;

	public String generateToken(Authentication auth, TokenType tokenType) {
		String id=auth.getName();
		Date currentDate = new Date();
		Long expiration= (tokenType==TokenType.ACCESS_TOKEN)?jwtConfig.accessTokenExpiration():jwtConfig.refreshTokenExpiration();
		Date expireDate = new Date(currentDate.getTime() + expiration);

		String token= Jwts.builder()
				.setSubject(id)
				.setIssuedAt(currentDate)
				.setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS512, jwtConfig.secret().getBytes())
				.compact();
		return jwtConfig.prefix()+token;
	}

	public Claims getClaimsFromToken(String token) {
		try {
			Claims claims = Jwts.parser()
					.setSigningKey(jwtConfig.secret().getBytes())
					.build()
					.parseClaimsJws(token)
					.getBody();

			return claims;
		} catch (Exception ex) {
			throw new AuthenticationCredentialsNotFoundException("JWT was exprired or incorrect",ex.fillInStackTrace());
		}
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser()
			.setSigningKey(jwtConfig.secret())
			.build()
			.parseClaimsJws(token);
			return true;
		} catch (Exception ex) {
			throw new AuthenticationCredentialsNotFoundException("JWT was exprired or incorrect",ex.fillInStackTrace());
		}
	}
}
