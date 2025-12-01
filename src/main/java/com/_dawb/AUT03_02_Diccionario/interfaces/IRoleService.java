package com._dawb.AUT03_02_Diccionario.interfaces;

import com._dawb.AUT03_02_Diccionario.models.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {
    List<Role> getAll();
    Role getById(int id);
    Optional<Role> findByName(String name);
    void add(Role rol);
    void update(Role rol);
    void delete(Integer id);
    void getByName(String name);
}
