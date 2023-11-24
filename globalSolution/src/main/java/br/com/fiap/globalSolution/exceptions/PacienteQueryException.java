package br.com.fiap.globalSolution.exceptions;

public class PacienteQueryException extends Exception {

	 private String tipoOperacao;

	    public PacienteQueryException(String tipoOperacao, String mensagem) {
	        super(mensagem);
	        this.tipoOperacao = tipoOperacao;
	    }

	    public String getTipoOperacao() {
	        return tipoOperacao;
	    }
	}

