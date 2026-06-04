package com.clinica.turnos.repository;

import com.clinica.turnos.model.Profesional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ProfesionalRepository implements IProfesionalRepository{

    private Map<Long, Profesional> storage = new HashMap<>();
    private Long idCounter = 1L;

    public Profesional save(Profesional profesional) {
        profesional.setId(idCounter++);
        storage.put(profesional.getId(), profesional);
        return profesional;
    }

    public Profesional findById(Long id) {
        return storage.get(id);
    }

    public List<Profesional> findAll() {
        return new ArrayList<>(storage.values());
    }

    public List<Profesional> findBySpeciality(String speciality) {
        return storage.values().stream()
                .filter(p -> p.getSpecialty().equalsIgnoreCase(speciality))
                .collect(Collectors.toList());
    }
}