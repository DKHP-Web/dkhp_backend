package dkhpweb.dkhp_backend.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import dkhpweb.dkhp_backend.constants.TokenType;
import dkhpweb.dkhp_backend.configs.JwtConfig;
import dkhpweb.dkhp_backend.dtos.Auth.TokenDataDto;
import dkhpweb.dkhp_backend.models.User;
import dkhpweb.dkhp_backend.models.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
@RequiredArgsConstructor
public class JwtUtil {
	private final JwtConfig jwtConfig;

	public String generateToken(User user, TokenType tokenType) {
		String id= user.getId();

		Date currentDate = new Date();
		Long expiration= (tokenType==TokenType.ACCESS_TOKEN)?jwtConfig.accessTokenExpiration():jwtConfig.refreshTokenExpiration();
		Date expireDate = new Date(currentDate.getTime() + expiration);

		var claims= Map.of("tokenType", tokenType.toString());
		if(tokenType==TokenType.ACCESS_TOKEN) claims.put("role", user.getRole().toString());
		String token= Jwts.builder()
				.setSubject(id)
				.claims(claims)
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
			throw new AuthorizationDeniedException("JWT token was exprired or incorrect");
		}
	}

	public TokenDataDto getDataFromAccessToken(String accessToken){
		var claims= getClaimsFromToken(accessToken);
		if(!TokenType.ACCESS_TOKEN.toString().equals(claims.get("tokenType"))){
			throw new AuthorizationDeniedException("Token is invalid");
		}
		return new TokenDataDto(claims.getSubject(), UserRole.valueOf(claims.get("role").toString()));
	}

	public String getUserIdFromRefreshToken(String refreshToken){
		var claims= getClaimsFromToken(refreshToken);
		if(!TokenType.REFRESH_TOKEN.toString().equals(claims.get("tokenType"))){
			throw new AuthorizationDeniedException("Token is invalid");
		}
		return claims.getSubject();
	}
}
