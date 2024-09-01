package com.example.goldClub.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.goldClub.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Inyección del filtro JWT para manejar la autenticación basada en tokens
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    // Definición del codificador de contraseñas, usando BCrypt para mayor seguridad
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuración de la seguridad HTTP para definir los permisos de acceso
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Deshabilitar CSRF porque se maneja con tokens JWT
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/api/usuarios/registro", "/api/usuarios/login").permitAll() // Permitir acceso a registro y login sin autenticación
                .anyRequest().authenticated() // Requerir autenticación para todas las demás solicitudes
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Añadir filtro JWT antes del filtro de autenticación de usuario y contraseña
            .formLogin().disable() // Deshabilitar el formulario de login por defecto
            .cors() // Habilitar CORS para permitir solicitudes desde otros dominios
            .and()
            .headers().frameOptions().disable(); // Deshabilitar restricciones de visualización en iframe, útil para pruebas

        return http.build();
    }

    // Configuración de CORS para definir qué orígenes pueden acceder a la API
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Permitir acceso a todos los endpoints
                        .allowedOrigins("https://goldclubfrontend.vercel.app") // Solo permitir solicitudes desde este origen
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos HTTP permitidos
                        .allowedHeaders("*") // Permitir todas las cabeceras
                        .allowCredentials(true); // Permitir el envío de cookies
            }
        };
    }
}
