
package ug.visituganda.visituganda.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Visit Uganda API",
                version = "1.0",
                description = "Customer & Business Registration + JWT Auth"
        )
)
public class SwaggerConfig {
}