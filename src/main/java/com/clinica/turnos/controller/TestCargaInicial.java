package com.clinica.turnos.controller;

import com.clinica.turnos.model.Paciente;
import com.clinica.turnos.model.Profesional;
import com.clinica.turnos.model.Turno;
import com.clinica.turnos.service.PacienteService;
import com.clinica.turnos.service.ProfesionalService;
import com.clinica.turnos.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TestCargaInicial implements CommandLineRunner {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private ProfesionalService profesionalService;

    @Autowired
    private TurnoService turnoService;

    @Override
    public void run(String... args) throws Exception {

        // Crear 2 pacientes
        Paciente paciente1 = new Paciente();
        paciente1.setName("Juan");
        paciente1.setLastName("Perez");
        paciente1.setDni("12345678");
        paciente1.setEmail("juan.perez@gmail.com");
        pacienteService.create(paciente1);

        Paciente paciente2 = new Paciente();
        paciente2.setName("Maria");
        paciente2.setLastName("Gomez");
        paciente2.setDni("87654321");
        paciente2.setEmail("maria.gomez@gmail.com");
        pacienteService.create(paciente2);

        // Crear 2 profesionales
        Profesional profesional1 = new Profesional();
        profesional1.setCompleteName("Dr. Carlos Lopez");
        profesional1.setSpecialty("Clinica");
        profesionalService.create(profesional1);

        Profesional profesional2 = new Profesional();
        profesional2.setCompleteName("Dra. Ana Martinez");
        profesional2.setSpecialty("Odontologia");
        profesionalService.create(profesional2);

        // Registrar 3 turnos
        Paciente p1 = pacienteService.findEntityById(1L);
        Paciente p2 = pacienteService.findEntityById(2L);
        Profesional prof1 = profesionalService.findEntityById(1L);
        Profesional prof2 = profesionalService.findEntityById(2L);

        Turno turno1 = new Turno();
        turno1.setPaciente(p1);
        turno1.setProfesional(prof1);
        turno1.setDate(LocalDate.of(2026, 6, 10));
        turnoService.register(turno1);

        Turno turno2 = new Turno();
        turno2.setPaciente(p2);
        turno2.setProfesional(prof2);
        turno2.setDate(LocalDate.of(2026, 6, 11));
        turnoService.register(turno2);

        Turno turno3 = new Turno();
        turno3.setPaciente(p1);
        turno3.setProfesional(prof2);
        turno3.setDate(LocalDate.of(2026, 6, 12));
        turnoService.register(turno3);
    }
}