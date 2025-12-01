package com._dawb.AUT03_02_Diccionario.seeders;

import com._dawb.AUT03_02_Diccionario.models.Role;
import com._dawb.AUT03_02_Diccionario.models.User;
import com._dawb.AUT03_02_Diccionario.repositories.IRoleRepository;
import com._dawb.AUT03_02_Diccionario.repositories.IUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserSeeder {

    @Bean
    public CommandLineRunner seedUsers(IUserRepository UR, IRoleRepository RR) {
        return args -> {
            // CREAR ROLES (si no existen)
            if (RR.findByName("ROLE_BASIC").isEmpty()) {
                RR.save(new Role("ROLE_BASIC"));
            }
            if (RR.findByName("ROLE_PREMIUM").isEmpty()) {
                RR.save(new Role("ROLE_PREMIUM"));
            }
            if (RR.findByName("ROLE_ADMIN").isEmpty()) {
                RR.save(new Role("ROLE_ADMIN"));
            }

            // BUSCAR ROLES
            Role basic = RR.findByName("ROLE_BASIC").orElseThrow();
            Role premium = RR.findByName("ROLE_PREMIUM").orElseThrow();
            Role admin = RR.findByName("ROLE_ADMIN").orElseThrow();

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            // CREAR USUARIOS UNO POR UNO (si no existen)
            if (UR.findByEmail("basic@diccionario.com").isEmpty()) {
                User usuario1 = new User("BASIC", "España", "basic@diccionario.com",
                        encoder.encode("Basic123!"), basic);
                UR.save(usuario1);
            }

            if (UR.findByEmail("premium@diccionario.com").isEmpty()) {
                User usuario2 = new User("PREMIUM", "Inglés", "premium@diccionario.com",
                        encoder.encode("Premium123!"), premium);
                UR.save(usuario2);
            }

            if (UR.findByEmail("admin@diccionario.com").isEmpty()) {
                User usuario3 = new User("ADMIN", "Alemán", "admin@diccionario.com",
                        encoder.encode("Admin123!"), admin);
                UR.save(usuario3);
            }
        };
    }
}