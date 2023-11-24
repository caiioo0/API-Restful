package br.com.fiap.globalSolution.infra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import br.com.fiap.globalSolution.dominio.ConjuntoPacientes;
import br.com.fiap.globalSolution.dominio.Paciente;
import br.com.fiap.globalSolution.exceptions.PacienteQueryException;

public class PacienteDAO implements ConjuntoPacientes {

	private Connection conexao;

	public PacienteDAO() {
		conexao = new ConnectionFactory().getConnection();
	}

	@Override
	public void adicionar(Paciente paciente) throws PacienteQueryException {

		String sql = "INSERT INTO pacientes (nome, cpf, genero, email, senha) VALUES(?, ?, ?, ?, ?)";
		try {
			PreparedStatement comandoDeInsercao = conexao.prepareStatement(sql);
			comandoDeInsercao.setString(1, paciente.getNome());
			comandoDeInsercao.setString(2, paciente.getCpf());
			comandoDeInsercao.setString(3, paciente.getGenero());
			comandoDeInsercao.setString(4, paciente.getEmail());
			comandoDeInsercao.setString(5, paciente.getSenha());
			comandoDeInsercao.execute();
			comandoDeInsercao.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new PacienteQueryException("adicionar", ex.getMessage());
		}
	}

	@Override
	public Paciente buscarPacientePorCpf(String cpf) {
		Paciente paciente = null;
		try {
			String sql = "SELECT * FROM pacientes WHERE cpf = ?";
			PreparedStatement comandoDeSelecao = conexao.prepareStatement(sql);
			comandoDeSelecao.setString(1, cpf);
			ResultSet resultados = comandoDeSelecao.executeQuery();
			while (resultados.next()) {
				paciente = new Paciente(resultados.getString("nome"), resultados.getString("cpf"),
						resultados.getString("genero"), resultados.getString("email"),
						resultados.getString("senha"));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return paciente;
	}

	@Override
	public void atualizarPaciente(Paciente pacienteAtualizado, String cpf) throws PacienteQueryException {
		String sqlUpdate = "UPDATE pacientes SET nome=?, genero=?,email=?, senha=? WHERE cpf=?";
		try {
			PreparedStatement comandoDeAtualizacao = conexao.prepareStatement(sqlUpdate);
			comandoDeAtualizacao.setString(1, pacienteAtualizado.getNome());
			comandoDeAtualizacao.setString(2, pacienteAtualizado.getGenero());
			comandoDeAtualizacao.setString(3, pacienteAtualizado.getEmail());
			comandoDeAtualizacao.setString(4, pacienteAtualizado.getSenha());
			comandoDeAtualizacao.setString(5, cpf);
			comandoDeAtualizacao.executeUpdate();
			comandoDeAtualizacao.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new PacienteQueryException("atualizar", ex.getMessage());
		}
	}

	@Override
	public void excluir(String cpf) {
		String sqlDelete = "DELETE FROM pacientes WHERE cpf=?";
		try {
			PreparedStatement comandoDeExclusao = conexao.prepareStatement(sqlDelete);
			comandoDeExclusao.setString(1, cpf);
			comandoDeExclusao.executeUpdate();
			comandoDeExclusao.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean checarCpf(String cpf) {

		boolean podeAlterar = true;

		String sql = "SELECT count(*) FROM pacientes WHERE cpf = ?";

		try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
			preparedStatement.setString(1, cpf);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					int atendimentos = resultSet.getInt(1);
					podeAlterar = atendimentos > 0;
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return podeAlterar;
	}

	@Override
	public void fechar() {
		try {
			conexao.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
