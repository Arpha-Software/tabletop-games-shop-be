package org.arpha.core.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Tabletop-games-shop",
                version = "1.0",
                description = "API"
        ),
        servers = {@Server(url = "/"), @Server(url = "https://tgs-test-aab09ba6a40b.herokuapp.com"),
                @Server(url = "https://tgs-test-aab09ba6a40b.herokuapp.com/swagger-ui/index.html"),
                @Server(url = "http://localhost:3000")}
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpeApiConfig {
}
