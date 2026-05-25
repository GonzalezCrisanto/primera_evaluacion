package com.clinica.turnos.repository;

import com.clinica.turnos.model.Profesional;

import java.util.List;

public interface IProfesionalRepository {
    Profesional save(Profesional profesional);
    Profesional findById(Long id);
    List<Profesional> findAll();
    List<Profesional> findBySpeciality(String especialidad);
}