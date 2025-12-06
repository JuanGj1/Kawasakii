package com.example.Kawasakii.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. Define el PasswordEncoder (OBLIGATORIO)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // 2. Gestión de Usuarios (Ejemplo en memoria: admin/password)
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("password") // Contraseña de prueba
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    // 3. Configuración de las reglas de acceso (Filtrado HTTP)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize

                        // 🚨 LÍNEA CORREGIDA: Incluye /login para evitar el ERR_TOO_MANY_REDIRECTS 🚨
                        .requestMatchers("/", "/css/**", "/img/**", "/contacto", "/carrito/**", "/favoritos/**", "/login").permitAll()

                        // RUTAS PROTEGIDAS: Solo para ADMIN (Panel de Productos)
                        .requestMatchers("/productos/listar", "/productos/nuevo", "/productos/guardar", "/productos/eliminar/**").hasRole("ADMIN")

                        // Cualquier otra solicitud requiere autenticación
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/productos/listar", true) // Redirige al panel después del login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // 1. La URL que activa el cierre de sesión (es un POST)
                        .logoutSuccessUrl("/") // 2. Redirige al inicio después del éxito
                        .permitAll() // Permite a cualquiera acceder a este flujo
                );
        return http.build();
    }
}