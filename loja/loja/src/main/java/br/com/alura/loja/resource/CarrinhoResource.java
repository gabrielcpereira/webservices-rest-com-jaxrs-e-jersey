package br.com.alura.loja.resource;

import java.net.URI;

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

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;

@Path("carrinhos")
public class CarrinhoResource {
	@Path(value = "{id}")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String busca(@PathParam("id") long id) {
		CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
		return carrinhoDAO.busca(id).toXML();
	}

	@Path("adiciona/json")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response adicionaJson(String json) {
		Response response = null;
		try {
			Carrinho carrinhoNovo = (new Gson()).fromJson(json, Carrinho.class);
			(new CarrinhoDAO()).adiciona(carrinhoNovo);

			URI location = URI.create("/carrinhos/" + carrinhoNovo.getId());
			response = Response.created(location).build();
		} catch (Exception e) {
			response = Response.serverError().build();
			response.getHeaders().add("mensagemError", e.getMessage());
		}
		return response;
	}

	@Path("adiciona/xml")
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response adicionaXml(Carrinho carrinho) {
		Response response = null;
		try {			
			(new CarrinhoDAO()).adiciona(carrinho);

			URI location = URI.create("/carrinhos/" + carrinho.getId());
			response = Response.created(location).build();
		} catch (Exception e) {
			response = Response.serverError().build();
			response.getHeaders().add("mensagemError", e.getMessage());
		}
		return response;
	}

	@Path("remover/{idCarrinho}/produto/{idProduto}")
	@DELETE
	public Response removeProdutoCarrinho(@PathParam("idCarrinho") long idCarrinho,
			@PathParam("idProduto") long idProduto) {
		Carrinho carrinho = new CarrinhoDAO().busca(idCarrinho);
		carrinho.remove(idProduto);

		return Response.ok().build();
	}

	@Path("{id}/produtos/{produtoId}")
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response alteraProduto(@PathParam("id") long id, @PathParam("produtoId") long produtoId, Produto produto) {
		Carrinho carrinho = new CarrinhoDAO().busca(id);		
		carrinho.trocaQuantidade(produto);
		return Response.ok().build();
	}
}
