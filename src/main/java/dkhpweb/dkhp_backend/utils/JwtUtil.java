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
		Date currentDate = new Date();
		Long expiration= switch(tokenType){
			case ACCESS_TOKEN -> jwtConfig.accessTokenExpiration();
			case REFRESH_TOKEN -> jwtConfig.refreshTokenExpiration();
			case TEMP_PASSWORD -> jwtConfig.tempPasswordTokenExpiration();
			default -> throw new IllegalArgumentException("Unknown token type: " + tokenType);
		};
		Date expireDate = new Date(currentDate.getTime() + expiration*1000);

		Map<String, String> claims= new HashMap();
		claims.put("sub", user.getId());
		claims.put("tokenType", tokenType.toString());
		if(tokenType==TokenType.ACCESS_TOKEN) claims.put("role", user.getRole().toString());
		String token= Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(currentDate)
				.setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS512, jwtConfig.secret().getBytes())
				.compact();
		return jwtConfig.prefix()+token;
	}

	public Claims getClaimsFromToken(String bearerToken) {
		try {
			String token= bearerToken.substring(jwtConfig.prefix().length());
			Claims claims = Jwts.parserBuilder()
					.setSigningKey(jwtConfig.secret().getBytes())
					.build()
					.parseClaimsJws(token)
					.getBody();

			return claims;
		} catch (Exception ex) {
			throw new AuthorizationDeniedException("JWT token was exprired or incorrect");
		}
	}

	public TokenDataDto getDataFromToken(String bearerToken){
		var claims= getClaimsFromToken(bearerToken);

		var tokenData= new TokenDataDto();
		tokenData.setUserId(claims.getSubject());
		if(claims.containsKey("sub"))
			tokenData.setUserId(claims.get("sub",String.class));
		if(claims.containsKey("tokenType"))
			tokenData.setTokenType(TokenType.valueOf(claims.get("tokenType", String.class)));
		else throw new IllegalArgumentException("Invalid token");
		if(claims.containsKey("role"))
			tokenData.setRole(UserRole.valueOf(claims.get("role", String.class)));

		return tokenData;
	}
}
