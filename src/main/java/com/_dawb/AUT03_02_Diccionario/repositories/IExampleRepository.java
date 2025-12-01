package com._dawb.AUT03_02_Diccionario.repositories;

import com._dawb.AUT03_02_Diccionario.models.Example;
import com._dawb.AUT03_02_Diccionario.models.Term;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface IExampleRepository extends JpaRepository<Example, Integer> {
    public List<Example> findByTermId(int Id);
    boolean existsByExEspAndExEngAndTerm(String exEsp, String exEng, Term term);
}