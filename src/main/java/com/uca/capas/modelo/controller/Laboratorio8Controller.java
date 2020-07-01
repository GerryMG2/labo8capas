package com.uca.capas.modelo.controller;

import java.text.ParseException;
import java.util.logging.Level;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sun.istack.logging.Logger;
import com.uca.capas.modelo.domain.Cliente;
import com.uca.capas.modelo.service.ClienteService;


@Controller
public class Laboratorio8Controller {
	
	private static final Logger logger = Logger.getLogger(Laboratorio8Controller.class);
	
	private ClienteService clienteService;

	//MENU PRINCIPAL LABORATORIO 8
	@RequestMapping("/indexLaboratorio8")
	public ModelAndView indexLaboratorio8() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("Laboratorio/indexLaboratorio8");
		return mav;
	}
	
	@RequestMapping("/insertcliente")
	public ModelAndView nuevoCliente() {
		ModelAndView mac = new ModelAndView();
		mac.addObject("cliente", new Cliente());
		mac.setViewName("Laboratorio/agregarCliente");
		return mac;
	}
	

	@RequestMapping(value = "/savecliente", method = RequestMethod.POST)
	public ModelAndView saveCliente(@Valid @ModelAttribute("cliente") Cliente c, BindingResult r) {
		ModelAndView mac = new ModelAndView();
		mac.addObject("cliente", new Cliente());
		mac.setViewName("Laboratorio/agregarCliente");
		if(r.hasErrors()) {
			mac.addObject("resultado", 0);
		}else {
			Integer key = null;
			if(c.getCcliente() == null) {
				mac.addObject("resultado", 1);
				key = clienteService.insertClienteAutoId(c);
			}else {
				mac.addObject("resultado", 1);
				clienteService.updateCliente(c);
			}
		}
		
		return mac;
	}
	
	@RequestMapping("/procAlmacenadoJdbc")
	public ModelAndView procAlmacenadoJdbc() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("usuario",new Cliente());
		mav.setViewName("Laboratorio/procedimiento");
		return mav;
	}
	
	@RequestMapping("/ejecutarProcedimientoJdbc")
	public ModelAndView ejecutarProcedimiento(@RequestParam Integer cliente,@RequestParam Boolean estado) {
		
		ModelAndView mav = new ModelAndView();
		Integer resultado;
		resultado = clienteService.ejecturaProcJdbc(cliente, estado);
		mav.addObject("resultado",resultado);
		mav.setViewName("Laboratorio/resultado");
		return mav;
	}
	
	@RequestMapping("/batchVehiculo")
	public ModelAndView batchInsertion() throws ParseException{
		
		ModelAndView mav = new ModelAndView();
		long startTime = System.nanoTime();
		Integer resultado;
		clienteService.cargaMasiva();
		long endTime = System.nanoTime();
		long duration= endTime - startTime;
		duration = duration / 1000000;
		logger.log(Level.INFO, "duration: ", duration);
		mav.setViewName("Laboratorio/resultado");
		return mav;
	}
	
	
	
	
	
}
