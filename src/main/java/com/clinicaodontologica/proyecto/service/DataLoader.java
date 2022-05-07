package com.clinicaodontologica.proyecto.service;

import com.clinicaodontologica.proyecto.entities.AppUser;
import com.clinicaodontologica.proyecto.entities.AppUsuarioRoles;
import com.clinicaodontologica.proyecto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    UserRepository userRepository;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
        String pass= passwordEncoder.encode("paualonso_");

        userRepository.save(new AppUser("Paula","paula.alonso","paula@fakemail.com",pass, AppUsuarioRoles.ROLE_USER));
        userRepository.save(new AppUser("Paula","paula.alonso","paulaalonso@mail.com",pass, AppUsuarioRoles.ROLE_ADMIN));
    }
}