package com.clinica.turnos.service;

import com.clinica.turnos.exception.DatoInvalidoException;
import com.clinica.turnos.exception.RecursoNoEncontradoException;
import com.clinica.turnos.model.Paciente;
import com.clinica.turnos.model.Profesional;
import com.clinica.turnos.model.Turno;
import com.clinica.turnos.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TurnoService {

    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private ProfesionalService profesionalService;

    public Turno register(Long pacienteId, Long profesionalId, LocalDate fecha) {
        Paciente paciente = pacienteService.findById(pacienteId);
        Profesional profesional = profesionalService.findById(profesionalId);

        if (turnoRepository.DuplicateExist(pacienteId, profesionalId, fecha)) {
            throw new DatoInvalidoException("An appointment already exists for that patient, professional and date");
        }

        Turno turno = new Turno();
        turno.setPaciente(paciente);
        turno.setProfesional(profesional);
        turno.setDate(fecha);

        return turnoRepository.save(turno);
    }

    public List<Turno> findAll() {
        return turnoRepository.findAll();
    }

    public List<Turno> findByFecha(LocalDate fecha) {
        return turnoRepository.findByDate(fecha);
    }

    public void delete(Long id) {
        if (turnoRepository.findById(id) == null) {
            throw new RecursoNoEncontradoException("Appointment with id " + id + " not found");
        }
        turnoRepository.deleteById(id);
    }
}