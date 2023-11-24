package br.com.fiap.globalSolution.service;

import br.com.fiap.globalSolution.dominio.ConjuntoPacientes;
import br.com.fiap.globalSolution.dominio.Paciente;
import br.com.fiap.globalSolution.exceptions.CpfDuplicadoException;
import br.com.fiap.globalSolution.exceptions.PacienteNaoEncontradoException;
import br.com.fiap.globalSolution.exceptions.PacienteQueryException;

public class PacienteService {

	private ConjuntoPacientes conjuntoPacientes;

	public PacienteService(ConjuntoPacientes conjuntoPacientes) {

		this.conjuntoPacientes = conjuntoPacientes;
	}

	public void adicionar(Paciente paciente) throws PacienteQueryException, CpfDuplicadoException {
		this.checarCpf(paciente.getCpf());

		conjuntoPacientes.adicionar(paciente);
		conjuntoPacientes.fechar();
	}

	public void atualizarPaciente(Paciente paciente, String cpf)
			throws PacienteNaoEncontradoException, PacienteQueryException, CpfDuplicadoException {
		conjuntoPacientes.atualizarPaciente(paciente, cpf);
		conjuntoPacientes.fechar();
	}

	private void checarCpf(String cpf) throws CpfDuplicadoException {
		if (conjuntoPacientes.checarCpf(cpf)) {
			throw new CpfDuplicadoException("Paciente já cadastrado com esse CPF..");
		}
	}

	public void excluir(String cpf) throws PacienteQueryException, PacienteNaoEncontradoException {
		this.pacienteExiste(cpf);
		conjuntoPacientes.excluir(cpf);
		conjuntoPacientes.fechar();		
	}

	private void pacienteExiste(String cpf) throws PacienteNaoEncontradoException {
		Paciente p = conjuntoPacientes.buscarPacientePorCpf(cpf);
		if(p == null) {
			throw new PacienteNaoEncontradoException("Paciente com esse CPF não foi encontrado no sistema");
	}
}
}