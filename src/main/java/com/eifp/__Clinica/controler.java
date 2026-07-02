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
			rodel.addAttribute("erro", erro.getMessage());
			return "registar-utilizador"
		}
	}
	
	@PostMapping("/receitas")
	public String criarReceita(@RequestParam Long consultaId, @RequestParam String medicamento, @RequestParam String dosagem, @RequestParam String intrucoes, Model model){
		try{
			registoService.criarReceita(consultaId, medicamento, dosagem, intrucoes);
			return "redirect:/receitas";
		}catch (RuntimeException error){
			model.addAttribute("error", error.getMessage());
			model.addAttribute("receitas", listagemService.listarReceita());
			model.addAttribute("consulta", listagemService.listarTodasConsultas());
			return "receitas";
		}
	}
	
	@PostMapping("/exames")
	public String criarExame(@RequestParam Long consultaId, @RequestParam String tipoExame, @RequestParam String discricao, @RequestParam(required = false) String resultado, Model model){
		try{
			registoService.criarExame(consultaId, medicamento, dosagem, intrucoes);
			return "redirect:/exames";
		}catch (RuntimeException error){
			model.addAttribute("error", error.getMessage());
			model.addAttribute("exames", listagemService.listarReceita());
			model.addAttribute("consulta", listagemService.listarTodasConsultas());
			return "exames";
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
		return "redirect:/login";
	}
	
	@GetMapping('/utilizadors')
	public String listarUtilizadors(Model model, HttpSession session){
		model.addAttribute("lista", ListagemService.listarUtilizadors());
		model.addAttribute("tipo", "UTILIZADORS");
		return "utilizadors"
	}
	
	@GetMapping('/paciente')
	public String listarPaciente(Model model, HttpSession session){
		Utilizador utilizadorLogado = (Utilizador) session.getAttribute("utilizadorLogado");
		
		if(utilizadorLogado == null){
			return "redirect:/login";
		}
		
		if(utilizadorLogado.getPerfil().equals('PACIENTE')){
			return "redirect:/home";
		}
		
		model.addAttribute("lista", ListagemService.listarPaciente());
		model.addAttribute("tipo", "PACIENTE");
		return "paciente"
	}
	
	@GetMapping('/medico')
	public String listarUtilizadors(Model model, HttpSession session){
		Utilizador utilizadorLogado = (Utilizador) session.getAttribute("utilizadorLogado");
		
		if(utilizadorLogado == null){
			return "redirect:/login";
		}
		
		if(utilizadorLogado.getPerfil().equals('MEDICO')){
			return "redirect:/home";
		}
		
		model.addAttribute("lista", ListagemService.listarMedico());
		model.addAttribute("tipo", "MEDICO");
		return "medico"
	}
	
	@GetMapping('/secretaria')
	public String listarUtilizadors(Model nodel){
		Utilizador utilizadorLogado = (Utilizador) session.getAttribute("utilizadorLogado");
		
		if(utilizadorLogado == null){
			return "redirect:/login";
		}
		
		if(utilizadorLogado.getPerfil().equals('SECRETARIA')){
			return "redirect:/home";
		}
		
		model.addAttribute("lista", ListagemService.listarSecretaria());
		model.addAttribute("tipo", "SECRETARIA");
		return "secretaria"
	}
	
	@GetMapping("/consultas")
	public String listarConsultas (@RequestParam(required = false) String especiblidade, Model model){
		model.addAttribute("consulta", consultaService.listarTodas());
		model.addAttribute("especiblidade", listagemService.listarEspecilidade());
		model.addAttribute("paciente", listagemService.listarPaciente());
		model.addAttribute("secretaria", listagemService.listarSecretaria());
		model.addAttribute("especiblidadeSelecionada", especiblidade);
		
		if(especiblidade != null && !especiblidade.isEmpty()){
			model.addAttribute("disponiblidade", consultaService.listarDisponiblidade(especiblidade));
		}
		
		return "consulta";
	}
	
	@PostMapping("/consultas")
	public String marcarConsulta(@RequestParam Long disponiblidadeId, @RequestParam Long pacienteId, @RequestParam(required = false) Long secretariaId, Model model){
		try{
			consultaService.marcarConsulta(disponiblidadeId, pacienteId, secretariaId);
			return "redirect:/consultas";
		}catch (RuntimeException){
			model.addAttribute("error", error.getMessage());
			model.addAttribute("consulta", consultaService.listarEspecilidade());
			model.addAttribute("especiblidades", consultaService.listarEspecilidade());
			model.addAttribute("pacientes", consultaService.listarEspecilidade());
			model.addAttribute("secretarias", consultaService.listarEspecilidade());
			return "consultas";
		}
	}
	
	@PostMapping("/receitas")
	public String listarReceitas(Model model){
		model.addAttribute("receitas", listagemService.listarReceita());
		model.addAttribute("consulta", listagemService.listarTodasConsultas());
		return "receitas";
	}
	
	@PostMapping("/exames")
	public String listarReceitas(Model model){
		model.addAttribute("exames", listagemService.listarReceita());
		model.addAttribute("consulta", listagemService.listarTodasConsultas());
		return "exames";
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
		model.addAttribute("disponiblidade", disponiblidadeservice.listarTodas());
		model.addAttribute("medico", listagemService.listarMedico());
	}
}

