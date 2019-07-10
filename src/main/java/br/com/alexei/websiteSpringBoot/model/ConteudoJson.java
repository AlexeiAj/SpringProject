package br.com.alexei.websiteSpringBoot.model;

import com.google.gson.Gson;

public class ConteudoJson {
	
	private String imagem;
	private String texto;
	
	public String getImagem() {
		return imagem;
	}
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public String getJson() {
		return new Gson().toJson(this);
	}
}
