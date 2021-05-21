package pe.edu.upc.matricula.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pe.edu.upc.matricula.models.entities.Usuario;
import pe.edu.upc.matricula.services.UsuarioService;


@Controller
@RequestMapping("/")
public class IndexController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping
	public String inicio(Model model) {
		Authentication auth = SecurityContextHolder
	            .getContext()
	            .getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		try {
			Optional<Usuario> usuarioLogin = usuarioService.findByUsuario(userDetail.getUsername());
			model.addAttribute("usuarioLogin", usuarioLogin.get());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return "inicio";
	}
	
	@GetMapping("help")
	public String help() {
		return "help";
	}
	
	@GetMapping("layout")
	public String layout() {
		return "layout/layout";
	}
	
	@GetMapping("login")
	public String login() {
		return "index";
	}

}
