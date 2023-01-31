package com.evento.app.repository;

import com.evento.app.models.Evento;
import org.springframework.data.repository.CrudRepository;

public interface EventoRepository extends CrudRepository<Evento, String> {

}
