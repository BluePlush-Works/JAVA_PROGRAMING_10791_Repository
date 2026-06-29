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

@Service
public class DisponiblidadeService{
	private final DisponiblidadeRepository disponiblidaderepository;
	private final MedicoRepository medicoRepository;
	
	public DisponiblidadeService(DisponiblidadeRepository disponiblidaderepository, MedicoRepository medicoRepository){
		this.disponiblidaderepository = disponiblidaderepository;
		this.medicoRepository = medicoRepository;
	}
	
	public List<Disponiblidade> listarTodos(){
		return disponiblidaderepository.findAll();
	}
	
	public List<Disponiblidade> listarporespeciblidade (String especiblidade){
		return disponiblidaderepository.findbyMedico_IDOrderByDateAscHoraInicioAsc(especiblidade);
	}
	
	public void criarDisponiblidade (Long medico_ID, LocalDate date, LocalTime horainicio, LocalTime horafin;){
		Medico medico = medicoRepository.findByID(medico_ID).orElseThrow(()-> new RunTimeExeption("No doctor was found."));
		
		if(!horainicio.isBefore(horafin)){
			throw new RunTimeExeption("Start time should be before end time.");
		}
		
		LocalTime horaatual = horainicio;
		
		while(horaatual.plusHours(1).isBefore(horafin) || horaatual.plusHours(1).equals(horafin)){
			LocalTime horaFimConsulta = horaAtual.plusHours(1);
			
			Boolean jaExiste = disponibilidadeRepository.existsByMedico_IdAndDataAndHoraInicioAndHoraFim(medicoId, data, horaAtual, horaFimTrabalho);

			if(!jaExiste){
				Disponibilidade disponibilidade = new Disponibilidade(null, medico, data, horaAtual, horaFimConsulta, false);
				disponibilidadeRepository.save(disponibilidade);
			}
		}
	}
}
