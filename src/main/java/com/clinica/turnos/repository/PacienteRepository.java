package com.clinica.turnos.repository;

import com.clinica.turnos.model.Paciente;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PacienteRepository implements IPacienteRepository {

    private Map<Long, Paciente> storage = new HashMap<>();
    private Long idCounter = 1L;

    public Paciente save(Paciente paciente) {
        paciente.setId(idCounter++);
        storage.put(paciente.getId(), paciente);
        return paciente;
    }

    public Paciente findById(Long id) {
        return storage.get(id);
    }

    public List<Paciente> findAll() {
        return new ArrayList<>(storage.values());
    }

    public void deleteById(Long id) {
        storage.remove(id);
    }
}