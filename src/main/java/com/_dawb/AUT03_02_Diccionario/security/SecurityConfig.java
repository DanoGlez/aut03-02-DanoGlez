package com._dawb.AUT03_02_Diccionario.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    //Establecemos cual será el passwordEncoder a utilizar por Spring Security
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Estableces las restricciones para usuarios
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authz) -> authz.requestMatchers("/", "/register").permitAll().anyRequest().authenticated());

        http.formLogin((formLogin) -> formLogin.loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/", true).permitAll());

        http.logout((logout) -> logout.logoutUrl("/logout").logoutSuccessUrl("/"));

        return http.build();
    }
}
