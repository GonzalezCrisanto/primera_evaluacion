package com.clinica.turnos.controller;

import com.clinica.turnos.model.Turno;
import com.clinica.turnos.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;

    @PostMapping
    public ResponseEntity<Turno> register(
            @RequestParam Long pacienteId,
            @RequestParam Long profesionalId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(turnoService.register(pacienteId, profesionalId, date));
    }

    @GetMapping
    public ResponseEntity<List<Turno>> findAll() {
        return ResponseEntity.ok(turnoService.findAll());
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<Turno>> findByFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(turnoService.findByFecha(date));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        turnoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}