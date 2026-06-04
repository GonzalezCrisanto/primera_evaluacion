# Primera Evaluación - Gestor de Turnos Médicos

API REST desarrollada con Spring Boot para gestionar turnos médicos, pacientes y profesionales de una clínica.

## Tecnologías utilizadas

- Java 17
- Spring Boot 3.5.14
- Spring Web MVC
- Spring Validation
- Lombok
- Log4j2
- JUnit 5
- Mockito
- Maven

## Estructura del proyecto

```
src/main/java/com/clinica/turnos/
├── controller/               # Controladores REST
│   ├── PacienteController.java
│   ├── ProfesionalController.java
│   ├── TurnoController.java
│   └── TestCargaInicial.java
├── service/                  # Lógica de negocio
│   ├── PacienteService.java
│   ├── ProfesionalService.java
│   └── TurnoService.java
├── repository/               # Almacenamiento en memoria
│   ├── IPacienteRepository.java
│   ├── IProfesionalRepository.java
│   ├── ITurnoRepository.java
│   ├── PacienteRepository.java
│   ├── ProfesionalRepository.java
│   └── TurnoRepository.java
├── model/                    # Entidades
│   ├── Paciente.java
│   ├── Profesional.java
│   └── Turno.java
├── dto/                      # Data Transfer Objects
│   ├── DTOPaciente.java
│   ├── DTOProfesional.java
│   └── TurnoResponseDTO.java
└── exception/                # Manejo de excepciones
    ├── RecursoNoEncontradoException.java
    ├── DatoInvalidoException.java
    └── GlobalExceptionHandler.java
```

## Cómo correr la aplicación

### Requisitos
- Java 17 o superior
- Maven 3.x

### Pasos

1. Clonar el repositorio
git clone https://github.com/GonzalezCrisanto/primera_evaluacion.git

2. Entrar al directorio
cd primera_evaluacion

3. Correr la aplicación
./mvnw spring-boot:run

La aplicación levanta en http://localhost:8080 y carga automáticamente 2 pacientes, 2 profesionales y 3 turnos de prueba.

## Endpoints

### Pacientes

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/pacientes` | Crear paciente |
| GET | `/pacientes` | Listar todos |
| GET | `/pacientes/{id}` | Obtener por ID |
| DELETE | `/pacientes/{id}` | Eliminar paciente |

Ejemplo POST /pacientes:
{
    "name": "Juan",
    "lastName": "Perez",
    "dni": "12345678",
    "email": "juan.perez@gmail.com"
}

### Profesionales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/profesionales` | Crear profesional |
| GET | `/profesionales?specialty=...` | Listar por especialidad |

Ejemplo POST /profesionales:
{
    "completeName": "Dr. Carlos Lopez",
    "specialty": "Clinica"
}

### Turnos

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/turnos` | Registrar turno |
| GET | `/turnos` | Listar todos |
| GET | `/turnos/fecha/{date}` | Listar por fecha |
| GET | `/turnos/filter?from=...&to=...` | Listar por rango de fechas |
| DELETE | `/turnos/{id}` | Eliminar turno |

Ejemplo POST /turnos:
{
    "paciente": { "id": 1 },
    "profesional": { "id": 1 },
    "date": "2026-06-10"
}

Ejemplo GET /turnos/filter:
GET /turnos/filter?from=2026-06-10&to=2026-06-12

## Manejo de errores

La API devuelve respuestas de error uniformes en el siguiente formato:

{
    "timestamp": "2026-05-23T12:00:00",
    "status": 404,
    "error": "Resource not found",
    "message": "Patient with id 99 not found"
}

| Código | Descripción |
|--------|-------------|
| 400 | Dato inválido o turno duplicado |
| 404 | Recurso no encontrado |
| 500 | Error interno del servidor |

## Tests

Para correr los tests unitarios:
./mvnw test

Los tests cubren la lógica de negocio de los tres services:
- PacienteServiceTest
- ProfesionalServiceTest
- TurnoServiceTest

## Autor

Gonzalez Cristanto
