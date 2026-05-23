package com.clinica.turnos.controller;

import com.clinica.turnos.dto.TurnoResponseDTO;
import com.clinica.turnos.model.Turno;
import com.clinica.turnos.service.TurnoService;
import jakarta.validation.Valid;
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
    public ResponseEntity<TurnoResponseDTO> register(@Valid @RequestBody Turno turno) {
        return ResponseEntity.status(HttpStatus.CREATED).body(turnoService.register(turno));
    }

    @GetMapping
    public ResponseEntity<List<TurnoResponseDTO>> findAll() {
        return ResponseEntity.ok(turnoService.findAll());
    }

    @GetMapping("/fecha/{date}")
    public ResponseEntity<List<TurnoResponseDTO>> findByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(turnoService.findByDate(date));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        turnoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}