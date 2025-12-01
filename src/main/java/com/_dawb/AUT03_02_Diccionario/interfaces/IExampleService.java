package com._dawb.AUT03_02_Diccionario.interfaces;

import com._dawb.AUT03_02_Diccionario.models.Example;

import java.util.List;

public interface IExampleService {
    List<Example> getAll();
    Example getById(int id);
    void add(Example example);
    void update(Example example);
    void delete(Integer id);
    List<Example> findByTerm(int Id);
}
