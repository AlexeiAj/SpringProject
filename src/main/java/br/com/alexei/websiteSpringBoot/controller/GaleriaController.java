package br.com.alexei.websiteSpringBoot.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.alexei.websiteSpringBoot.model.Imagem;
import br.com.alexei.websiteSpringBoot.service.ImagemService;

@Controller
public class GaleriaController {
	
	@Autowired
	private ImagemService imagemService;

	@RequestMapping("/galeria")
	public ModelAndView galeria() {
		ModelAndView mv = new ModelAndView("galeria");
		
		mv.addObject("imagens", imagemService.findAll());
		mv.addObject("imagem", new Imagem());
		
		return mv;
	}
	
	@PostMapping("/carregarImagensAjax")
	public ModelAndView carregarImagensAjax() {
		ModelAndView mv = new ModelAndView("galeria :: fragment-imagens");
		
		mv.addObject("imagens", imagemService.findAll());
		mv.addObject("imagem", new Imagem());
		
		return mv;
	}
	
	@PostMapping("/carregarImagensSelectAjax")
	public ModelAndView carregarImagensSelectAjax() {
		ModelAndView mv = new ModelAndView("website :: fragment-imagens");
		
		mv.addObject("imagens", imagemService.findAll());
		mv.addObject("imagem", new Imagem());
		
		return mv;
	}
	
	@PostMapping("/carregarImagemAjax/{id}")
	public ModelAndView carregarPostAjax(@PathVariable("id") int id) {
		ModelAndView mv = new ModelAndView("galeria :: fragment-form");
		
		Imagem imagemResposta = imagemService.findById(id).get();
		
		mv.addObject("imagem", imagemResposta);
		
		return mv;
	}
	
	@RequestMapping("/adicionarImagemAjax")
	public String adicionarImagemAjax(@RequestParam("file") MultipartFile file) {
		
		if(file.isEmpty()) {
			galeria();
			return "redirect:galeria";
		}
		
		String PATH_FILE = Paths.get("").toAbsolutePath().toString() + "\\images\\galeria\\";
		String NOME = file.getOriginalFilename();
		
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(PATH_FILE + NOME);
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        String PATH_GALERIA = "/images/galeria/";
        Imagem imagem = new Imagem();
        imagem.setImagem_name(NOME);
        imagem.setImagem_path(PATH_GALERIA);
        imagem.setImagem_file_path(PATH_FILE);
        imagemService.save(imagem);
        
        return "redirect:galeria";
	}

	@DeleteMapping("/removeImagemAjax/{id}")
	public void removeImagemAjax(@PathVariable("id") int id, HttpServletResponse response) {
		Imagem imagem = imagemService.findById(id).get();
		
		try {
			Path path = Paths.get(imagem.getImagem_file_path() + imagem.getImagem_name());
			Files.delete(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		imagemService.deleteById(id);
		response.setStatus(200);
	}
}
