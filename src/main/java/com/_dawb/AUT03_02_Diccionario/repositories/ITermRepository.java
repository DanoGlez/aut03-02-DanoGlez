package com._dawb.AUT03_02_Diccionario.repositories;

import com._dawb.AUT03_02_Diccionario.models.Term;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ITermRepository extends JpaRepository<Term, Integer>{
    List<Term> findFirst30ByOrderByIdAsc();
    Optional<Term> findByEsp(String esp);
    Optional<Term> findByEng(String esp);

}
