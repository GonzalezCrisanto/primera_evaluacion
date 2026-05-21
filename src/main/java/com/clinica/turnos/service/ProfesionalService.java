package com.clinica.turnos.service;

import com.clinica.turnos.exception.RecursoNoEncontradoException;
import com.clinica.turnos.model.Profesional;
import com.clinica.turnos.repository.ProfesionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfesionalService {

    @Autowired
    private ProfesionalRepository profesionalRepository;

    public Profesional create(Profesional profesional) {
        return profesionalRepository.save(profesional);
    }

    public Profesional findById(Long id) {
        Profesional profesional = profesionalRepository.findById(id);
        if (profesional == null) {
            throw new RecursoNoEncontradoException("Professional with id " + id + " not found");
        }
        return profesional;
    }

    public List<Profesional> findByEspecialidad(String especialidad) {
        return profesionalRepository.findByEspecialidad(especialidad);
    }
}