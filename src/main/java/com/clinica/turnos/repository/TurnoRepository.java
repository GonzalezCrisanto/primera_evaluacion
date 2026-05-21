package com.clinica.turnos.repository;

import com.clinica.turnos.model.Turno;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TurnoRepository {

    private Map<Long, Turno> storage = new HashMap<>();
    private Long idCounter = 1L;

    public Turno save(Turno turno) {
        turno.setId(idCounter++);
        storage.put(turno.getId(), turno);
        return turno;
    }

    public List<Turno> findAll() {
        return new ArrayList<>(storage.values());
    }

    public List<Turno> findByDate(LocalDate date) {
        return storage.values().stream()
                .filter(t -> t.getDate().equals(date))
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        storage.remove(id);
    }

    public boolean DuplicateExist(Long pacienteId, Long profesionalId, LocalDate date) {
        return storage.values().stream()
                .anyMatch(t -> t.getPaciente().getId().equals(pacienteId)
                        && t.getProfesional().getId().equals(profesionalId)
                        && t.getDate().equals(date));
    }

    public Turno findById(Long id) {
        return storage.get(id);
    }
}