package dkhpweb.dkhp_backend.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="jwt")
public record JwtConfig(
        String header,
        String prefix,
        String secret,
        Long accessTokenExpiration,
        Long refreshTokenExpiration
){}
