package com.clinica.turnos.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Turno {
    private Long id;
    private Paciente paciente;
    private Profesional profesional;
    private LocalDate date;
}
