package com.gestion.alumno_service.controller;

import com.gestion.alumno_service.dto.AlumnoRequestDTO;
import com.gestion.alumno_service.dto.AlumnoResponseDTO;
import com.gestion.alumno_service.mapper.AlumnoMapper;
import com.gestion.alumno_service.service.IAlumnoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/alumnos")
@RequiredArgsConstructor
public class EstudianteController {

    private final IAlumnoService service;
    private final AlumnoMapper mapper;

    @GetMapping
    public Mono<ResponseEntity<Flux<AlumnoResponseDTO>>> findAll(@RequestParam(value = "estado", required = false, defaultValue = "ACTIVO") String estado) {
        return Mono.just(
                ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(
                                service.findAllByEstado(estado)
                                        .map(mapper::convertToDto)));
    }

    @PostMapping
    public Mono<ResponseEntity<AlumnoResponseDTO>> save(@Valid @RequestBody Mono<AlumnoRequestDTO> alumno) {
        return alumno
                .map(mapper::convertToEntity)
                .flatMap(service::save)
                .map(e ->
                        ResponseEntity
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.convertToDto(e))
                );
    }


}

