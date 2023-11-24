package br.com.fiap.globalSolution.dominio;

import br.com.fiap.globalSolution.exceptions.PacienteNaoEncontradoException;
import br.com.fiap.globalSolution.exceptions.PacienteQueryException;

public interface ConjuntoPacientes {

	void adicionar(Paciente paciente) throws PacienteQueryException;

	Paciente buscarPacientePorCpf(String cpf);

	void atualizarPaciente(Paciente pacienteAtualizado, String cpf)
			throws PacienteQueryException, PacienteNaoEncontradoException;

	boolean checarCpf(String cpf);

	void excluir(String cpf) throws PacienteQueryException, PacienteNaoEncontradoException;

	void fechar();

}
