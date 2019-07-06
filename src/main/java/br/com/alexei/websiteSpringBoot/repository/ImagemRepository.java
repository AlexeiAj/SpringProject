package br.com.alexei.websiteSpringBoot.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.alexei.websiteSpringBoot.model.Imagem;

public interface ImagemRepository extends CrudRepository<Imagem, Integer>{}
