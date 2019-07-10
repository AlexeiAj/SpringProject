package br.com.alexei.websiteSpringBoot.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.alexei.websiteSpringBoot.model.Usuario;
import br.com.alexei.websiteSpringBoot.service.UsuarioService;

@Service
public class AutenticacaoService implements UserDetailsService{

	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuarioOpt = usuarioService.findByLogin(username);
		if(usuarioOpt.isPresent()) return usuarioOpt.get();
		
		throw new UsernameNotFoundException("Dados invalidos!");
	}
}
