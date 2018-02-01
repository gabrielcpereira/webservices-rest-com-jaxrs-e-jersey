package br.com.alura.loja;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.Test;

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
	
	@Test
	public void testaQueAConexaoComOServidorFunciona() {		
		String urlClient = Servidor.getHost();
		WebTarget target = (ClientBuilder.newClient()).target(urlClient);
		
		String conteudo = target.path("carrinhos/1").request().get(String.class);
		Assert.assertTrue(conteudo.contains("<rua>Rua Vergueiro 3185"));
	}	
		
	@Test
    public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {
		String urlClient = Servidor.getHost();
		WebTarget target = (ClientBuilder.newClient()).target(urlClient);
		
		String conteudo = target.path("carrinhos/1").request().get(String.class);
		Carrinho carrinho = (Carrinho)new XStream().fromXML(conteudo);
		Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
    }
		
	@Test
	public void testaCriacaoCarrinhoJson() {
		Produto videogame = new Produto(1, "Videogame 4", 4000, 1);
		Produto esporte = new Produto(2, "Jogo de esporte", 60, 2);
		Carrinho carrinhoNovo = new Carrinho()
				.adiciona(videogame)
				.adiciona(esporte)
				.para("Rua Vergueiro 3185, 8 andar", "São Paulo")
				.setId(2l);		
		String json = carrinhoNovo.toJson();
	
		String recurso = "carrinhos/adiciona/json";
		Response response = postRecurso(json, recurso, MediaType.APPLICATION_JSON);
		
		Assert.assertEquals(201, response.getStatus());
	}
	
	@Test
	public void testaCriacaoCarrinhoXml() {
		Produto videogame = new Produto(1, "Videogame 5", 4000, 1);
		Produto esporte = new Produto(2, "Jogo de esporte 1", 60, 2);
		Carrinho carrinhoNovo = new Carrinho()
				.adiciona(videogame)
				.adiciona(esporte)
				.para("Rua Vergueiro 3185, 9 andar", "São Paulo")
				.setId(3l);		
		String xmlCarrinho = carrinhoNovo.toXML();
		
		String recurso = "carrinhos/adiciona/xml";
//		Response response = postRecurso(xmlCarrinho, recurso, MediaType.APPLICATION_XML);
//	
//		Assert.assertEquals(201, response.getStatus());
				
		String urlClient = Servidor.getHost();
		ClientConfig config = new ClientConfig();
		config.register(new LoggingFilter());
		WebTarget target = (ClientBuilder.newClient(config)).target(urlClient);

		Entity<Carrinho> entity = Entity.entity(carrinhoNovo, MediaType.APPLICATION_XML);
		Response response = target.path(recurso)
				.request()
				.post(entity);
		
		Assert.assertEquals(201, response.getStatus());
	}
	
	@Test
	public void testaAlteraQuantidadeProdutoUmCarrinhoUm() {
		Produto videogame = new Produto(1, "Videogame 5", 4000, 2);
		
		String urlClient = Servidor.getHost();
		WebTarget target = (ClientBuilder.newClient()).target(urlClient);
		
		String recurso = "carrinhos/2/produtos/1";
		Entity<Produto> entity = Entity.entity(videogame, MediaType.APPLICATION_XML);
		Response response = target.path(recurso)
				.request()
				.buildPut(entity)
				.invoke();
		
		Assert.assertEquals(200, response.getStatus());
	}
	
	@Test
	public void testaRemocaoProdutoDoisCarrinhoUm() {	
		String urlClient = Servidor.getHost();
		WebTarget target = (ClientBuilder.newClient()).target(urlClient);
		
		String recurso = "carrinhos/remover/1/produto/2";
		Response response = target.path(recurso)
				.request()
				.buildDelete()
				.invoke();
		
		Assert.assertEquals(200, response.getStatus());
	}
	
	@Test
	public void testaCriacaoNovoProjeto() {			
		Projeto projeto = new Projeto(1l, "Minha loja", 2014);
		String xmlProjeto = projeto.toXML();

		String recurso = "projetos/adiciona/xml";
		Response response = postRecurso(xmlProjeto, recurso, MediaType.APPLICATION_XML);
		
		Assert.assertEquals(201, response.getStatus());
	}
	
	@Test
	public void testaRemocaoProjetoUm() {
		String urlClient = Servidor.getHost();
		WebTarget target = (ClientBuilder.newClient()).target(urlClient);
		
		String recurso = "projetos/remove/1";
		Response response = target.path(recurso)
				.request()
				.buildDelete()
				.invoke();
		
		Assert.assertEquals(200, response.getStatus());
	}

	private Response postRecurso(String mensagem, String recurso, String mediaType) {
		String urlClient = Servidor.getHost();
		ClientConfig config = new ClientConfig();
		config.register(new LoggingFilter());
		WebTarget target = (ClientBuilder.newClient(config)).target(urlClient);

		Entity<String> entity = Entity.entity(mensagem, mediaType);
		Response response = target.path(recurso)
				.request()
				.post(entity);

		return response;
	}
}
