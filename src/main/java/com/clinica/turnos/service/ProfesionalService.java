package com.clinica.turnos.service;

import com.clinica.turnos.dto.DTOProfesional;
import com.clinica.turnos.exception.RecursoNoEncontradoException;
import com.clinica.turnos.model.Profesional;
import com.clinica.turnos.repository.ProfesionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class ProfesionalService {
    @Autowired
    private ProfesionalRepository profesionalRepository;

    private static final Logger logger = LogManager.getLogger(ProfesionalService.class);

    public DTOProfesional create(Profesional profesional) {
        logger.info("Creating professional: {}", profesional.getCompleteName());
        return toDTO(profesionalRepository.save(profesional));
    }

    public Profesional findEntityById(Long id) {
        logger.info("Searching professional with id: {}", id);
        Profesional profesional = profesionalRepository.findById(id);
        if (profesional == null) {
            logger.error("Professional with id {} not found", id);
            throw new RecursoNoEncontradoException("Professional with id " + id + " not found");
        }
        return profesional;
    }

    public List<DTOProfesional> findBySpecialty(String specialty) {
        logger.info("Listing professionals with specialty: {}", specialty);
        return profesionalRepository.findBySpeciality(specialty)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private DTOProfesional toDTO(Profesional profesional) {
        return new DTOProfesional(profesional.getCompleteName(), profesional.getSpecialty());
    }
}