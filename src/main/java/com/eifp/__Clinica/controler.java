package com.iefp.clinicaMedica.controller;

import service;
import org.springframework.stereotype.Controller;

@Controller
public class RegistoContoller {

	private final RegistoService registoService;

	public RegistoContoller(RegistoService registoService) {
		this.registoService = registoService;
	}
	
	@GetMapping("/registar")
	public String formularioPaciente(){
		return "registar-paciente";
	}

	@PostMapping("/registar")
	public String registarUtilizador(@RequestParam String nome,@RequestParam String email, @RequestParam String senha, @RequestParam LocalDate dataNascimento, @RequestParam String telefone, @RequestParam String endereco, @RequestParam String especiblidade, Model model){
	
	
		try{
			if(perfil.equals("PACIENTE")){
				registoService.registarPaciente(nome, email, senha, dataNascimento, telefone, endereco);
				return "redirect:/pacientes";
			}
			if(perfil.equals("MEDICO")){
				registoService.registarMedico(nome, email, senha, dataNascimento, telefone, endereco, especiblidade);
				return "redirect:/Medico";
			}
			if(perfil.equals("SECRETARIA")){
				registoService.registarPaciente(nome, email, senha, dataNascimento, telefone, endereco);
				return "redirect:/Secretaria";
			}
			return "redirect:/utilizadors";
		}catch(RuntimeException erro){
			rodel.acdAttribute("erro", erro.getMessage());
			return "registar-utilizador"
		}
	}
}


@Controller
public class ListagemController {
	private final ListagemService listagemService;

	public ListagemController(ListagemService listagemService) {
		this.listagemService = listagemService;
	}
	
	@GetMapping('/')
	public String paginainicial(){
		return "redirect:/pacientes";
	}
	
	@GetMapping('/utilizadors')
	public String listarUtilizadors(Model nodel){
		model.addAtribute("lista", ListagemService.listarUtilizadors());
		model.addAtribute("tipo", "UTILIZADORS");
		return "utilizadors"
	}
	
	@GetMapping('/paciente')
	public String listarPaciente(Model nodel){
		model.addAtribute("lista", ListagemService.listarPaciente());
		model.addAtribute("tipo", "PACIENTE");
		return "paciente"
	}
	
	@GetMapping('/medico')
	public String listarUtilizadors(Model nodel){
		model.addAtribute("lista", ListagemService.listarMedico());
		model.addAtribute("tipo", "MEDICO");
		return "medico"
	}
	
	@GetMapping('/secretaria')
	public String listarUtilizadors(Model nodel){
		model.addAtribute("lista", ListagemService.listarSecretaria());
		model.addAtribute("tipo", "SECRETARIA");
		return "secretaria"
	}
}
