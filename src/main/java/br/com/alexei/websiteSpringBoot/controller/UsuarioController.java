package br.com.alexei.websiteSpringBoot.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.alexei.websiteSpringBoot.model.Usuario;
import br.com.alexei.websiteSpringBoot.service.UsuarioService;

@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;

	@RequestMapping("/cadastroUsuarios")
	public ModelAndView cadastroUsuarios() {
		ModelAndView mv = new ModelAndView("cadastroUsuarios");
		mv.addObject("usuarios", usuarioService.findAll());
		mv.addObject("usuario", new Usuario());
		return mv;
	}
	
	@PostMapping("/carregarUsuariosAjax")
	public ModelAndView carregarUsuariosAjax() {
		ModelAndView mv = new ModelAndView("cadastroUsuarios :: fragment-usuarios");
		mv.addObject("usuarios", usuarioService.findAll());
		mv.addObject("usuario", new Usuario());
		return mv;
	}
	
	@PostMapping("/carregarUsuarioAjax/{id}")
	public ModelAndView carregarUsuarioAlterarAjax(@PathVariable("id") int id) {
		ModelAndView mv = new ModelAndView("cadastroUsuarios :: fragment-form");
		mv.addObject("usuario", usuarioService.findById(id).get());
		return mv;
	}
	
	@PostMapping("/adicionarUsuarioAjax")
	public String adicionarUsuarioAjax(@ModelAttribute("usuario") @Valid Usuario usuario, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) return "cadastroUsuarios :: fragment-form";
		
		usuarioService.save(usuario);
		return "cadastroUsuarios";
	}
	
	@PutMapping("/alterarUsuarioAjax")
	public String alterarUsuarioAjax(@ModelAttribute("usuario") @Valid Usuario usuario, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) return "cadastroUsuarios :: fragment-form";
		
		usuarioService.edit(usuario);
		return "cadastroUsuarios";
	}
	
	@DeleteMapping("/removerUsuarioAjax/{id}")
	public void removerUsuarioAjax(@PathVariable("id") int id, HttpServletResponse response) {
		usuarioService.deleteById(id);
		response.setStatus(200);
	}
	
	@RequestMapping("/login")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("login");
		mv.addObject("usuario", new Usuario());
		return mv;
	}
	
	@RequestMapping("/logar")
    public String logar(@ModelAttribute("usuario") @Valid Usuario usuario, BindingResult bindingResult, HttpSession session, Model model) {
        if (bindingResult.hasErrors()) return "login";
        if(!usuarioService.exist(usuario)) {
        	model.addAttribute("message", "Usuario Não Existente");
        	return "login";
        }

        session.setAttribute("usuarioLogado", usuario);
        return "redirect:website";
    }
}
