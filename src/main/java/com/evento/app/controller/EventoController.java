package com.evento.app.controller;

import com.evento.app.models.Convidado;
import com.evento.app.models.Evento;
import com.evento.app.repository.ConvidadoRepository;
import com.evento.app.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Controller
public class EventoController {
    @Autowired
    private EventoRepository er;
    @Autowired
    private ConvidadoRepository cr;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET)
    public String form(){
        return "evento/formCadastro";
    }

    @RequestMapping(value = "/cadastrarEvento", method = RequestMethod.POST)
    public String form(@Valid Evento evento, BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "Verifique os campos.");
            return "redirect:/cadastrarEvento";
        }
        evento.setDataCadastro(LocalDateTime.now(ZoneId.of("UTC")));
        er.save(evento);
        attributes.addFlashAttribute("mensagem", "Evento adicionado com sucesso!");
        return "redirect:/cadastrarEvento";
    }

    @RequestMapping("/eventos")
    public ModelAndView listaEvento(){
        ModelAndView modelAndView = new ModelAndView("evento/listaEventos");
        Iterable<Evento> listEventos = er.findAll();
        modelAndView.addObject("eventos", listEventos);
        return modelAndView;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView detalhesEvento(@PathVariable("id") UUID id){
        Evento evento = er.findById(id);
        ModelAndView modelAndView = new ModelAndView("evento/detalhesEvento");
        modelAndView.addObject("evento", evento);

        Iterable<Convidado> convidados = cr.findByEvento(evento);
        modelAndView.addObject("convidados", convidados);
        return modelAndView;
    }

    @RequestMapping("/deletar")
    public String deletarEvento(@RequestParam UUID id){
        Evento evento = er.findById(id);
        er.delete(evento);
        return "redirect:/eventos";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String detalhesEventoPost(@PathVariable("id") UUID id, @Valid Convidado convidado, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "Verifique os campos");
            return "redirect:/{id}";
        }
        Evento evento = er.findById(id);
        convidado.setEvento(evento);
        cr.save(convidado);
        attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso!");
        return "redirect:/{id}";
    }

    @RequestMapping("/")
    public String deletarConvidado(@RequestParam UUID id){
        Convidado convidado = cr.findById(id);
        Evento eventoiInfo = convidado.getEvento();
        String idEvento = String.valueOf(eventoiInfo.getId());
        cr.delete(convidado);
        return "redirect:/"+idEvento;
    }

}
