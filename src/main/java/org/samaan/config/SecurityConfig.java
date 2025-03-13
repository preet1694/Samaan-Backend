package org.samaan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // ✅ Correct CORS config
                // .csrf(csrf -> csrf.disable()) // ❗ Disable CSRF only if needed
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // ✅ Define multiple allowed origins properly
        configuration.setAllowedOrigins(List.of(
                "https://samaan-psi.vercel.app/",
                "https://samaan-preetbrahmbhatt16-gmailcoms-projects.vercel.app",
                "https://samaan-git-main-preetbrahmbhatt16-gmailcoms-projects.vercel.app/",
                "http://localhost:5173"
        ));

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true); // ✅ Needed if using authentication

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
