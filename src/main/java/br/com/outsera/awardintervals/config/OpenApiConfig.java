package br.com.outsera.awardintervals.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Golden Raspberry Awards API")
						.description("API to analyze intervals between Golden Raspberry Awards (Worst Picture) consecutive wins by producers")
						.version("1.0.0")
						.contact(new Contact()
								.name("Awards API Team")
								.email("api@example.com")));
	}
}