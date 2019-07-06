package br.com.alexei.websiteSpringBoot.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.alexei.websiteSpringBoot.model.Imagem;
import br.com.alexei.websiteSpringBoot.repository.ImagemRepository;

@Service
public class ImagemService {
	
	@Autowired
	private ImagemRepository repository;
	
	public Iterable<Imagem> findAll(){
		return repository.findAll();
	}
	
	public Optional<Imagem> findById(int id){
		return repository.findById(id);
	}
	
	public Imagem save(Imagem post){
		return repository.save(post);
	}

	public Imagem edit(Imagem post){
		return repository.save(post);
	}
	
	public void deleteById(int id) {
		repository.deleteById(id);
	}
}
