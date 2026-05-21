package com.clinica.turnos.service;

import com.clinica.turnos.exception.RecursoNoEncontradoException;
import com.clinica.turnos.model.Paciente;
import com.clinica.turnos.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente create(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public Paciente findById(Long id) {
        Paciente paciente = pacienteRepository.findById(id);
        if (paciente == null) {
            throw new RecursoNoEncontradoException("Patient with id " + id + " not found");
        }
        return paciente;
    }

    public List<Paciente> findAll() {
        return pacienteRepository.findAll();
    }

    public void delete(Long id) {
        if (pacienteRepository.findById(id) == null) {
            throw new RecursoNoEncontradoException("Patient with id " + id + " not found");
        }
        pacienteRepository.deleteById(id);
    }
}