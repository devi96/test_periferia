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
public class AlumnoRequestDTO{
    @NotNull(message = "El ID es obligatorio")
    private long id;
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;
    @Min(value = 7, message = "La edad debe ser mayor a 6")
    @Max(value = 50, message = "Edad inválida")
    private int edad;
    @NotNull(message = "El estado es obligatorio")
    private EstadoAlumno estado;
}