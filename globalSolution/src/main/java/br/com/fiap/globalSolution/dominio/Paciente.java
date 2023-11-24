package br.com.fiap.globalSolution.dominio;

public class Paciente {

	private String nome;
	private String cpf;
	private String genero;
	private String email;
	private String senha;

	public Paciente() {

	}

	public Paciente(String nome, String cpf, String genero, String email, String senha) {
		this.nome = nome;
		this.cpf = cpf;
		this.genero = genero;
		this.email = email;
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void atualizar(String cpf2) {
		this.nome = nome;
		this.genero = genero;
		this.email = email;
		this.senha = senha;
		
	}

}
