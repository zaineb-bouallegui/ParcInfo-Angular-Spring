package com.example.stage.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Autorise toutes les origines (changez ceci en fonction de vos besoins)
        config.addAllowedOrigin("*");

        // Autorise toutes les méthodes HTTP (GET, POST, PUT, DELETE, etc.)
        config.addAllowedMethod("*");

        // Autorise tous les en-têtes
        config.addAllowedHeader("*");

        // Autorise l'envoi de cookies par le navigateur
        config.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
