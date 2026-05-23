package com.clinica.turnos.service;

import com.clinica.turnos.dto.DTOPaciente;
import com.clinica.turnos.exception.RecursoNoEncontradoException;
import com.clinica.turnos.model.Paciente;
import com.clinica.turnos.repository.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private PacienteService pacienteService;

    private Paciente paciente;

    @BeforeEach
    void setUp() {
        paciente = new Paciente();
        paciente.setId(1L);
        paciente.setName("Juan");
        paciente.setLastName("Perez");
        paciente.setDni("12345678");
        paciente.setEmail("juan.perez@gmail.com");
    }

    @Test
    void create_ShouldReturnDTOPaciente_WhenPacienteIsValid() {
        when(pacienteRepository.save(paciente)).thenReturn(paciente);

        DTOPaciente result = pacienteService.create(paciente);

        assertNotNull(result);
        assertEquals("Juan", result.getName());
        assertEquals("Perez", result.getLastName());
        verify(pacienteRepository, times(1)).save(paciente);
    }

    @Test
    void findById_ShouldReturnDTOPaciente_WhenPacienteExists() {
        when(pacienteRepository.findById(1L)).thenReturn(paciente);

        DTOPaciente result = pacienteService.findById(1L);

        assertNotNull(result);
        assertEquals("Juan", result.getName());
        assertEquals("Perez", result.getLastName());
    }

    @Test
    void findById_ShouldThrowException_WhenPacienteNotFound() {
        when(pacienteRepository.findById(99L)).thenReturn(null);

        assertThrows(RecursoNoEncontradoException.class, () -> pacienteService.findById(99L));
    }

    @Test
    void findAll_ShouldReturnListOfDTOPaciente() {
        when(pacienteRepository.findAll()).thenReturn(List.of(paciente));

        List<DTOPaciente> result = pacienteService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Juan", result.get(0).getName());
    }

    @Test
    void delete_ShouldDeletePaciente_WhenPacienteExists() {
        when(pacienteRepository.findById(1L)).thenReturn(paciente);

        pacienteService.delete(1L);

        verify(pacienteRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_ShouldThrowException_WhenPacienteNotFound() {
        when(pacienteRepository.findById(99L)).thenReturn(null);

        assertThrows(RecursoNoEncontradoException.class, () -> pacienteService.delete(99L));
    }
}