package com._dawb.AUT03_02_Diccionario.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "TERMS")
@Getter
@Setter
public class Term {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    @NotNull(message = "Este campo es obligatorio")
    @NotBlank(message = "Este campo es obligatorio")
    private String esp;

    @NotNull(message = "Este campo es obligatorio")
    @NotBlank(message = "Este campo es obligatorio")
    private String eng;

    @OneToMany(mappedBy = "term", cascade = CascadeType.ALL)
    private List<Example> examples;

    public int getExampleCount() {
        return examples != null ? examples.size() : 0;
    }
}
