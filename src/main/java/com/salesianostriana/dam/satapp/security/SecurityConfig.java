package com.salesianostriana.dam.satapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(){

        UserDetails tecnico = User.withUsername("tecnico")
                .password("{noop}1234")
                .roles("TECNICO")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password("{noop}1234")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(tecnico, admin);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.csrf(csrf -> csrf.disable());
        httpSecurity.authorizeHttpRequests((auth) -> auth
                .requestMatchers("api/incidencia/tecnico/**").hasRole("TECNICO")
                .requestMatchers("api/incidencia/{id}/tecnicoResponsable/**").hasRole("TECNICO")
                .requestMatchers("api/ubicacion/admin/**").hasRole("ADMIN")
                .requestMatchers("api/equipo/admin/**").hasRole("ADMIN")
                .requestMatchers("api/usuario/admin/**").hasRole("ADMIN")
                .requestMatchers("api/categoria/admin/**").hasRole("ADMIN"));
        return httpSecurity.build();
    }



}
