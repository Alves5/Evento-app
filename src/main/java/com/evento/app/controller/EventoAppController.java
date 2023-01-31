package com.evento.app.controller;

import com.evento.app.models.Evento;
import com.evento.app.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Controller
public class EventoAppController {
    @Autowired
    private EventoRepository er;

    @RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET)
    public String form(){
        return "evento/formCadastro";
    }
    @RequestMapping(value = "/cadastrarEvento", method = RequestMethod.POST)
    public String form(Evento evento){
        evento.setDataCadastro(LocalDateTime.now(ZoneId.of("UTC")));
        er.save(evento);
        return "redirect:/cadastrarEvento";
    }
    @RequestMapping("/eventos")
    public ModelAndView listaEvento(){
        ModelAndView modelAndView = new ModelAndView("index");
        Iterable<Evento> listEventos = er.findAll();
        modelAndView.addObject("eventos", listEventos);
        return modelAndView;
    }
}
