package com.gestion.alumno_service.service.impl;

import com.gestion.alumno_service.exception.AlumnoDuplicadoException;
import com.gestion.alumno_service.model.Alumno;
import com.gestion.alumno_service.repository.IAlumnoRepo;
import com.gestion.alumno_service.service.IAlumnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AlumnoServiceImpl implements IAlumnoService {

    private final IAlumnoRepo repo;

    @Override
    public Mono<Alumno> save(Alumno alumno) {
        return repo.existsById(alumno.getId())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new AlumnoDuplicadoException("El alumno con ID ya existe"));
                    } else {
                        return repo.save(alumno);
                    }
                });
    }

    @Override
    public Flux<Alumno> findAllByEstado(String estado) {
        if (estado == null || estado.isBlank()) {
            return repo.findAll();
        } else {
            return repo.findByEstado(estado);
        }
    }
}
