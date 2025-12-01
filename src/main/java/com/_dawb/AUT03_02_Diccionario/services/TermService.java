package com._dawb.AUT03_02_Diccionario.services;

import com._dawb.AUT03_02_Diccionario.interfaces.ITermService;
import com._dawb.AUT03_02_Diccionario.models.Term;
import com._dawb.AUT03_02_Diccionario.repositories.ITermRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TermService implements ITermService {
    @Autowired
    public ITermRepository TR;

    public List<Term> getAll() {
        return TR.findAll();
    }

    public List<Term> findFirst30ByOrderByIdAsc() {
        return TR.findFirst30ByOrderByIdAsc();
    }

    public Optional<Term> findByEsp(String termEsp){
        return TR.findByEsp(termEsp);
    }

    public Optional<Term> findByEng(String termEng){
        return TR.findByEng(termEng);
    }

    public Term getById(int id) {
        return TR.findById(id).orElse(null);
    }

    public void add(Term termino) {
        TR.save(termino);
    }

    public void update(Term termino) {
        TR.save(termino);
    }

    public void delete(Integer id) {
        TR.deleteById(id);
    }
}
