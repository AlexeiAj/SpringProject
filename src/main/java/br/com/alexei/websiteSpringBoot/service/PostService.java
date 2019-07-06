package br.com.alexei.websiteSpringBoot.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alexei.websiteSpringBoot.model.Post;
import br.com.alexei.websiteSpringBoot.repository.PostRepository;

@Service
public class PostService {
	
	@Autowired
	private PostRepository repository;
	
	public Iterable<Post> findAll(){
		return repository.findAll();
	}
	
	public Optional<Post> findById(int id){
		return repository.findById(id);
	}
	
	public Post save(Post post){
		return repository.save(post);
	}

	public Post edit(Post post){
		return repository.save(post);
	}
	
	public void deleteById(int id) {
		repository.deleteById(id);
	}
	
	public Iterable<Post> findPostByTituloECategoria(String pesquisa) {
		return repository.findPostByTituloECategoria(pesquisa);
	}
}
