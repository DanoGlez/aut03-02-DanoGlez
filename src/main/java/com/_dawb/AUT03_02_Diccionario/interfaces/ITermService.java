package com._dawb.AUT03_02_Diccionario.interfaces;

import com._dawb.AUT03_02_Diccionario.models.Term;

import java.util.List;
import java.util.Optional;

public interface ITermService {
    List<Term> getAll();
    List<Term> findFirst30ByOrderByIdAsc();
    Optional<Term> findByEsp(String termEsp);
    Optional<Term> findByEng(String termEng);
    Term getById(int id);
    void add(Term termino);
    void update(Term termino);
    void delete(Integer id);
}
