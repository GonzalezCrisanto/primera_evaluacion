package com.clinica.turnos.service;

import com.clinica.turnos.dto.DTOPaciente;
import com.clinica.turnos.exception.RecursoNoEncontradoException;
import com.clinica.turnos.model.Paciente;
import com.clinica.turnos.repository.IPacienteRepository;
import com.clinica.turnos.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Service responsible for handling business logic related to patients.
 * Delegates persistence operations to {@link PacienteRepository}.
 */
@Service
public class PacienteService {

    @Autowired
    private IPacienteRepository pacienteRepository;

    private static final Logger logger = LogManager.getLogger(PacienteService.class);

    /**
     * Creates a new patient and returns a DTO with their basic information.
     *
     * @param paciente the patient to create
     * @return a {@link DTOPaciente} with the created patients data
     */
    public DTOPaciente create(Paciente paciente) {
        logger.info("Creating patient: {} {}", paciente.getName(), paciente.getLastName());
        return toDTO(pacienteRepository.save(paciente));
    }

    /**
     * Retrieves a patient by their ID and returns a DTO.
     * Throws {@link RecursoNoEncontradoException} if the patient does not exist.
     *
     * @param id the patients ID
     * @return a {@link DTOPaciente} with the patients data
     */
    public DTOPaciente findById(Long id) {
        logger.info("Searching patient with id: {}", id);
        Paciente paciente = pacienteRepository.findById(id);
        if (paciente == null) {
            logger.error("Patient with id {} not found", id);
            throw new RecursoNoEncontradoException("Patient with id " + id + " not found");
        }
        return toDTO(paciente);
    }

    /**
     * Retrieves all patients and returns them as a list of DTOs.
     *
     * @return list of {@link DTOPaciente}
     */
    public List<DTOPaciente> findAll() {
        logger.info("Listing all patients");
        return pacienteRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Deletes a patient by their ID.
     * Throws {@link RecursoNoEncontradoException} if the patient does not exist.
     *
     * @param id the patients ID
     */
    public void delete(Long id) {
        logger.info("Deleting patient with id: {}", id);
        if (pacienteRepository.findById(id) == null) {
            logger.error("Patient with id {} not found", id);
            throw new RecursoNoEncontradoException("Patient with id " + id + " not found");
        }
        pacienteRepository.deleteById(id);
    }

    /**
     * Retrieves the full {@link Paciente} entity by ID.
     * Used internally by {@link TurnoService} to build appointments.
     * Throws {@link RecursoNoEncontradoException} if the patient does not exist.
     *
     * @param id the patient's ID
     * @return the full {@link Paciente} entity
     */
    public Paciente findEntityById(Long id) {
        Paciente paciente = pacienteRepository.findById(id);
        if (paciente == null) {
            logger.error("Patient with id {} not found", id);
            throw new RecursoNoEncontradoException("Patient with id " + id + " not found");
        }
        return paciente;
    }

    /**
     * Converts a {@link Paciente} entity to a {@link DTOPaciente}.
     * Only exposes name and last name, hiding sensitive data.
     *
     * @param paciente the patient entity
     * @return a {@link DTOPaciente}
     */
    private DTOPaciente toDTO(Paciente paciente) {
        return new DTOPaciente(paciente.getName(), paciente.getLastName());
    }
}