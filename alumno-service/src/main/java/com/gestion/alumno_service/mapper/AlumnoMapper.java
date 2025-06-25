package com.gestion.alumno_service.mapper;

import com.gestion.alumno_service.dto.AlumnoRequestDTO;
import com.gestion.alumno_service.dto.AlumnoResponseDTO;
import com.gestion.alumno_service.model.Alumno;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlumnoMapper {

    private final ModelMapper modelMapper;

    public AlumnoResponseDTO convertToDto(Alumno a) {
        return modelMapper.map(a, AlumnoResponseDTO.class);
    }
    public Alumno convertToEntity(AlumnoRequestDTO dto) {
        return modelMapper.map(dto, Alumno.class);
    }
}
