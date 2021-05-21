package pe.edu.upc.matricula.controllers.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.matricula.models.entities.Alumno;
import pe.edu.upc.matricula.models.entities.DetalleMatricula;
import pe.edu.upc.matricula.models.entities.Matricula;
import pe.edu.upc.matricula.models.entities.Usuario;
import pe.edu.upc.matricula.services.AlumnoService;
import pe.edu.upc.matricula.services.DetalleMatriculaService;
import pe.edu.upc.matricula.services.MatriculaService;
import pe.edu.upc.matricula.services.UsuarioService;

@Controller
@RequestMapping("/admin")
public class MatriculasController {
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	AlumnoService alumnoService;
	
	@Autowired
	MatriculaService matriculaService;
	
	@Autowired
	DetalleMatriculaService detalleMatriculaService;

	@GetMapping("matricula")
	public String matricula(Model model, SessionStatus status, RedirectAttributes attributes) {	
		Authentication auth = SecurityContextHolder
	            .getContext()
	            .getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		try {
			Optional<Usuario> usuarioLogin = usuarioService.findByUsuario(userDetail.getUsername());
			List<Alumno> alumnos = alumnoService.findAll();
			model.addAttribute("usuarioLogin", usuarioLogin.get());
			model.addAttribute("alumnos", alumnos);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "/admin/matricula/inicio";
	}
	
	@GetMapping("matricula/seleccionar/{id}")
	public String docenteBuscar(Model model, @RequestParam("id") Integer id, SessionStatus status, RedirectAttributes attributes) {
		Authentication auth = SecurityContextHolder
	            .getContext()
	            .getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		try {
			System.out.println("id:" + id);
			Optional<Usuario> usuarioLogin = usuarioService.findByUsuario(userDetail.getUsername());
			List<Alumno> alumnos = alumnoService.findAll();
			Optional<Alumno> alumnoBuscado = alumnoService.findById(id);
			Optional<Matricula> matricula = matriculaService.findByAlumno(alumnoBuscado.get());
			List<DetalleMatricula> detalleMatriculas = detalleMatriculaService.findByMatricula(matricula.get());
			
			
			model.addAttribute("alumnos", alumnos);
			model.addAttribute("alumnoBuscado", alumnoBuscado.get());
			model.addAttribute("usuarioLogin", usuarioLogin.get());
			model.addAttribute("detalleMatriculas", detalleMatriculas);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "/admin/matricula/seleccion";
	}
}
