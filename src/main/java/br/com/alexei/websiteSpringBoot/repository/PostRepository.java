package br.com.alexei.websiteSpringBoot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.alexei.websiteSpringBoot.model.Post;

public interface PostRepository extends CrudRepository<Post, Integer>{

	@Query(value = "select * from Posts p where p.post_titulo like %?1% or p.post_categoria like %?1% ", nativeQuery = true)
	List<Post> findPostByTituloECategoria(String pesquisa);
	
}
