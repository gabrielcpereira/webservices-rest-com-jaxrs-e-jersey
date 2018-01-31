package br.com.alura.loja.resource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;

@Path("carrinhos")
public class CarrinhoResource {
//	@Path(value="{id}")
//	@GET
//	@Produces(MediaType.APPLICATION_XML)
//	public String busca(@PathParam("id") long id) {
//		CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
//		return carrinhoDAO.busca(id).toXML();
//	}
	
	@Path(value="{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String busca(@PathParam("id") long id) {
		CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
		return carrinhoDAO.busca(id).toJson();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_XML)
	public String adiciona(String json) {
		Carrinho carrinhoNovo = (new Gson()).fromJson(json, Carrinho.class);
		new CarrinhoDAO().adiciona(carrinhoNovo); 
		
		return "<status>SUCESSO</status>";
	}
}
