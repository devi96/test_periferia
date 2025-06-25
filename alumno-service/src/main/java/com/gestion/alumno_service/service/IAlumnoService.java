package com.gestion.alumno_service.service;

import com.gestion.alumno_service.model.Alumno;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAlumnoService{
    Mono<Alumno> save(Alumno entity);
    Flux<Alumno> findAllByEstado(String estado);

}
