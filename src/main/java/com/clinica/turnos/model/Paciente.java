package com.clinica.turnos.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Paciente {
    private Long id;
    @NotBlank(message = "Names is required")
    private String name;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @NotBlank(message = "DNI is required")
    private String dni;
    @Email(message = "Email is not valid")
    @NotBlank(message = "Email is required")
    private String email;
}
