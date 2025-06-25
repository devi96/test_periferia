package com.gestion.alumno_service.service;

import com.gestion.alumno_service.model.Alumno;
import com.gestion.alumno_service.model.EstadoAlumno;
import com.gestion.alumno_service.repository.IAlumnoRepo;
import com.gestion.alumno_service.service.impl.AlumnoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

public class AlumnoServiceImplTest {

    @Mock
    private IAlumnoRepo repo;

    @InjectMocks
    private AlumnoServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_ShouldSave_WhenIdDoesNotExist() {
        Alumno alumno = new Alumno(1L, "Juan", "Perez", 22, EstadoAlumno.ACTIVO);

        when(repo.existsById(1L)).thenReturn(Mono.just(false));
        when(repo.save(alumno)).thenReturn(Mono.just(alumno));

        StepVerifier.create(service.save(alumno))
                .expectNext(alumno)
                .verifyComplete();
    }

    @Test
    void save_ShouldReturnError_WhenIdAlreadyExists() {
        Alumno alumno = new Alumno(1L, "Juan", "Perez", 22, EstadoAlumno.ACTIVO);

        when(repo.existsById(1L)).thenReturn(Mono.just(true));

        StepVerifier.create(service.save(alumno))
                .expectErrorMessage("El alumno con ID ya existe")
                .verify();
        verify(repo, never()).save(any());
    }

    @Test
    void findAllByEstado_ShouldReturnAllActive_WhenEstadoIsBlank() {
        Alumno a1 = new Alumno(1L, "Juan", "Perez", 22, EstadoAlumno.ACTIVO);
        Alumno a2 = new Alumno(2L, "Ana", "Lopez", 21, EstadoAlumno.ACTIVO);

        when(repo.findByEstado("ACTIVO")).thenReturn(Flux.just(a1,a2));

        StepVerifier.create(service.findAllByEstado(""))
                .expectNext(a1,a2)
                .verifyComplete();
    }

    @Test
    void findAllByEstado_ShouldFilterByEstado() {
        Alumno a1 = new Alumno(1L, "Juan", "Perez", 22, EstadoAlumno.INACTIVO);

        when(repo.findByEstado("INACTIVO")).thenReturn(Flux.just(a1));

        StepVerifier.create(service.findAllByEstado("INACTIVO"))
                .expectNext(a1)
                .verifyComplete();
    }
}