package com.iefp._Clinica.model;

public class Paciente{
	private String nome;
	private Integer idade;
	
	public Paciente (String nome, Integer idade){
		this.nome = nome;
		this.idade = idade;
	}
	
	Public String getnome(){ return nome;}
	Public Integer getidade(){return idade;}
}
