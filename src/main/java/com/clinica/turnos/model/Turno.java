package com.clinica.turnos.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Turno {
    private Long id;
    @NotNull(message = "Patient is required")
    private Paciente paciente;
    @NotNull(message = "Professional is required")
    private Profesional profesional;
    @NotNull(message = "Date is required")
    private LocalDate date;
}
