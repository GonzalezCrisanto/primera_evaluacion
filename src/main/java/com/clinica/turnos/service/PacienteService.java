package com.clinica.turnos.service;

import com.clinica.turnos.dto.DTOPaciente;
import com.clinica.turnos.exception.RecursoNoEncontradoException;
import com.clinica.turnos.model.Paciente;
import com.clinica.turnos.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    private DTOPaciente toDTO(Paciente paciente) {
        return new DTOPaciente(paciente.getName(), paciente.getLastName());
    }

    public DTOPaciente create(Paciente paciente) {
        return toDTO(pacienteRepository.save(paciente));
    }

    public DTOPaciente findById(Long id) {
        Paciente paciente = pacienteRepository.findById(id);
        if (paciente == null) {
            throw new RecursoNoEncontradoException("Patient with id " + id + " not found");
        }
        return toDTO(paciente);
    }

    public List<DTOPaciente> findAll() {
        return pacienteRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        if (pacienteRepository.findById(id) == null) {
            throw new RecursoNoEncontradoException("Patient with id " + id + " not found");
        }
        pacienteRepository.deleteById(id);
    }

    public Paciente findEntityById(Long id) {
        Paciente paciente = pacienteRepository.findById(id);
        if (paciente == null) {
            throw new RecursoNoEncontradoException("Patient with id " + id + " not found");
        }
        return paciente;
    }
}