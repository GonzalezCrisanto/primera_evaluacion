package com.clinica.turnos.service;

import com.clinica.turnos.dto.DTOProfesional;
import com.clinica.turnos.exception.RecursoNoEncontradoException;
import com.clinica.turnos.model.Profesional;
import com.clinica.turnos.repository.ProfesionalRepository;
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
public class ProfesionalServiceTest {

    @Mock
    private ProfesionalRepository profesionalRepository;

    @InjectMocks
    private ProfesionalService profesionalService;

    private Profesional profesional;

    @BeforeEach
    void setUp() {
        profesional = new Profesional();
        profesional.setId(1L);
        profesional.setCompleteName("Dr. Carlos Lopez");
        profesional.setSpecialty("Clinica");
    }

    @Test
    void create_ShouldReturnDTOProfesional_WhenProfesionalIsValid() {
        when(profesionalRepository.save(profesional)).thenReturn(profesional);

        DTOProfesional result = profesionalService.create(profesional);

        assertNotNull(result);
        assertEquals("Dr. Carlos Lopez", result.getCompleteName());
        assertEquals("Clinica", result.getSpeciality());
        verify(profesionalRepository, times(1)).save(profesional);
    }

    @Test
    void findEntityById_ShouldReturnProfesional_WhenProfesionalExists() {
        when(profesionalRepository.findById(1L)).thenReturn(profesional);

        Profesional result = profesionalService.findEntityById(1L);

        assertNotNull(result);
        assertEquals("Dr. Carlos Lopez", result.getCompleteName());
        assertEquals("Clinica", result.getSpecialty());
    }

    @Test
    void findEntityById_ShouldThrowException_WhenProfesionalNotFound() {
        when(profesionalRepository.findById(99L)).thenReturn(null);

        assertThrows(RecursoNoEncontradoException.class, () -> profesionalService.findEntityById(99L));
    }

    @Test
    void findBySpecialty_ShouldReturnListOfDTOProfesional_WhenSpecialtyExists() {
        when(profesionalRepository.findBySpeciality("Clinica")).thenReturn(List.of(profesional));

        List<DTOProfesional> result = profesionalService.findBySpecialty("Clinica");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Dr. Carlos Lopez", result.get(0).getCompleteName());
    }

    @Test
    void findBySpecialty_ShouldReturnEmptyList_WhenSpecialtyNotFound() {
        when(profesionalRepository.findBySpeciality("Odontologia")).thenReturn(List.of());

        List<DTOProfesional> result = profesionalService.findBySpecialty("Odontologia");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}