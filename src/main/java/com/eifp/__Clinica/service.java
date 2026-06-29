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

@Controller
public class DisponiblidadeController{
	private final DisponiblidadeService disponiblidadeservice;
	private final ListagemService listagemService;
	
	public DisponiblidadeService(DisponiblidadeService disponiblidadeservice, ListagemService listagemService){
		this.disponiblidadeservice = disponiblidadeservice;
		this.listagemService = listagemService;
	}
	
	@GetMapping("/disponiblidade")
	public String listarDisponiblidade (Model model){
		model.addAtribute("disponiblidade", disponiblidadeservice.listarTodas());
		model.addAtribute("medico", listagemService.listarMedico());
	}
	
	@PostMapping("/disponiblidade")
	public String criarDisponiblidade(@RequestParam Long medicoID, @RequestParam LocalDate date, @RequestParam LocalTime horainicio, @RequestParam LocalTime horaend, Model model){
		try{
			disponiblidadeservice.criarDisponiblidade(medicoID, date, horainicio, horaend, model)
		}catch (RuntimeException error){
			model.addAtribute("error", error.getMessage());
			model.addAtribute("disponiblidade", disponiblidadeservice.listarTodas());
			model.addAtribute("medico", listagemService.listarMedico());
			return "disponiblidade";
		}
	}
}

Controller
public class ConsultaController{
	private final consultaService;
	private final listagemService;
	
	public DisponiblidadeService(ConsultaService consultaService, ListagemService listagemService){
		this.consultaService = consultaService;
		this.listagemService = listagemService;
	}
	
	@GetMapping("/consultas")
	public String listarConsultas (@RequestParam(required = false) String especiblidade, Model model){
		model.addAtribute("consulta", consultaService.listarTodas());
		model.addAtribute("especiblidade", listagemService.listarEspecilidade());
		model.addAtribute("paciente", listagemService.listarPaciente());
		model.addAtribute("secretaria", listagemService.listarSecretaria());
		model.addAtribute("especiblidadeSelecionada", especiblidade);
		
		if(especiblidade != null && !especiblidade.isEmpty()){
			model.addAtribute("disponiblidade", consultaService.listarDisponiblidade(especiblidade));
		}
		
		return "consulta";
	}
	
	@PostMapping("/consultas")
	public String marcarConsulta(@RequestParam Long disponiblidadeId, @RequestParam Long pacienteId, @RequestParam(required = false) Long secretariaId, Model model){
		try{
			consultaService.marcarConsulta(disponiblidadeId, pacienteId, secretariaId);
			return "redirect:/consultas";
		}catch (RuntimeException){
			model.addAtribute("error", error.getMessage());
			model.addAtribute("consulta", consultaService.listarEspecilidade());
			model.addAtribute("especiblidades", consultaService.listarEspecilidade());
			model.addAtribute("pacientes", consultaService.listarEspecilidade());
			model.addAtribute("secretarias", consultaService.listarEspecilidade());
			return "consultas";
		}
	}
}
