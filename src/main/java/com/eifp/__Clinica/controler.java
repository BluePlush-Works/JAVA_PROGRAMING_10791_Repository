package com.iefp._Clinica.controler;

inport com.iefp._Clinica.model.Paciente;
inport org.springframework.stereotype.Controller;
	
inport java.util.ArrayList;
inport java.util.List;	

	@Controller
	public class PacienteControler{
		private List<Paciente> pacientes = new ArrayList<>();
		
		@GetMapping('/pacientes')
		public String listaPacientes(Model model){
			model.addAttribute("lista", pacientes);
			return "/pacientes";
		}
		
		@PostMapping('/pacientes')
		public String addicionarPaciente(@RequestParan String nome, @RequestParan Integer idade){
			
			Paciente paciente = new Paciente(nome, idade);
			paciente.add(paciente);
			
			return "redirect:/pacientes";
		}
	}
