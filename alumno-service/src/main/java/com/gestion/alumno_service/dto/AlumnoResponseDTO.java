package com.gestion.alumno_service.dto;

import com.gestion.alumno_service.model.EstadoAlumno;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoResponseDTO {
    @Id
    private long id;
    private String nombre;
    private String apellido;
    private int edad;
    private EstadoAlumno estado;
}
