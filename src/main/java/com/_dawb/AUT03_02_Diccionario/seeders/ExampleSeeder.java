package com._dawb.AUT03_02_Diccionario.seeders;

import com._dawb.AUT03_02_Diccionario.models.Example;
import com._dawb.AUT03_02_Diccionario.models.Term;
import com._dawb.AUT03_02_Diccionario.repositories.IExampleRepository;
import com._dawb.AUT03_02_Diccionario.repositories.ITermRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Optional;

@Component
public class ExampleSeeder {

    @Bean
    public CommandLineRunner seedExamples(IExampleRepository exampleRepo, ITermRepository termRepo) {
        return args -> {
            // Solo se ejecuta el seeder si la tabla está vacía
            if (exampleRepo.count() == 0) {
                System.out.println("La tabla 'examples' está vacía. Seeding iniciado...");

                // Manejo de errores al abrir el archivo
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(new ClassPathResource("data/examples.csv").getInputStream()))
                ) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(";");
                        if (parts.length == 3) {
                            String termEsp = parts[0].trim();
                            String exEsp = parts[1].trim();
                            String exEng = parts[2].trim();

                            // Buscar término en español o inglés
                            Optional<Term> termOpt = termRepo.findByEsp(termEsp);
                            if (termOpt.isEmpty()) {
                                termOpt = termRepo.findByEng(termEsp);
                            }

                            if (termOpt.isPresent()) {
                                Term term = termOpt.get();

                                // Evitar duplicados
                                boolean exists = exampleRepo.existsByExEspAndExEngAndTerm(exEsp, exEng, term);
                                if (!exists) {
                                    Example example = new Example(exEsp, exEng, term);
                                    exampleRepo.save(example);
                                }
                            } else {
                                System.out.println("Término no encontrado: " + termEsp);
                            }
                        }
                    }
                    System.out.println("Seeding completado.");
                } catch (IOException e) {
                    // Capturamos cualquier error de lectura del archivo
                    throw new Exception("Error al leer el archivo examples.csv: " + e.getMessage());
                }
            } else {
                System.out.println("La tabla 'examples' ya contiene datos. Seeding omitido.");
            }
        };
    }
}