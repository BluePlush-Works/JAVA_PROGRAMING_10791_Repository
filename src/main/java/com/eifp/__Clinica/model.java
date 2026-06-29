package com.iefp._Clinica.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Date
@NoArgsConstructor
@AllArgsConstructor
public class Utilizador{
	
	@Id
	@GenerateValue(stratagy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	@Collum(unique=true)
	private String email;
	
	private String password;
	private String perfil;
	private LocalDate dataNashimento;
	private String telephone;
	private String endreco;
	
	public Integer real_age(){
		if(dataNashimento==null){
			return 0;
		}
		
		return Period.between(dataNashimento, LocalDate.now()).getYear();
	}
	
	public Paciente (String nome, Integer idade){
		this.nome = nome;
		this.idade = idade;
	}
	
	Public String getnome(){ return nome;}
	Public Integer getidade(){return idade;}
}

@Entity
@Date
@NoArgsConstructor
@AllArgsConstructor
public class Paciente{
	@Id
	@GenerateValue(stratagy = GenerationType.IDENTITY)
	private Long paciente_ID;
	
	@OnetoOne
	@JoinColumn(name = "utilizador_id", unique = true)
	private Utilizador utilizado;
}

@Entity
@Date
@NoArgsConstructor
@AllArgsConstructor
public class Medico{
	@Id
	@GenerateValue(stratagy = GenerationType.IDENTITY)
	private Long medico_ID;
	
	private String especiblidade;
	
	@OnetoOne
	@JoinColumn(name = "utilizador_id", unique = true)
	private Utilizador utilizado;
}

@Entity
@Date
@NoArgsConstructor
@AllArgsConstructor
public class Secretaria{
	@Id
	@GenerateValue(stratagy = GenerationType.IDENTITY)
	private Long secretary_ID;
	
	@OnetoOne
	@JoinColumn(name = "utilizador_id", unique = true)
	private Utilizador utilizado;
}

@Entity
@Date
@NoArgsConstructor
@AllArgsConstructor
public class Disponiblidade{
	@Id
	@GenerateValue(stratagy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "medico_ID")
	private Medico medico;
	
	private LocalDate date;
	private LocalTime horainicio;
	private LocalTime horafin;
	
	private boolean ocupado;
}

@Entity
@Date
@NoArgsConstructor
@AllArgsConstructor
public class Consulta{
	@Id
	@GenerateValue(stratagy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "disponiblidade_ID")
	private Disponiblidade disponiblidade;
	
	@ManyToOne
	@JoinColumn(name = "paciente_ID")
	private Paciente paciente;
	
	@ManyToOne
	@JoinColumn(name = "secretaria_ID")
	private Secretaria secretaria;
	
	private LocalDate date;
	private LocalTime horainicio;
	private LocalTime horafin;
	private String estado;
}
