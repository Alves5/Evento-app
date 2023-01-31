package com.evento.app.repository;

import com.evento.app.models.Evento;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface EventoRepository extends CrudRepository<Evento, String> {
    Evento findById(UUID id);
}
