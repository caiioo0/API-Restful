package br.com.fiap.globalSolution.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.fiap.globalSolution.dominio.ConjuntoPacientes;
import br.com.fiap.globalSolution.dominio.Paciente;
import br.com.fiap.globalSolution.exceptions.CpfDuplicadoException;
import br.com.fiap.globalSolution.exceptions.PacienteNaoEncontradoException;
import br.com.fiap.globalSolution.exceptions.PacienteQueryException;
import br.com.fiap.globalSolution.infra.dao.PacienteDAO;
import br.com.fiap.globalSolution.service.PacienteService;

@Path("pacientes")
public class PacienteController {

	private ConjuntoPacientes pacienteDao;
	private PacienteService pacienteService;

	public PacienteController() {
		pacienteDao = new PacienteDAO();
		pacienteService = new PacienteService(pacienteDao);
	}

	@POST
	public Response adicionarPaciente(Paciente paciente) {
		try {
			pacienteService.adicionar(paciente);
		} catch (PacienteQueryException ex) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Erro ao " + ex.getTipoOperacao() + " o paciente: " + ex.getMessage()).build();

		} catch (CpfDuplicadoException ex) {
			return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
		}

		return Response.status(Response.Status.CREATED).build();
	}

	@GET
	@Path("/{cpf}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPacientePorCpf(@PathParam("cpf") String cpf) {
		Response.Status status = null;
		Paciente paciente = pacienteDao.buscarPacientePorCpf(cpf);
		pacienteDao.fechar();
		if (paciente == null)
			status = Response.Status.NOT_FOUND;
		else
			status = Response.Status.OK;
		return Response.status(status).entity(paciente).build();
	}

	@PUT
	@Path("/{cpf}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response atualizarPaciente(Paciente paciente, @PathParam("cpf") String cpf) throws CpfDuplicadoException {

		try {
			pacienteService.atualizarPaciente(paciente, cpf);
		} catch (PacienteNaoEncontradoException ex) {	
			return Response.status(Response.Status.NOT_FOUND).entity("Paciente não encontrado para o CPF: " + cpf)
					.build();
		} catch (PacienteQueryException ex) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Erro ao " + ex.getTipoOperacao() + " o paciente: " + ex.getMessage()).build();
		}
		return Response.status(Response.Status.OK).entity("Atualizado com Sucesso").build();

	}

	@DELETE
	@Path("/{cpf}")
	public Response excluirPaciente(@PathParam("cpf") String cpf) {
		try {
			pacienteService.excluir(cpf);
		} catch (PacienteNaoEncontradoException ex) {
			return Response.status(Response.Status.NOT_FOUND).entity("Paciente não encontrado para o CPF: " + cpf)
					.build();
		} catch (PacienteQueryException ex) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Erro ao " + ex.getTipoOperacao() + " o paciente: " + ex.getMessage()).build();
		} 
		return Response.status(Response.Status.ACCEPTED).entity("Removido com Sucesso").build();

	}
}