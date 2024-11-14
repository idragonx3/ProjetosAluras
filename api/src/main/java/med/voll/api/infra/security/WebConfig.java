package med.voll.api.infra.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // Permite requisições para todos os endpoints
				.allowedOrigins("/*") // Substitua pelo seu domínio do frontend
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
				.allowedHeaders("*") // Todos os cabeçalhos são permitidos
				.allowCredentials(true); // Permitir credenciais (cookies ou tokens)
	}
}