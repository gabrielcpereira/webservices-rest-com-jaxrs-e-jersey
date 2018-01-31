package br.com.alura.loja;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Servidor {
	
	private static final String host = "http://localhost:8080/";
	
	public static String getHost() {
		return host;
	}
	
	public static void main(String[] args) throws IOException {
		HttpServer server = iniciarServer();		
        pararServer(server);
	}

	public static void pararServer(HttpServer server) {
		server.stop();
	}

	public static HttpServer iniciarServer() throws IOException {
		URI uri = URI.create(host);
		String packages = "br.com.alura.loja";
		ResourceConfig config = new ResourceConfig().packages(packages);
		HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, config);
		
		System.out.println("::::::::::::::::::::::::::::::::::::> Started server <::::::::::::::::::::::::::::::::::::");
		System.in.read();
		
		return server;
	}
}
