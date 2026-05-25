package com.clinica.turnos.repository;

import com.clinica.turnos.model.Turno;

import java.time.LocalDate;
import java.util.List;

public interface ITurnoRepository {
    Turno save(Turno turno);
    Turno findById(Long id);
    List<Turno> findAll();
    List<Turno> findByDate(LocalDate date);
    void deleteById(Long id);
    boolean DuplicateExist(Long pacienteId, Long profesionalId, LocalDate date);
    List<Turno> findByDateRange(LocalDate from, LocalDate to);
}