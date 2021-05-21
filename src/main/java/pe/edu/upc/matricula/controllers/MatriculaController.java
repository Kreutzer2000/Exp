package pe.edu.upc.matricula.controllers;
											
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.matricula.models.entities.Alumno;
import pe.edu.upc.matricula.models.entities.DetalleMatricula;
import pe.edu.upc.matricula.models.entities.DocenteCurso;
import pe.edu.upc.matricula.models.entities.Matricula;
import pe.edu.upc.matricula.models.entities.Usuario;
import pe.edu.upc.matricula.services.AlumnoService;
import pe.edu.upc.matricula.services.DetalleMatriculaService;
import pe.edu.upc.matricula.services.DocenteCursoService;
import pe.edu.upc.matricula.services.MatriculaService;
import pe.edu.upc.matricula.services.UsuarioService;

@Controller
@RequestMapping("/alumno")
public class MatriculaController {

	@Autowired
	DocenteCursoService docenteCursoService;
	
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
			Optional<Alumno>alumno = alumnoService.findByUsuario(usuarioLogin.get());
			Optional<Matricula> matricula = matriculaService.findByAlumno(alumno.get());
			if (matricula.isPresent()) {
				List<DetalleMatricula> detalleMatriculas = detalleMatriculaService.findByMatricula(matricula.get());
				model.addAttribute("detalleMatriculas", detalleMatriculas);
			}else {
				attributes.addFlashAttribute("mensaje2", "Aun no se ha registrado matricula");
			}	
			model.addAttribute("usuarioLogin", usuarioLogin.get());
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "/alumno/matricula/inicio";
	}
	
	@GetMapping("matricula/crear")
	public String matriculaCrear(Model model, SessionStatus status, RedirectAttributes attributes) {
		
		Authentication auth = SecurityContextHolder
	            .getContext()
	            .getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		Matricula matricula = new Matricula();
		DetalleMatricula detalleMatricula = new DetalleMatricula();
		try {
			boolean btnDisabled = false;
			Optional<Usuario> usuarioLogin = usuarioService.findByUsuario(userDetail.getUsername());
			Optional<Alumno>alumno = alumnoService.findByUsuario(usuarioLogin.get());
			
			if (matriculaService.findByAlumno(alumno.get()).isPresent()) {
				attributes.addFlashAttribute("mensaje", "Ya existe matrícula");
				btnDisabled = true;
				System.out.println("Existe matrícula");
			}
			List<Integer>arregloCursos = new ArrayList<Integer>();
			List<DocenteCurso> docentesCursos = docenteCursoService.findAll(); //Crear en orden alfabético
			model.addAttribute("btnDisabled", btnDisabled);
			model.addAttribute("usuarioLogin", usuarioLogin.get());
			model.addAttribute("arregloCursos", arregloCursos);
			model.addAttribute("matricula", matricula);
			model.addAttribute("detalleMatricula", detalleMatricula);
			model.addAttribute("docentesCursos", docentesCursos);
			status.setComplete();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "/alumno/matricula/crear";
	}
	
	@PostMapping("matricula/save")
	public String saveMatricula(@ModelAttribute("matricula") Matricula matricula,@RequestParam("arregloCursos[]") List<Integer> to, SessionStatus status, RedirectAttributes attributes) {
		
		Authentication auth = SecurityContextHolder
	            .getContext()
	            .getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		
		try {
			//Alumno
			Optional<Usuario> usuarioLogin = usuarioService.findByUsuario(userDetail.getUsername());
			Optional<Alumno>alumno = alumnoService.findByUsuario(usuarioLogin.get());
			
			//Matricula
			matricula.setAlumno(alumno.get());
			matriculaService.save(matricula);
			
			//Save matricula en alumno
			Optional<Matricula>matriculaAlumno = matriculaService.findByAlumno(alumno.get());
			Alumno getAlumno = alumno.get();
			getAlumno.setMatricula(matriculaAlumno.get());
			getAlumno.setUsuario(getAlumno.getUsuario());
			alumnoService.save(getAlumno);

			for (int i = 0; i < to.size(); i++) {
				DetalleMatricula detalleMatricula = new DetalleMatricula();
				detalleMatricula.setMatricula(matriculaAlumno.get());
				Optional<DocenteCurso> docenteCurso = docenteCursoService.findById(to.get(i)); 
				detalleMatricula.setDocenteCurso(docenteCurso.get());
				detalleMatriculaService.save(detalleMatricula);
				System.out.println("i: "+ i +"to: "+ to.get(i) +" curso: " + detalleMatricula.getDocenteCurso().getCurso().getNombre() + " - id:"+ detalleMatricula.getDocenteCurso().getId());
				status.setComplete();
				attributes.addFlashAttribute("mensaje", "Matricula creada");
				if (i == to.size()) {
					attributes.addFlashAttribute("mensaje", "Matricula creada");
					status.setComplete();
					return "redirect:/alumno/matricula";
				}
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "redirect:/alumno/matricula";
	}
	
	
	@GetMapping("matricula/seleccionar/{id}")
	public String docenteBuscar(Model model, @RequestParam("id") Integer id, SessionStatus status, RedirectAttributes attributes) {
		try {
			System.out.println("id:" + id);
			
			Optional<Alumno> alumnoM = alumnoService.findById(id);
			Optional<Matricula> matricula = matriculaService.findByAlumno(alumnoM.get());
			List<DetalleMatricula> detalleMatriculas = detalleMatriculaService.findByMatricula(matricula.get());
			
			List<Alumno> alumnos = alumnoService.findAll();
			model.addAttribute("alumnos", alumnos);
			model.addAttribute("alumnoM", alumnoM);
			model.addAttribute("detalleMatriculas", detalleMatriculas);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "/alumno/matricula/seleccion";
	}
}
