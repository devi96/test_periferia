package com.gestion.alumno_service.repository;

import com.gestion.alumno_service.model.Alumno;
import com.gestion.alumno_service.model.EstadoAlumno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataMongoTest
public class IAlumnoRepoTest {
    @Autowired
    private IAlumnoRepo repo;

    @BeforeEach
    void cleanDatabase() {
        repo.deleteAll().block();
    }

    @Test
    void testGuardarYBuscarPorEstado() {
        Alumno alumno = new Alumno(1L, "Juan", "Pérez", 20, EstadoAlumno.ACTIVO);

        StepVerifier.create(repo.save(alumno)
                        .thenMany(repo.findByEstado("ACTIVO")))
                .expectNextMatches(a -> a.getNombre().equals("Juan") && a.getEstado().equals(EstadoAlumno.ACTIVO))
                .verifyComplete();
    }
}
