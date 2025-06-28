package sn.regulation.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info().title("API Regulation Prix Senegal")
                .description("Documentation de l'API pour la régulation des prix au Sénégal.")
                .version("1.0.0"));
    }
}
