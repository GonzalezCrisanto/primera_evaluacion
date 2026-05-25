package com.clinica.turnos.service;

import com.clinica.turnos.dto.DTOPaciente;
import com.clinica.turnos.dto.DTOProfesional;
import com.clinica.turnos.dto.TurnoResponseDTO;
import com.clinica.turnos.exception.DatoInvalidoException;
import com.clinica.turnos.exception.RecursoNoEncontradoException;
import com.clinica.turnos.model.Paciente;
import com.clinica.turnos.model.Profesional;
import com.clinica.turnos.model.Turno;
import com.clinica.turnos.repository.ITurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Service responsible for handling business logic related to medical appointments.
 * Validates patient and professional existence before registering an appointment.
 * Prevents duplicate appointments for the same patient, professional and date.
 */
@Service
public class TurnoService {

    @Autowired
    private ITurnoRepository turnoRepository;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private ProfesionalService profesionalService;

    private static final Logger logger = LogManager.getLogger(TurnoService.class);

    /**
     * Registers a new appointment after validating that the patient and professional exist
     * and that no duplicate appointment exists for the same patient, professional and date.
     *
     * @param turno the appointment to register, containing patient and professional IDs and date
     * @return a {@link TurnoResponseDTO} with the registered appointment's data
     * @throws RecursoNoEncontradoException if the patient or professional do not exist
     * @throws DatoInvalidoException if a duplicate appointment already exists
     */
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

    /**
     * Retrieves all registered appointments.
     *
     * @return list of {@link TurnoResponseDTO}
     */
    public List<TurnoResponseDTO> findAll() {
        logger.info("Listing all appointments");
        return turnoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all appointments for a specific date.
     *
     * @param date the date to filter by
     * @return list of {@link TurnoResponseDTO} matching the date
     */
    public List<TurnoResponseDTO> findByDate(LocalDate date) {
        logger.info("Listing appointments for date: {}", date);
        return turnoRepository.findByDate(date)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Deletes an appointment by its ID.
     * Throws {@link RecursoNoEncontradoException} if the appointment does not exist.
     *
     * @param id the appointment's ID
     */
    public void delete(Long id) {
        logger.info("Deleting appointment with id: {}", id);
        if (turnoRepository.findById(id) == null) {
            logger.error("Appointment with id {} not found", id);
            throw new RecursoNoEncontradoException("Appointment with id " + id + " not found");
        }
        turnoRepository.deleteById(id);
    }

    /**
     * Retrieves all appointments within a date range.
     *
     * @param from the start date (inclusive)
     * @param to the end date (inclusive)
     * @return list of {@link TurnoResponseDTO} within the date range
     */
    public List<TurnoResponseDTO> findByDateRange(LocalDate from, LocalDate to) {
        return turnoRepository.findByDateRange(from, to)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converts a {@link Turno} entity to a {@link TurnoResponseDTO}.
     * Maps patient and professional data to their respective DTOs.
     *
     * @param turno the appointment entity
     * @return a {@link TurnoResponseDTO}
     */
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