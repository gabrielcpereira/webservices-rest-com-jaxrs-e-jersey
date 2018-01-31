package br.com.alura.loja;

import java.io.IOException;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.modelo.Projeto;
import junit.framework.Assert;

public class ClienteTest {
		
//	private HttpServer server;
//	
//	@Before
//	public void iniciarServer() throws IOException {	
//		server = Servidor.iniciarServer();
//	}
//	
//	@After
//	public void encerrarServer() {
//		Servidor.pararServer(server);
//	}
	
//	@Test
//	public void testaQueAConexaoComOServidorFunciona() {		
//		String urlClient = Servidor.getHost();
//		WebTarget target = (ClientBuilder.newClient()).target(urlClient);
//		
//		String conteudo = target.path("carrinhos/1").request().get(String.class);
//		Assert.assertTrue(conteudo.contains("<rua>Rua Vergueiro 3185"));
//	}	
//	
//	@Test
//	public void testaQueAConexaoComOServidorFuncionaNoPathDeProjetos() {
//		String urlClient = Servidor.getHost();
//		WebTarget target = (ClientBuilder.newClient()).target(urlClient);
//		
//		String conteudo = target.path("projetos/1").request().get(String.class);
//		Assert.assertTrue(conteudo.contains("<nome>Minha loja"));
//	}
//	
//	@Test
//    public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {
//		String urlClient = Servidor.getHost();
//		WebTarget target = (ClientBuilder.newClient()).target(urlClient);
//		
//		String conteudo = target.path("carrinhos/1").request().get(String.class);
//		Carrinho carrinho = (Carrinho)new XStream().fromXML(conteudo);
//		Assert.assertEquals("Rua Vergueiro 3185", carrinho.getRua());
//    }
//	
//	@Test
//    public void testaQueBuscarUmProjetoTrazOProjetoEsperado() {
//		String urlClient = Servidor.getHost();
//		WebTarget target = (ClientBuilder.newClient()).target(urlClient);
//		
//		String conteudo = target.path("projetos/1")
//				.request()
//				.get(String.class);
//		Projeto projeto = (Projeto)new XStream().fromXML(conteudo);
//		Assert.assertEquals("Minha loja", projeto.getNome());
//    }
	
	@Test
	public void testaCriacaoCarrinho() {
		Produto videogame = new Produto(1, "Videogame 4", 4000, 1);
		Produto esporte = new Produto(2, "Jogo de esporte", 60, 2);
		Carrinho carrinhoNovo = new Carrinho()
				.adiciona(videogame)
				.adiciona(esporte)
				.para("Rua Vergueiro 3185, 8 andar", "S‹o Paulo")
				.setId(2l);		
		String json = (new Gson()).toJson(carrinhoNovo);
		
		String urlClient = Servidor.getHost();
		WebTarget target = (ClientBuilder.newClient()).target(urlClient);
				
		Entity<String> entity = Entity.entity(json, MediaType.APPLICATION_JSON);
		Response response = target.path("carrinhos")
				.request()
				.post(entity);
		
        Assert.assertEquals("<status>sucesso</status>", response.readEntity(String.class));
	}
}
