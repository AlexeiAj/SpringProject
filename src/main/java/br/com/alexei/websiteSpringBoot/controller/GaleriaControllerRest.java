package br.com.alexei.websiteSpringBoot.controller;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alexei.websiteSpringBoot.model.Imagem;
import br.com.alexei.websiteSpringBoot.service.ImagemService;

@RestController
@RequestMapping("/fotos")
public class GaleriaControllerRest {
	
	@Autowired
	private ImagemService imagemService;
	
	@GetMapping
	public List<Imagem> fotos() {
		return (List<Imagem>) imagemService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Imagem> foto(@PathVariable Integer id) {
		Optional<Imagem> fotoOpt = imagemService.findById(id);
		
		if(fotoOpt.isPresent()) return ResponseEntity.ok(fotoOpt.get());
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Imagem> adicionar(@RequestParam("file") MultipartFile file, UriComponentsBuilder uriBuilder){
		
		if(file.isEmpty()) return ResponseEntity.notFound().build();
	
		String PATH_FILE = Paths.get("").toAbsolutePath().toString() + "\\src\\main\\resources\\public\\images\\galeria\\";
		String NOME = file.getOriginalFilename();
		
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(PATH_FILE + NOME);
            Files.write(path, bytes);
        } catch (IOException e) {
        	return ResponseEntity.notFound().build();
        }
        
        String PATH_GALERIA = "/images/galeria/";
        Imagem imagem = new Imagem();
        imagem.setImagem_name(NOME);
        imagem.setImagem_path(PATH_GALERIA);
        imagem.setImagem_file_path(PATH_FILE);
        imagemService.save(imagem);
		
		URI uri = uriBuilder.path("/fotos/{id}").buildAndExpand(imagem.getId()).toUri();
		
		return ResponseEntity.created(uri).body(imagem);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Integer id){
		Optional<Imagem> fotoOpt = imagemService.findById(id);
	
		if(fotoOpt.isPresent()) {
			
			try {
				Path path = Paths.get(fotoOpt.get().getImagem_file_path() + fotoOpt.get().getImagem_name());
				Files.delete(path);
			} catch (IOException e) {
				return ResponseEntity.notFound().build();
			}
			
			imagemService.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
