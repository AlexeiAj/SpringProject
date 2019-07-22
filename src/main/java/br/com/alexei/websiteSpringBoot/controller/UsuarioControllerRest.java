package br.com.alexei.websiteSpringBoot.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alexei.websiteSpringBoot.model.Usuario;
import br.com.alexei.websiteSpringBoot.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControllerRest {

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public List<Usuario> usuarios() {
		return (List<Usuario>) usuarioService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> usuario(@PathVariable Integer id) {
		Optional<Usuario> usuarioOpt = usuarioService.findById(id);
		
		if(usuarioOpt.isPresent()) return ResponseEntity.ok(usuarioOpt.get());
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Usuario> adicionar(@RequestBody @Valid Usuario usuario, UriComponentsBuilder uriBuilder){
		usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
		usuarioService.save(usuario);
		
		URI uri = uriBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri();
		
		return ResponseEntity.created(uri).body(usuario);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Usuario> alterar(@PathVariable Integer id, @RequestBody @Valid Usuario usuario){
		Optional<Usuario> usuarioOpt = usuarioService.findById(id);
		
		if(usuarioOpt.isPresent()) {
			usuario.setId(usuarioOpt.get().getId());
			usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
			usuarioService.edit(usuario);
			return ResponseEntity.ok(usuario);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Integer id){
		Optional<Usuario> usuarioOpt = usuarioService.findById(id);
	
		if(usuarioOpt.isPresent()) {
			usuarioService.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
