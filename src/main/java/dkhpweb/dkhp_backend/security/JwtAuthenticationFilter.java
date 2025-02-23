package dkhpweb.dkhp_backend.security;

import java.io.IOException;
import java.util.Collections;

import dkhpweb.dkhp_backend.configs.JwtConfig;
import dkhpweb.dkhp_backend.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	private final JwtUtil jwtUtil;
	private final JwtConfig jwtConfig;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String rawJwtStr=request.getHeader(jwtConfig.header());
		if(rawJwtStr!=null && rawJwtStr.startsWith(jwtConfig.prefix())) {
			try {
				handleToken(rawJwtStr);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}

		chain.doFilter(request, response);
	}

	private void handleToken(String rawJwtStr) {
		String token= rawJwtStr.substring(jwtConfig.prefix().length(),rawJwtStr.length());
		if(StringUtils.hasText(token)) {
			var claims= jwtUtil.getClaimsFromToken(token);
			var authToken = new UsernamePasswordAuthenticationToken(
					claims.getSubject(),
					null,
					Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+claims.get("role").toString())));
			SecurityContextHolder.getContext().setAuthentication(authToken);
		}
	}
}
