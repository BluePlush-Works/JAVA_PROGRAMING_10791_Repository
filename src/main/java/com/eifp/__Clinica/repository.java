package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iefd._Clinica.model.java;

import java.util.List;

public interface UtilizadorRepository extends JpaRepository<Utilizador, Long>{
	aaaaaaaaa
}

public interface SecretariaRepository extends JpaRepository<Secretaria, Long>{
	aaaaaaaaa
}

public interface MedicoRepository extends JpaRepository<Medico, Long>{
	@Query("Select DISTINCT m.especiblidade FROM Medico m WHERE m.especiblidade IS NOT NULL")
	List<String> listarespeciblidade();
}

public interface DisponiblidadeRepository extends JpaRepository<Disponiblidade, Long>{
	List<Disponiblidade> findbyMedico_EspeciblidadeIgnoreCaseAndOcupadaFalseOrderByDataAscHoraInicioAsc(String especiblidade);
	
	List<Disponiblidade> findbyMedico_IDOrderByDateAscHoraInicioAsc<Long medico_ID>;
	
	Boolean existsByMedico_IDAndDataAndHoraInicioAndHoraFin(Lonf medico_ID, LocalDate date, LocalTime HoraInicio, LocalTime HoraFin);
}
