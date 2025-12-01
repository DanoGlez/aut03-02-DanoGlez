package com._dawb.AUT03_02_Diccionario.repositories;

import com._dawb.AUT03_02_Diccionario.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findBynickName(String nickName);
    Optional<User> findByEmail(String email);
}
