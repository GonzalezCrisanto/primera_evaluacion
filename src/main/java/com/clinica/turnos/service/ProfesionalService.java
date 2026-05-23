package com.clinica.turnos.service;

import com.clinica.turnos.dto.DTOProfesional;
import com.clinica.turnos.exception.RecursoNoEncontradoException;
import com.clinica.turnos.model.Profesional;
import com.clinica.turnos.repository.ProfesionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfesionalService {

    @Autowired
    private ProfesionalRepository profesionalRepository;

    private DTOProfesional toDTO(Profesional profesional) {
        return new DTOProfesional(profesional.getCompleteName(), profesional.getSpecialty());
    }

    public DTOProfesional create(Profesional profesional) {
        return toDTO(profesionalRepository.save(profesional));
    }

    public Profesional findById(Long id) {
        Profesional profesional = profesionalRepository.findById(id);
        if (profesional == null) {
            throw new RecursoNoEncontradoException("Professional with id " + id + " not found");
        }
        return profesional;
    }

    public List<DTOProfesional> findBySpecialty(String specialty) {
        return profesionalRepository.findBySpeciality(specialty)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Profesional findEntityById(Long id) {
        Profesional profesional = profesionalRepository.findById(id);
        if (profesional == null) {
            throw new RecursoNoEncontradoException("Professional with id " + id + " not found");
        }
        return profesional;
    }
}