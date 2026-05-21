package com.clinica.turnos.controller;

import com.clinica.turnos.model.Profesional;
import com.clinica.turnos.service.ProfesionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profesionales")
public class ProfesionalController {

    @Autowired
    private ProfesionalService profesionalService;

    @PostMapping
    public ResponseEntity<Profesional> create(@RequestBody Profesional profesional) {
        return ResponseEntity.status(HttpStatus.CREATED).body(profesionalService.create(profesional));
    }

    @GetMapping
    public ResponseEntity<List<Profesional>> findByEspecialidad(@RequestParam String speciality) {
        return ResponseEntity.ok(profesionalService.findBySpeciality(speciality));
    }
}