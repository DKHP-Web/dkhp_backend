package dkhpweb.dkhp_backend;

import dkhpweb.dkhp_backend.configs.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtConfig.class)
public class DkhpBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DkhpBackendApplication.class, args);
    }

}
