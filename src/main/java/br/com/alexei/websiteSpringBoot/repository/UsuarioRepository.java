package br.com.alexei.websiteSpringBoot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.alexei.websiteSpringBoot.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer>{
	
//	@Query(value = "select u.id from Usuarios u where u.login = ?1 and u.senha = ?2 ", nativeQuery = true)
//	List<Integer> getByLoginESenha(String login, String senha);
	
	Optional<Usuario> findByLogin(String login);
	
}
