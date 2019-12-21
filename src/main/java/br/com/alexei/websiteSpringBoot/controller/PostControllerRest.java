package br.com.alexei.websiteSpringBoot.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alexei.websiteSpringBoot.model.Post;
import br.com.alexei.websiteSpringBoot.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostControllerRest {

	@Autowired
	private PostService postService;
	
	@GetMapping
	public List<Post> posts(@RequestParam(value = "search", required = false) String search) {
		if(search != null) return (List<Post>) postService.findPostByTituloECategoria(search);
		return (List<Post>) postService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Post> post(@PathVariable Integer id) {
		Optional<Post> postOpt = postService.findById(id);
		
		if(postOpt.isPresent()) return ResponseEntity.ok(postOpt.get());
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Post> adicionar(@RequestBody @Valid Post post, UriComponentsBuilder uriBuilder){
		postService.save(post);
		
		URI uri = uriBuilder.path("/posts/{id}").buildAndExpand(post.getId()).toUri();
		
		return ResponseEntity.created(uri).body(post);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Post> alterar(@PathVariable Integer id, @RequestBody @Valid Post post){
		Optional<Post> postOpt = postService.findById(id);
		
		if(postOpt.isPresent()) {
			post.setId(postOpt.get().getId());
			postService.edit(post);
			return ResponseEntity.ok(post);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Integer id){
		Optional<Post> postOpt = postService.findById(id);
	
		if(postOpt.isPresent()) {
			postService.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
