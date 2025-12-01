package com._dawb.AUT03_02_Diccionario.services;

import com._dawb.AUT03_02_Diccionario.interfaces.IExampleService;
import com._dawb.AUT03_02_Diccionario.models.Example;
import com._dawb.AUT03_02_Diccionario.repositories.IExampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExampleService implements IExampleService {
    @Autowired
    public IExampleRepository ER;

    public List<Example> getAll() {
        return ER.findAll();
    }

    public Example getById(int id) {
        return ER.findById(id).orElse(null);
    }

    public void add(Example example) {
        ER.save(example);
    }

    public void update(Example example) {
        ER.save(example);
    }

    public void delete(Integer id) {
        ER.deleteById(id);
    }

    public List<Example> findByTerm(int Id){
        return ER.findByTermId(Id);
    }
}
