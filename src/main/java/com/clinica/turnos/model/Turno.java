package com.clinica.turnos.model;

import com.clinica.turnos.dto.DTOPaciente;
import com.clinica.turnos.dto.DTOProfesional;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Turno {
    private Long id;
    private DTOPaciente dtoPaciente;
    private DTOProfesional dtoProfesional;
    private LocalDate fecha;
}
