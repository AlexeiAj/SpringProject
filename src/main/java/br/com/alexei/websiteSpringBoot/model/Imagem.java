package br.com.alexei.websiteSpringBoot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Transient;

import com.google.gson.Gson;

@Entity(name="imagens")
public class Imagem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	private String imagem_name;
	
	@NotNull
	private String imagem_path;
	
	@NotNull
	private String imagem_file_path;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImagem_name() {
		return imagem_name;
	}

	public void setImagem_name(String imagem_name) {
		this.imagem_name = imagem_name;
	}
	
	public String getImagem_path() {
		return imagem_path;
	}
	
	public void setImagem_path(String imagem_path) {
		this.imagem_path = imagem_path;
	}
	
	public String getImagem_file_path() {
		return imagem_file_path;
	}
	
	public void setImagem_file_path(String imagem_file_path) {
		this.imagem_file_path = imagem_file_path;
	}
}
