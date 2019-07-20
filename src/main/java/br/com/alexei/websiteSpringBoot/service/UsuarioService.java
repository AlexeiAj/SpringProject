package br.com.alexei.websiteSpringBoot.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alexei.websiteSpringBoot.model.Usuario;
import br.com.alexei.websiteSpringBoot.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	
	public Iterable<Usuario> findAll(){
		return repository.findAll();
	}
	
	public Optional<Usuario> findById(int id){
		return repository.findById(id);
	}
	
	public Optional<Usuario> findByLogin(String login){
		return repository.findByLogin(login);
	}
	
	public Usuario save(Usuario usuario){
		return repository.save(usuario);
	}

	public Usuario edit(Usuario usuario){
		return repository.save(usuario);
	}
	
	public void deleteById(int id) {
		repository.deleteById(id);
	}
	
//	public boolean exist(Usuario usuario) {
//		return repository.getByLoginESenha(usuario.getLogin(), usuario.getSenha()).size() > 0 && !repository.getByLoginESenha(usuario.getLogin(), usuario.getSenha()).isEmpty() && repository.getByLoginESenha(usuario.getLogin(), usuario.getSenha()) != null;
//	}
}
