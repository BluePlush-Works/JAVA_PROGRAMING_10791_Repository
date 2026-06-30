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
	private final DisponiblidadeRepository disponiblidadeRepository;
	private final ConsultaRepository consultaRepository;
	private final ReceitaRepository receitaRepository;
	private final ExameRepository exameRepository;

	public RegistoService(UtilizadorRepository utilizadorRepository, PacienteRepository pacienteRepository, SecretariaRepository secretariaRepository, MedicoRepository medicoRepository, DisponiblidadeRepository disponiblidadeRepository, ConsultaRepository consultaRepository,  ReceitaRepository receitaRepository, ExameRepository exameRepository) {
		this.utilizadorRepository = utilizadorRepository;
		this.pacienteRepository = pacienteRepository;
		this.secretariaRepository = secretariaRepository;
		this.medicoRepository = medicoRepository;
		this.disponiblidadeRepository = disponiblidadeRepository;
		this.consultaRepository = consultaRepository;
		this.receitaRepository = receitaRepository;
		this.exameRepository = exameRepository;
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
	
	public void criarReceita(long consultaId, String medicamento, String dosagem, String intrucoes){
		Consulta consulta = consultaRepository.findById(consultaId).orElseThrow(()->new RuntimeException("Apointment does not exist"));
		
		Receita receita = new Receita(null, consulta, medicamento, dosagem, intrucoes);
		
		receitaRepository.save(receita);
	}
	
	public void criarExame(long exameId, String tipoExame, String discricao, String resultado){
		Consulta consulta = consultaRepository.findById(consultaId).orElseThrow(()->new RuntimeException("Apointment does not exist"));
		
		Exame exame = new Exame(null, tipoExame, discricao, resultado, consulta);
		
		exameRepository.save(exame);
	}
}

@Service
public class ListagemService {

	private final UtilizadorRepository utilizadorRepository;
	private final PacienteRepository pacienteRepository;
	private final SecretariaRepository secretariaRepository;
	private final MedicoRepository medicoRepository;
	private final DisponiblidadeRepository disponiblidadeRepository;
	private final ConsultaRepository consultaRepository;
	private final ReceitaRepository receitaRepository;
	private final ExameRepository exameRepository;

	public ListagemService(UtilizadorRepository utilizadorRepository, PacienteRepository pacienteRepository, SecretariaRepository secretariaRepository, MedicoRepository medicoRepository, DisponiblidadeRepository disponiblidadeRepository, ConsultaRepository consultaRepository,  ReceitaRepository receitaRepository, ExameRepository exameRepository) {
		this.utilizadorRepository = utilizadorRepository;
		this.pacienteRepository = pacienteRepository;
		this.secretariaRepository = secretariaRepository;
		this.medicoRepository = medicoRepository;
		this.disponiblidadeRepository = disponiblidadeRepository;
		this.consultaRepository = consultaRepository;
		this.receitaRepository = receitaRepository;
		this.exameRepository = exameRepository;
	}

	public List<Utilizador> listarTodos(){
		return utilizadorRepository.findAll();
	}
	
	public List<Pacient> listarPaciente(){
		return pacienteRepository.findAll();
	}
	
	public List<Medico> listarMedico(){
		return medicoRepository.findAll();
	}
	
	public List<Secretaria> listarSecretaria(){
		return secretariaRepository.findAll();
	}
	
	public List<Disponibilidade> listarDisponiblidade(){
		return disponibilidadeRepository.findAll();
	}
	
	public List<Consulta> listarConsulta(){
		return consultaRepository.findAll();
	}
	
	public List<Receita> listarReceita(){
		return receitaRepository.findAll();
	}
	
	public List<Exame> listarExame(){
		return exameRepository.findAll();
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
		Medico medico = medicoRepository.findByID(medico_ID).orElseThrow(()-> new RuntimeException("No doctor was found."));
		
		if(!horainicio.isBefore(horafin)){
			throw new RuntimeException("Start time should be before end time.");
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

@Service
public class ConsultaService{
	private final ConsultaRepository consultarepository;
	private final MedicoRepository medicoRepository;
	private final PacienteRepository pacienterepository;
	private final SecretariaRepository secretariarepository;
	private final DisponiblidadeRepository disponiblidaderepository;
	
	public ConsultaService(ConsultaRepository consultarepository, MedicoRepository medicoRepository, PacienteRepository pacienterepository, SecretariaRepository secretariarepository, DisponiblidadeRepository disponiblidaderepository){
		this.consultarepository = consultarepository;
		this.medicoRepository = medicoRepository;
		this.pacienterepository = pacienterepository;
		this.secretariarepository = secretariarepository;
		this.disponiblidaderepository = disponiblidaderepository;
	}
	
	public List<Consulta> listarTodos(){
		return consultarepository.findAll();
	}
	
	public List<String> listarespecibilidade(){
		return medicoRepository.listarespecibilidade();
	}
	
	public List<Disponibilidade> listardisponibilidade(){
		return disponiblidaderepository.findbyMedico_IDOrderByDateAscHoraInicioAsc(especiblidade);
	}
	
	public void marcarconsulta(Long disponibilidade_id, Long paciente_id, Long secretaria_id){
		Disponibilidade disponibilidade = disponiblidaderepository.findByID(disponibilidadeId).orElseThrow(()-> new RuntimeException("No availability was found."));
		Paciente paciente = pacienterepository.findByID(pacienteId).orElseThrow(()-> new RuntimeException("Pacient not found."));
		Secretaria secretaria = secretariarepository.findByID(secretariaId).orElseThrow(()-> new RuntimeException("Secretary not found."));
		
		Consulta consulta = new Consulta(null, disponibilidade, paciente, secretaria, disponibilidade.getData(), disponibilidade.horainicio, disponibilidade.horafin, "BOOKED");
		
		consultarepository.save(consulta);
		
		disponibilidade.setOcupada(true);
		disponiblidaderepository.save(disponibilidade);
	}
}
