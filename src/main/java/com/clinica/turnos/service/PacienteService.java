package com.clinica.turnos.service;

import com.clinica.turnos.dto.DTOPaciente;
import com.clinica.turnos.exception.RecursoNoEncontradoException;
import com.clinica.turnos.model.Paciente;
import com.clinica.turnos.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    private static final Logger logger = LogManager.getLogger(PacienteService.class);

    public DTOPaciente create(Paciente paciente) {
        logger.info("Creating patient: {} {}", paciente.getName(), paciente.getLastName());
        return toDTO(pacienteRepository.save(paciente));
    }

    public DTOPaciente findById(Long id) {
        logger.info("Searching patient with id: {}", id);
        Paciente paciente = pacienteRepository.findById(id);
        if (paciente == null) {
            logger.error("Patient with id {} not found", id);
            throw new RecursoNoEncontradoException("Patient with id " + id + " not found");
        }
        return toDTO(paciente);
    }

    public List<DTOPaciente> findAll() {
        logger.info("Listing all patients");
        return pacienteRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        logger.info("Deleting patient with id: {}", id);
        if (pacienteRepository.findById(id) == null) {
            logger.error("Patient with id {} not found", id);
            throw new RecursoNoEncontradoException("Patient with id " + id + " not found");
        }
        pacienteRepository.deleteById(id);
    }

    public Paciente findEntityById(Long id) {
        Paciente paciente = pacienteRepository.findById(id);
        if (paciente == null) {
            logger.error("Patient with id {} not found", id);
            throw new RecursoNoEncontradoException("Patient with id " + id + " not found");
        }
        return paciente;
    }

    private DTOPaciente toDTO(Paciente paciente) {
        return new DTOPaciente(paciente.getName(), paciente.getLastName());
    }
}