package com._dawb.AUT03_02_Diccionario.interfaces;

import com._dawb.AUT03_02_Diccionario.models.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    void addUser(User user);
    void updateUserRoles(User user);
    Optional<User> findBynickName(String nickName);
    Optional<User> findByEmail(String email);
    List<User> getAll();
}
