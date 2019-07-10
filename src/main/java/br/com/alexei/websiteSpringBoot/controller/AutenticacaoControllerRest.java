package br.com.alexei.websiteSpringBoot.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alexei.websiteSpringBoot.config.security.TokenDto;
import br.com.alexei.websiteSpringBoot.config.security.TokenService;
import br.com.alexei.websiteSpringBoot.model.Usuario;

@RestController
@RequestMapping("/auth")
public class AutenticacaoControllerRest {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid Usuario usuario) {
		UsernamePasswordAuthenticationToken dados = new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getSenha(), new ArrayList<>());
		
		try {
			Authentication authentication = authManager.authenticate(dados);
			String token = tokenService.gerarToken(authentication);
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));
		}catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
