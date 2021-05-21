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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.matricula.models.entities.Curso;
import pe.edu.upc.matricula.models.entities.Docente;
import pe.edu.upc.matricula.models.entities.DocenteCurso;
import pe.edu.upc.matricula.models.entities.Usuario;
import pe.edu.upc.matricula.services.CursoService;
import pe.edu.upc.matricula.services.DocenteCursoService;
import pe.edu.upc.matricula.services.DocenteService;
import pe.edu.upc.matricula.services.UsuarioService;

@Controller
@RequestMapping("/admin")
public class DocenteCursoController {

	@Autowired
	private DocenteService docenteService;
	
	@Autowired
	private CursoService cursoService;
	
	@Autowired
	private DocenteCursoService docenteCursoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("docente-curso")
	public String docenteCurso(Model model) {
		Authentication auth = SecurityContextHolder
	            .getContext()
	            .getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		try {
			List<DocenteCurso> docenteCursos = docenteCursoService.findAll();
			Optional<Usuario> usuarioLogin = usuarioService.findByUsuario(userDetail.getUsername());
			model.addAttribute("usuarioLogin", usuarioLogin.get());
			model.addAttribute("docenteCursos",docenteCursos);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "/admin/docente-curso/inicio";
	}
	
	@GetMapping("docente-curso/crear")
	public String docenteCursoCrear(Model model) {
		Authentication auth = SecurityContextHolder
	            .getContext()
	            .getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		DocenteCurso docenteCurso = new DocenteCurso();
		try {
			Optional<Usuario> usuarioLogin = usuarioService.findByUsuario(userDetail.getUsername());
			List<Docente>docentes = docenteService.findAll();
			List<Curso>cursos = cursoService.findAll();
			model.addAttribute("usuarioLogin", usuarioLogin.get());
			model.addAttribute("docentes", docentes);
			model.addAttribute("cursos", cursos);
			model.addAttribute("docenteCurso", docenteCurso);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "/admin/docente-curso/crear";
	}
	
	@PostMapping("docente-curso/save")
	public String docenteCursoSave(@ModelAttribute("docenteCurso") DocenteCurso docenteCurso, SessionStatus status, RedirectAttributes attributes) {
		try {
			docenteCursoService.save(docenteCurso);
			status.setComplete();
			attributes.addFlashAttribute("mensaje", "Docente-Curso creado correctamente");	
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "redirect:/admin/docente-curso";
	}
	
	@GetMapping("docente-curso/{id}")
	public String docenteCursoShow(@PathVariable("id") Integer id, Model model) {
		try {
			Optional<DocenteCurso> optional = docenteCursoService.findById(id);
			if (optional.isPresent()) {
				model.addAttribute("docenteCurso", optional.get());
			}
			
			List<Docente>docentes = docenteService.findAll();
			List<Curso>cursos = cursoService.findAllByEstado(true);
			model.addAttribute("docentes", docentes);
			model.addAttribute("cursos", cursos);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "/admin/curso/editar";
	}
	
	@PostMapping("docente-curso/update/{id}")
	public String cursoUpdate(@PathVariable("id") Integer id, @ModelAttribute("docenteCurso") DocenteCurso docenteCurso, Model model, SessionStatus status, RedirectAttributes attributes) {
		try {
			try {
				docenteCursoService.save(docenteCurso);
				status.setComplete();
				attributes.addFlashAttribute("mensaje", "Docente/Curso se actualizó correctamente");	
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
			return "redirect:/admin/docente-curso";
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "redirect:/admin/curso";
	}
}
