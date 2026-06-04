package com.clinica.turnos.repository;

import com.clinica.turnos.model.Paciente;

import java.util.List;

public interface IPacienteRepository {
    Paciente save(Paciente paciente);
    Paciente findById(Long id);
    List<Paciente> findAll();
    void deleteById(Long id);
}