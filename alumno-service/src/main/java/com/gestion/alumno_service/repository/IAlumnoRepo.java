package com.gestion.alumno_service.repository;
import com.gestion.alumno_service.model.Alumno;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface IAlumnoRepo extends ReactiveMongoRepository<Alumno, Long> {
    Flux<Alumno> findByEstado(String estado);
}
