package br.com.fiap.globalSolution.infra.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	public Connection getConnection() {
		String urlDeConexao = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
		String login = "rm99456";
		String senha = "210503";

		try {
			return DriverManager.getConnection(urlDeConexao, login, senha);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
