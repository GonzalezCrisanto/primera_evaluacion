package com.clinica.turnos.service;

import com.clinica.turnos.dto.TurnoResponseDTO;
import com.clinica.turnos.exception.DatoInvalidoException;
import com.clinica.turnos.exception.RecursoNoEncontradoException;
import com.clinica.turnos.model.Paciente;
import com.clinica.turnos.model.Profesional;
import com.clinica.turnos.model.Turno;
import com.clinica.turnos.repository.TurnoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TurnoServiceTest {

    @Mock
    private TurnoRepository turnoRepository;

    @Mock
    private PacienteService pacienteService;

    @Mock
    private ProfesionalService profesionalService;

    @InjectMocks
    private TurnoService turnoService;

    private Paciente paciente;
    private Profesional profesional;
    private Turno turno;

    @BeforeEach
    void setUp() {
        paciente = new Paciente();
        paciente.setId(1L);
        paciente.setName("Juan");
        paciente.setLastName("Perez");
        paciente.setDni("12345678");
        paciente.setEmail("juan.perez@gmail.com");

        profesional = new Profesional();
        profesional.setId(1L);
        profesional.setCompleteName("Dr. Carlos Lopez");
        profesional.setSpecialty("Clinica");

        turno = new Turno();
        turno.setId(1L);
        turno.setPaciente(paciente);
        turno.setProfesional(profesional);
        turno.setDate(LocalDate.of(2026, 6, 10));
    }

    @Test
    void register_ShouldReturnTurnoResponseDTO_WhenTurnoIsValid() {
        when(pacienteService.findEntityById(1L)).thenReturn(paciente);
        when(profesionalService.findEntityById(1L)).thenReturn(profesional);
        when(turnoRepository.DuplicateExist(1L, 1L, LocalDate.of(2026, 6, 10))).thenReturn(false);
        when(turnoRepository.save(turno)).thenReturn(turno);

        TurnoResponseDTO result = turnoService.register(turno);

        assertNotNull(result);
        assertEquals("Juan", result.getPaciente().getName());
        assertEquals("Dr. Carlos Lopez", result.getProfesional().getCompleteName());
        verify(turnoRepository, times(1)).save(turno);
    }

    @Test
    void register_ShouldThrowException_WhenTurnoIsDuplicate() {
        when(pacienteService.findEntityById(1L)).thenReturn(paciente);
        when(profesionalService.findEntityById(1L)).thenReturn(profesional);
        when(turnoRepository.DuplicateExist(1L, 1L, LocalDate.of(2026, 6, 10))).thenReturn(true);

        assertThrows(DatoInvalidoException.class, () -> turnoService.register(turno));
    }

    @Test
    void register_ShouldThrowException_WhenPacienteNotFound() {
        when(pacienteService.findEntityById(99L)).thenThrow(new RecursoNoEncontradoException("Patient not found"));
        turno.getPaciente().setId(99L);

        assertThrows(RecursoNoEncontradoException.class, () -> turnoService.register(turno));
    }

    @Test
    void findAll_ShouldReturnListOfTurnoResponseDTO() {
        when(turnoRepository.findAll()).thenReturn(List.of(turno));

        List<TurnoResponseDTO> result = turnoService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Juan", result.get(0).getPaciente().getName());
    }

    @Test
    void findByDate_ShouldReturnListOfTurnoResponseDTO_WhenDateExists() {
        when(turnoRepository.findByDate(LocalDate.of(2026, 6, 10))).thenReturn(List.of(turno));

        List<TurnoResponseDTO> result = turnoService.findByDate(LocalDate.of(2026, 6, 10));

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void delete_ShouldDeleteTurno_WhenTurnoExists() {
        when(turnoRepository.findById(1L)).thenReturn(turno);

        turnoService.delete(1L);

        verify(turnoRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_ShouldThrowException_WhenTurnoNotFound() {
        when(turnoRepository.findById(99L)).thenReturn(null);

        assertThrows(RecursoNoEncontradoException.class, () -> turnoService.delete(99L));
    }
}