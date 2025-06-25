# test_periferia
Prueba técnica para – Java Spring Boot - Periferia IT

## Tecnologías

- Java 17+
- Spring Boot (WebFlux)
- Maven
- MongoDB (Reactive)
- ModelMapper
- JUnit 5
- Mockito

## Endpoints

### `GET /alumnos?estado={estado}`

- Obtiene todos los alumnos filtrados por estado (`ACTIVO` o `INACTIVO`).  
- Si no se envía el parámetro `estado`, retorna los alumnos activos.

**Respuesta exitosa:**  
- Código: 200 OK  
- Cuerpo: Lista de alumnos en formato JSON.

---

### `POST /alumnos`

- Crea un nuevo alumno.

**Cuerpo de la petición:**  
```json
{
  "id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "edad": 20,
  "estado": "ACTIVO"
}
```

**Respuestas:**
- 201 Created: Alumno creado exitosamente.
- 409 Conflict: El ID ya existe.
- 400 Bad Request: Error de validación en los datos enviados.
```
