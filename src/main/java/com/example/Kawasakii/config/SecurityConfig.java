package com.example.Kawasakii.config;

import com.example.Kawasakii.service.impl.CustomUserDetailsService;
import com.example.Kawasakii.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    public SecurityConfig() {
        // constructor vacío: no inyectamos repositorios aquí para evitar ciclos
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UsuarioRepository usuarioRepository) {
        // Crear la instancia manualmente para evitar ciclo de dependencias
        return new CustomUserDetailsService(usuarioRepository);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(org.springframework.security.config.annotation.web.builders.HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/css/**", "/img/**", "/contacto", "/carrito/**", "/login", "/registro", "/registro/**").permitAll()
                        // Protección para rutas de administración (incluye estadísticas)
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/productos/listar", "/productos/nuevo", "/productos/guardar", "/productos/eliminar/**").hasRole("ADMIN")
                        .requestMatchers("/favoritos/**").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        // Redirigir al inicio tras login (no forzar al panel de admin)
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );
        return http.build();
    }
}