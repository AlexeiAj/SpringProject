package br.com.alexei.websiteSpringBoot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Transient;

import com.google.gson.Gson;

@Entity(name="posts")
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
    @Size(min=5, max=200)
	private String post_titulo;
	
	private String post_categoria;
	
	@Size(max=2000)
	private String post_conteudo;
	
	@NotNull
	private String post_data;
	
	public String getTexto() {
		if(getConteudoJson().getTexto() != null) return getConteudoJson().getTexto();
		return "";
	}
	
	public String getLink() {
		if(getConteudoJson().getLink() != null) return getConteudoJson().getLink();
		return "";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPost_titulo() {
		return post_titulo;
	}

	public void setPost_titulo(String post_titulo) {
		this.post_titulo = post_titulo;
	}

	public String getPost_categoria() {
		return post_categoria;
	}

	public void setPost_categoria(String post_categoria) {
		this.post_categoria = post_categoria;
	}

	public String getPost_conteudo() {
		return post_conteudo;
	}

	public void setPost_conteudo(String post_conteudo) {
		this.post_conteudo = post_conteudo;
	}

	public String getPost_data() {
		return post_data;
	}

	public void setPost_data(String post_data) {
		this.post_data = post_data;
	}
	
	public ConteudoJson getConteudoJson() {
		return new Gson().fromJson(post_conteudo, ConteudoJson.class);
	}
	
}
