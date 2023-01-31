package com.evento.app.repository;

import com.evento.app.models.Convidado;
import com.evento.app.models.Evento;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ConvidadoRepository extends CrudRepository<Convidado, String> {
    Convidado findById(UUID id);
    Iterable<Convidado> findByEvento(Evento evento);
}
