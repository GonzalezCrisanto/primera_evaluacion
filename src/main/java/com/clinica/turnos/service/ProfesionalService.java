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

/**
 * Service responsible for handling business logic related to medical professionals.
 * Delegates persistence operations to {@link ProfesionalRepository}.
 */
@Service
public class ProfesionalService {
    @Autowired
    private ProfesionalRepository profesionalRepository;

    private static final Logger logger = LogManager.getLogger(ProfesionalService.class);

    /**
     * Creates a new professional and returns a DTO with their basic information.
     *
     * @param profesional the professional to create
     * @return a {@link DTOProfesional} with the created professional's data
     */
    public DTOProfesional create(Profesional profesional) {
        logger.info("Creating professional: {}", profesional.getCompleteName());
        return toDTO(profesionalRepository.save(profesional));
    }

    /**
     * Retrieves the full {@link Profesional} entity by ID.
     * Used internally by {@link TurnoService} to build appointments.
     * Throws {@link RecursoNoEncontradoException} if the professional does not exist.
     *
     * @param id the professional's ID
     * @return the full {@link Profesional} entity
     */
    public Profesional findEntityById(Long id) {
        logger.info("Searching professional with id: {}", id);
        Profesional profesional = profesionalRepository.findById(id);
        if (profesional == null) {
            logger.error("Professional with id {} not found", id);
            throw new RecursoNoEncontradoException("Professional with id " + id + " not found");
        }
        return profesional;
    }

    /**
     * Retrieves all professionals filtered by specialty.
     *
     * @param specialty the medical specialty to filter by
     * @return list of {@link DTOProfesional} matching the specialty
     */
    public List<DTOProfesional> findBySpecialty(String specialty) {
        logger.info("Listing professionals with specialty: {}", specialty);
        return profesionalRepository.findBySpeciality(specialty)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converts a {@link Profesional} entity to a {@link DTOProfesional}.
     *
     * @param profesional the professional entity
     * @return a {@link DTOProfesional}
     */
    private DTOProfesional toDTO(Profesional profesional) {
        return new DTOProfesional(profesional.getCompleteName(), profesional.getSpecialty());
    }
}