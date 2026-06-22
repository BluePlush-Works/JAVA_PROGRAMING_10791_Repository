package com.iefp.clinicaMedica.controller;

import service;
import org.springframework.stereotype.Controller;

@Controller
public class RegistoContoller {

	private final RegistoService registoService;

	public RegistoContoller(RegistoService registoService) {
		this.registoService = registoService;
	}
	
	@GetMapping("/registar/paciente")
	public String formularioPaciente(){
		return "registar-paciente";
	}

	@PostMapping("/registar/paciente")
	public String registarPaciente(@RequestParam String nome,@RequestParam String email, @RequestParam String senha, @RequestParam LocalDate dataNascimento, @RequestParam String telefone, @RequestParam String endereco){

		registoService.registarPaciente(nome, email, senha, dataNascimento, telefone, endereco);

		return "redirect:/pacientes";
	}

	@GetMapping("/registar/Medico")
	public String formularioPaciente(){
		return "registar-Medico";
	}

	@PostMapping("/registar/Medico")
	public String registarMedico(@RequestParam String nome,
		@RequestParam String email,	@RequestParam String senha,	@RequestParam LocalDate dataNascimento,	@RequestParam String telefone,	@RequestParam String endereco, @RequestParam String especiblidade){

		registoService.registarMedico(nome, email, senha, dataNascimento, telefone, endereco, especiblidade);

		return "redirect:/Medico";
	}

	@GetMapping("/registar/Secretaria")
	public String formularioPaciente(){
		return "registar-Secretaria";
	}

	@PostMapping("/registar/Secretaria")
	public String registarPaciente(@RequestParam String nome,
		@RequestParam String email,	@RequestParam String senha,	@RequestParam LocalDate dataNascimento,	@RequestParam String telefone,	@RequestParam String endereco){

		registoService.registarPaciente(nome, email, senha, dataNascimento, telefone, endereco);

		return "redirect:/Secretaria";
	}
}


@Controller
public class ListagemController {
	private final ListagemService listagemService;

	public ListagemController(ListagemService listagemService) {
		this.listagemService = listagemService;
	}
	
	@GetMapping('/utilizadors')
	public String listarUtilizadors(Model nodel){
		model.addAtribute("lista", ListagemService.listarUtilizadors());
		return "utilizadors"
	}
	
	@GetMapping('/paciente')
	public String listarPaciente(Model nodel){
		model.addAtribute("lista", ListagemService.listarPaciente());
		return "paciente"
	}
	
	@GetMapping('/medico')
	public String listarUtilizadors(Model nodel){
		model.addAtribute("lista", ListagemService.listarMedico());
		return "medico"
	}
	
	@GetMapping('/secretaria')
	public String listarUtilizadors(Model nodel){
		model.addAtribute("lista", ListagemService.listarSecretaria());
		return "secretaria"
	}
}
