package br.com.alexei.websiteSpringBoot.config.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import br.com.alexei.websiteSpringBoot.service.UsuarioService;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	//Configuracoes de autenticacao
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	//Configuracoes de autorizacao
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
			.and()
			.authorizeRequests()
			.antMatchers("/cadastroUsuarios/**").permitAll()
			.antMatchers("/carregarUsuariosAjax/**").permitAll()
			.antMatchers("/carregarUsuarioAjax/**").permitAll()
			.antMatchers("/adicionarUsuarioAjax/**").permitAll()
			.antMatchers("/alterarUsuarioAjax/**").permitAll()
			.antMatchers("/removerUsuarioAjax/**").permitAll()
			.antMatchers("/logar/**").permitAll()
			.antMatchers("/login/**").permitAll()
			.antMatchers("/website/**").permitAll()
			.antMatchers("/filtrarPostsAjax/**").permitAll()
			.antMatchers("/carregarPostsAjax/**").permitAll()
			.antMatchers("/carregarPostAjax/**").permitAll()
			.antMatchers("/adicionarPostAjax/**").permitAll()
			.antMatchers("/alterarPostAjax/**").permitAll()
			.antMatchers("/removerPostAjax/**").permitAll()
			.antMatchers("/galeria/**").permitAll()
			.antMatchers("/carregarImagensAjax/**").permitAll()
			.antMatchers("/carregarImagensSelectAjax/**").permitAll()
			.antMatchers("/carregarImagemAjax/**").permitAll()
			.antMatchers("/adicionarImagemAjax/**").permitAll()
			.antMatchers("/removeImagemAjax/**").permitAll()
			
			.antMatchers("/actuator/**").permitAll()
			.antMatchers("/images/**").permitAll()
			.antMatchers("/webjars/**").permitAll()
			.antMatchers(HttpMethod.POST, "/auth").permitAll()
			.anyRequest().authenticated()
//			.and().formLogin()
//            .loginPage("/login")
//            .defaultSuccessUrl("/")
//            .permitAll()
			.and()
			.csrf()
			.disable()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioService), UsernamePasswordAuthenticationFilter.class);
	}
	
	//Configuracoes de recursos estaticos(js, css, imagem, etc..)
	@Override
	public void configure(WebSecurity web) throws Exception {
	}
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	@Bean
    CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(Arrays.asList("*"));
	    configuration.setAllowCredentials(true);
	    configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Headers","Access-Control-Allow-Origin","Access-Control-Request-Method", "Access-Control-Request-Headers","Origin","Cache-Control", "Content-Type", "Authorization"));
	    configuration.setAllowedMethods(Arrays.asList("DELETE", "GET", "POST", "PATCH", "PUT"));
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
    }
}
