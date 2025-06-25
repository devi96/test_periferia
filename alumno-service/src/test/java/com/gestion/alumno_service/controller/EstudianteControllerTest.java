package com.gestion.alumno_service.controller;

import com.gestion.alumno_service.dto.AlumnoRequestDTO;
import com.gestion.alumno_service.dto.AlumnoResponseDTO;
import com.gestion.alumno_service.exception.AlumnoDuplicadoException;
import com.gestion.alumno_service.mapper.AlumnoMapper;
import com.gestion.alumno_service.model.Alumno;
import com.gestion.alumno_service.model.EstadoAlumno;
import com.gestion.alumno_service.service.IAlumnoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;
@ActiveProfiles("test")
@WebFluxTest(controllers = EstudianteController.class)
class EstudianteControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private IAlumnoService service;

    @MockitoBean
    private AlumnoMapper mapper;

    private AlumnoRequestDTO request;
    private Alumno alumno;
    private AlumnoResponseDTO response;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new AlumnoRequestDTO(1L, "Juan", "Pérez", 20, EstadoAlumno.ACTIVO);
        alumno = new Alumno(1L, "Juan", "Pérez", 20, EstadoAlumno.ACTIVO);
        response = new AlumnoResponseDTO(1L, "Juan", "Pérez", 20, EstadoAlumno.ACTIVO);

    }

    @Test
    void testGuardarAlumno_Created() {
        when(mapper.convertToEntity(request)).thenReturn(alumno);
        when(service.save(alumno)).thenReturn(Mono.just(alumno));

        webTestClient.post()
                .uri("/alumnos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated();
    }
    @Test
    void testGuardarAlumno_IdDuplicado() {
        when(mapper.convertToEntity(request)).thenReturn(alumno);
        when(service.save(alumno)).thenReturn(Mono.error(new AlumnoDuplicadoException("ID ya existe")));

        webTestClient.post()
                .uri("/alumnos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody()
                .jsonPath("$.message").isEqualTo("ID ya existe");
    }
    @Test
    void testFindAllByEstadoActivo() {

        when(service.findAllByEstado("ACTIVO")).thenReturn(Flux.just(alumno));
        when(mapper.convertToDto(alumno)).thenReturn(response);

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/alumnos")
                        .queryParam("estado", "ACTIVO")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").isEqualTo(1)
                .jsonPath("$[0].nombre").isEqualTo("Juan")
                .jsonPath("$[0].apellido").isEqualTo("Pérez")
                .jsonPath("$[0].edad").isEqualTo(20)
                .jsonPath("$[0].estado").isEqualTo("ACTIVO");
    }

}