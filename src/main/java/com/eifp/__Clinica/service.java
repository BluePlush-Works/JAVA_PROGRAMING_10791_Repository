package service;

import model;
import repository;

import java.time.LocalDate;
import java.util.List;

public class RegistoService {

	private final UtilizadorRepository utilizadorRepository;
	private final PacienteRepository pacienteRepository;
	private final SecretariaRepository secretariaRepository;
	private final MedicoRepository medicoRepository;

	public RegistoService(UtilizadorRepository utilizadorRepository,
		PacienteRepository pacienteRepository,
		SecretariaRepository secretariaRepository,
		MedicoRepository medicoRepository) {
		this.utilizadorRepository = utilizadorRepository;
		this.pacienteRepository = pacienteRepository;
		this.secretariaRepository = secretariaRepository;
		this.medicoRepository = medicoRepository;
	}

	public void registarPaciente(String nome, String email, String senha, LocalDate dataNascimento, String telefone, String endereco){

		Utilizador utilizador = new Utilizador(	null, nome, email, senha, "PACIENTE", dataNascimento, telefone, endereco);
		if(utilizadorRepository.existsByEmail(email)){
			throw new RuntimeException("That email adress is already connected to an existing user.");
		}

		utilizadorRepository.save(utilizador);

		Paciente paciente = new Paciente(null, utilizador);
	}
	
	public void registarMedico(String nome, String email, String senha, LocalDate dataNascimento, String telefone, String endereco, String especiblidade){

		Utilizador utilizador = new Utilizador(	null, nome, email, senha, "MEDICO", dataNascimento, telefone, endereco, especiblidade);

		utilizadorRepository.save(utilizador);

		Medico medico = new Medico(null, utilizador, especiblidade);
	}
	
	public void registarSecretaria(String nome, String email, String senha, LocalDate dataNascimento, String telefone, String endereco){

		Utilizador utilizador = new Utilizador(	null, nome, email, senha, "PACIENTE", dataNascimento, telefone, endereco);

		utilizadorRepository.save(utilizador);

		Secretaria secretaria = new Secretaria(null, utilizador);
	}
	
}

@Service
public class ListagemService {

	private final UtilizadorRepository utilizadorRepository;
	private final SecretariaRepository secretariaRepository;
	private final MedicoRepository medicoRepository;
	private final PacienteRepository pacienteRepository;

	public ListagemService(UtilizadorRepository utilizadorRepository,SecretariaRepository secretariaRepository,MedicoRepository medicoRepository,PacienteRepository pacienteRepository) {
		this.utilizadorRepository = utilizadorRepository;
		this.secretariaRepository = secretariaRepository;
		this.medicoRepository = medicoRepository;
		this.pacienteRepository = pacienteRepository;
	}

	public List<Utilizador> listarTodos(){
		return utilizadorRepository.findAll();
	}
}
