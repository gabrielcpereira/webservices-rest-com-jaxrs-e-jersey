package br.com.alura.loja.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.dao.ProjetoDAO;
import br.com.alura.loja.modelo.Projeto;

@Path("projetos")
public class ProjetoResource {
	@Path(value="{id}")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String busca(@PathParam("id") long id) {
		return (new ProjetoDAO()).busca(id).toXML();
	}
//	@Path(value="{id}")
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public String busca(@PathParam("id") long id) {
//		return (new ProjetoDAO()).busca(id).toJson();
//	}
	
	@Path(value="adiciona/xml")
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response adicionaXml(String xml) {
		Projeto projeto = (Projeto)(new XStream()).fromXML(xml);
		new ProjetoDAO().adiciona(projeto);
		
		URI location = URI.create("/projetos/" + projeto.getId());
		return Response.created(location ).build();
	}
	
	@Path(value="remove/{id}")
	@DELETE
	public Response removeProduto(@PathParam("id") long id) {		
		(new ProjetoDAO()).remove(id);		
		return Response.ok().build();
	}
}
