package com.clinica.turnos.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Profesional {
    private Long id;
    @NotBlank(message = "Full name is required")
    private String completeName;
    @NotBlank(message = "Specialty is required")
    private String specialty;
}
