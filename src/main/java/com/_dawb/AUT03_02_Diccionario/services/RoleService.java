package com._dawb.AUT03_02_Diccionario.services;

import com._dawb.AUT03_02_Diccionario.interfaces.IRoleService;
import com._dawb.AUT03_02_Diccionario.models.Role;
import com._dawb.AUT03_02_Diccionario.repositories.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements IRoleService {
    @Autowired
    public IRoleRepository RR;

    public List<Role> getAll() {
        return RR.findAll();
    }

    public Role getById(int id) {
        return RR.findById(id).orElse(null);
    }

    public Optional<Role> findByName(String name){
        return RR.findByName(name);
    };

    public void add(Role rol) {
        RR.save(rol);
    }

    public void update(Role rol) {
        RR.save(rol);
    }

    public void delete(Integer id) {
        RR.deleteById(id);
    }

    public void getByName(String name) {
        RR.findByName(name);
    }
}
