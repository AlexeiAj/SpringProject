package br.com.alexei.websiteSpringBoot.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.alexei.websiteSpringBoot.model.ConteudoJson;
import br.com.alexei.websiteSpringBoot.model.Filtro;
import br.com.alexei.websiteSpringBoot.model.Post;
import br.com.alexei.websiteSpringBoot.service.PostService;

@Controller
public class PostController {
	
	@Autowired
	private PostService postService;

	@RequestMapping("/website")
	public ModelAndView website() {
		ModelAndView mv = new ModelAndView("website");
		
		mv.addObject("posts", postService.findAll());
		mv.addObject("post", new Post());
		mv.addObject("filtro", new Filtro());
		mv.addObject("conteudoJson", new ConteudoJson());
		
		return mv;
	}
	
	@PostMapping("/filtrarPostsAjax")
	public ModelAndView filtrarPostsAjax(@ModelAttribute("filtro") Filtro filtro) {
		ModelAndView mv = new ModelAndView("website :: fragment-posts");
		
		mv.addObject("posts", postService.findPostByTituloECategoria(filtro.getPesquisa()));
		mv.addObject("post", new Post());
		mv.addObject("filtro", new Filtro());
		mv.addObject("conteudoJson", new ConteudoJson());
		
		return mv;
	}
	
	@PostMapping("/carregarPostsAjax")
	public ModelAndView carregarPostsAjax() {
		ModelAndView mv = new ModelAndView("website :: fragment-posts");
		
		mv.addObject("posts", postService.findAll());
		mv.addObject("post", new Post());
		mv.addObject("filtro", new Filtro());
		mv.addObject("conteudoJson", new ConteudoJson());
		
		return mv;
	}
	
	@PostMapping("/carregarPostAjax/{id}")
	public ModelAndView carregarPostAjax(@PathVariable("id") int id) {
		ModelAndView mv = new ModelAndView("website :: fragment-form");
		
		Post postResposta = postService.findById(id).get();
		
		mv.addObject("post", postResposta);
		mv.addObject("filtro", new Filtro());
		mv.addObject("conteudoJson", postResposta.getConteudoJson());
		
		return mv;
	}
	
	@PostMapping("/adicionarPostAjax")
	public ModelAndView adicionarPostAjax(@ModelAttribute("post") @Valid Post post, BindingResult bindingResult, @ModelAttribute("conteudoJson") ConteudoJson conteudoJson) {
		ModelAndView mv = new ModelAndView("website");
		
		mv.addObject("post", new Post());
		mv.addObject("filtro", new Filtro());
		mv.addObject("conteudoJson", new ConteudoJson());
		
		if (bindingResult.hasErrors()) return new ModelAndView("website :: fragment-form");
		
		post.setPost_conteudo(conteudoJson.getJson());
		if("".equals(post.getPost_data())) {
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
			post.setPost_data(String.valueOf(dateFormat.format(LocalDateTime.now())));
		}
		postService.save(post);
		
		return mv;
	}
	
	@PutMapping("/alterarPostAjax")
	public ModelAndView alterarPostAjax(@ModelAttribute("post") @Valid Post post, BindingResult bindingResult, @ModelAttribute("conteudoJson") ConteudoJson conteudoJson) {
		ModelAndView mv = new ModelAndView("website");
		
		mv.addObject("post", new Post());
		mv.addObject("filtro", new Filtro());
		mv.addObject("conteudoJson", new ConteudoJson());
		
		if (bindingResult.hasErrors()) return new ModelAndView("website :: fragment-form");
		
		post.setPost_conteudo(conteudoJson.getJson());
		postService.edit(post);
		
		return mv;
	}
	
	@DeleteMapping("/removerPostAjax/{id}")
	public void removerPostAjax(@PathVariable("id") int id, HttpServletResponse response) {
		postService.deleteById(id);
		response.setStatus(200);
	}
}
