package com.gestion.alumno_service.exception;

public class AlumnoDuplicadoException extends RuntimeException {
    public AlumnoDuplicadoException(String mensaje) {
        super(mensaje);
    }
}