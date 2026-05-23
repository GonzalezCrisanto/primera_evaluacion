package com.clinica.turnos.service;

import com.clinica.turnos.dto.DTOPaciente;
import com.clinica.turnos.dto.DTOProfesional;
import com.clinica.turnos.dto.TurnoResponseDTO;
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
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class TurnoService {

    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private ProfesionalService profesionalService;

    private static final Logger logger = LogManager.getLogger(TurnoService.class);

    public TurnoResponseDTO register(Turno turno) {
        logger.info("Registering appointment for patient id: {} and professional id: {}",
                turno.getPaciente().getId(), turno.getProfesional().getId());
        Paciente paciente = pacienteService.findEntityById(turno.getPaciente().getId());
        Profesional profesional = profesionalService.findEntityById(turno.getProfesional().getId());

        if (turnoRepository.DuplicateExist(paciente.getId(), profesional.getId(), turno.getDate())) {
            logger.error("Duplicate appointment for patient id: {} professional id: {} and date: {}",
                    paciente.getId(), profesional.getId(), turno.getDate());
            throw new DatoInvalidoException("An appointment already exists for that patient, professional and date");
        }

        turno.setPaciente(paciente);
        turno.setProfesional(profesional);
        return toDTO(turnoRepository.save(turno));
    }

    public List<TurnoResponseDTO> findAll() {
        logger.info("Listing all appointments");
        return turnoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<TurnoResponseDTO> findByDate(LocalDate date) {
        logger.info("Listing appointments for date: {}", date);
        return turnoRepository.findByDate(date)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        logger.info("Deleting appointment with id: {}", id);
        if (turnoRepository.findById(id) == null) {
            logger.error("Appointment with id {} not found", id);
            throw new RecursoNoEncontradoException("Appointment with id " + id + " not found");
        }
        turnoRepository.deleteById(id);
    }

    public List<TurnoResponseDTO> findByDateRange(LocalDate from, LocalDate to) {
        return turnoRepository.findByDateRange(from, to)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private TurnoResponseDTO toDTO(Turno turno) {
        DTOPaciente dtoPaciente = new DTOPaciente(
                turno.getPaciente().getName(),
                turno.getPaciente().getLastName()
        );
        DTOProfesional dtoProfesional = new DTOProfesional(
                turno.getProfesional().getCompleteName(),
                turno.getProfesional().getSpecialty()
        );
        return new TurnoResponseDTO(turno.getId(), dtoPaciente, dtoProfesional, turno.getDate());
    }
}