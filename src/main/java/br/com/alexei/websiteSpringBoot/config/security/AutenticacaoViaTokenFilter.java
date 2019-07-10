package br.com.alexei.websiteSpringBoot.config.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.alexei.websiteSpringBoot.model.Usuario;
import br.com.alexei.websiteSpringBoot.service.UsuarioService;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter{

	private TokenService tokenService;
	private UsuarioService usuarioService;
	
	public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioService usuarioService) {
		this.tokenService = tokenService;
		this.usuarioService = usuarioService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		String token = recuperarToken(request);
		boolean tokenValido = tokenService.isTokenValido(token);
		
		if(tokenValido) autenticarUsuario(token);
		
		filterChain.doFilter(request, response);
	}

	private void autenticarUsuario(String token) {
		Integer idUsuario = tokenService.getIdUsuario(token);
		Optional<Usuario> usuarioOpt = usuarioService.findById(idUsuario);
		
		if(usuarioOpt.isPresent()) {
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuarioOpt.get(), null, usuarioOpt.get().getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
	}

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) return null;
		
		return token.substring(7, token.length());
	}

}