@Controller
public class ConsultaController{
	private final consultaService;
	private final listagemService;
	
	public DisponiblidadeService(ConsultaService consultaService, ListagemService listagemService){
		this.consultaService = consultaService;
		this.listagemService = listagemService;
	}
}

@Controller
public class LoginControler{
	private final ListagemService listagemService;
	
	public LoginControler(ListagemService listagemService){
		this.listagemService = listagemService;
	}
	
	@GetMapping("/login")
	public String mostrarLogin(){
		return "login";
	}
	
	@PostMapping("/login")
	public String fazerLogin(@RequestParam String email, @RequestParam String senha, HttpSession session, Model model){
		Optional<Utilizador> utilizadorEncontrado = listagemService.procurarPorEmailSenha(email, senha);
		
		if(utilizadorEncontrado.isPresent()){
			Utilizador utilizador = utilizadorEncontrado.get();
			
			session.setAtribute("utilizadorLogado", utilizador);
			
			if(utilizador.getPerfil().equals("PACIENTE")){
				return "redirect:/consultas";
			}
			
			if(utilizador.getPerfil().equals("MEDICO")){
				return "redirect:/disponiblidade";
			}
			
			if(utilizador.getPerfil().equals("SECRETARIA")){
				return "redirect:/pacientes";
			}
			
			return "redirect:/home";
		}
		model.addAttribute("error", "Invalid Email Adress or Password");
		return "login";
	}
	
	@GetMapping("/home")
	public String home(HttpSession session, Model model){
		Utilizador utilizadorLogado = (Utilizador) session.getAttribute("utilizadorLogado");
		
		if(utilizadorLogado == null){
			return "redirect:/login";
		}
		model.addAttribute("utilizador", utilizadorLogado);
		return "home";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session){
		session.invalidate();
		return "redirect:/login";
	}
}

public class EditarControler{
	private final EditarService editarService;
	private final ListagemService listagemService;
	
	public EditarControler(EditarService editarService, ListagemService listagemService){
		this editarService = editarService;
		this listagemService = listagemService;
	}
	
	@GetMapping("/editar/paciente/{id}")
	public String mostrarEditarPaciente(@PathVariable Long id, Model model){
		Paciente paciente = listagemService.procurarPacientePorId(id);
		
		model.addAttribute("item", paciente);
		model.addAttribute("tipo", "PACIENTE");
		return "editar_utilizador";
	}
	
	@GetMapping("/editar/medico/{id}")
	public String mostrarEditarMedico(@PathVariable Long id, Model model){
		Medico medico = listagemService.procurarMedicoPorId(id);
		
		model.addAttribute("item", medico);
		model.addAttribute("tipo", "MEDICO");
		return "editar_utilizador";
	}
	
	@GetMapping("/editar/secretaria/{id}")
	public String mostrarEditarSecretaria(@PathVariable Long id, Model model){
		Secretaria secretaria = listagemService.procurarSecretariaPorId(id);
		
		model.addAttribute("item", secretaria);
		model.addAttribute("tipo", "SECRETARIA");
		return "editar_utilizador";
	}
	
	@PostMapping("/editar/paciente/{id}")
	public String editarPaciente(@PathVariable Long id, @PathVariable String nome, @PathVariable email, @PathVariable senha, @PathVariable dataNascimento, @PathVariable telefone, endereco){
		editarService.editarPaciente(id, email, senha, dataNascimento, telefone, endereco);
		
		return "redirect:/pacientes";
	}
	
	@PostMapping("/editar/medico/{id}")
	public String editarMedico(@PathVariable Long id, @PathVariable String nome, @PathVariable email, @PathVariable senha, @PathVariable dataNascimento, @PathVariable telefone, endereco, @PathVariable String especiblidade){
		editarService.editarMedico(id, email, senha, dataNascimento, telefone, endereco, especiblidade);
		
		return "redirect:/medicos";
	}
	
	@PostMapping("/editar/secretaria/{id}")
	public String editarSecretaria(@PathVariable Long id, @PathVariable String nome, @PathVariable email, @PathVariable senha, @PathVariable dataNascimento, @PathVariable telefone, endereco){
		editarService.editarSecretaria(id, email, senha, dataNascimento, telefone, endereco);
		
		return "redirect:/secretarias";
	}
}

@Controller
public class RemoverController{
	private final RemoveService removeService;
	
	public RemoverController(RemoveService removeService){
		this removeService = removeService;
	}
	
	@GetMapping("/editar/paciente/{id}")
	public String removerPaciente(@PathVariable Long id){
		removeService.removerPaciente(id);
		return "redirect:/pacientes";
	}
	
	@GetMapping("/editar/medico/{id}")
	public String removerMedico(@PathVariable Long id){
		removeService.removerMedico(id);
		return "redirect:/medicos";
	}
	
	@GetMapping("/editar/secretaria/{id}")
	public String removerSecretaria(@PathVariable Long id){
		removeService.removerSecretaria(id);
		return "redirect:/secretarias";
	}
}
