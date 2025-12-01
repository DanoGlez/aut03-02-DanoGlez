package com._dawb.AUT03_02_Diccionario.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Example {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @NotNull
    @NotBlank(message = "Este campo no puede estar en blanco")
    @Size(min = 5, max = 100, message = "La frase de ejemplo ha de tener entre 5 y 100 caracteres")
    private String exEsp;

    @NotNull
    @NotBlank(message = "Este campo no puede estar en blanco")
    @Size(min = 5, max = 100, message = "La frase de ejemplo ha de tener entre 5 y 100 caracteres")
    private String exEng;

    private String author;

    @ManyToOne
    @JoinColumn(name = "term_id", nullable = false)
    @NotNull
    private Term term;

    // CONTRUCTORES:
    public Example() {}

    public Example(String exEsp, String exEng, String author, Term term) {
        this.exEsp = exEsp;
        this.exEng = exEng;
        this.author = author;
        this.term = term;
    }

    public Example (String exEsp, String exEng, Term term) {
        this.exEsp = exEsp;
        this.exEng = exEng;
        this.term = term;
    }
}
