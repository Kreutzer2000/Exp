package pe.edu.upc.matricula.controllers.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.matricula.models.entities.Alumno;
import pe.edu.upc.matricula.models.entities.Docente;
import pe.edu.upc.matricula.models.entities.Usuario;
import pe.edu.upc.matricula.services.AlumnoService;
import pe.edu.upc.matricula.services.DocenteService;
import pe.edu.upc.matricula.services.UsuarioService;

@Controller
@RequestMapping("/admin")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private DocenteService docenteService;
	
	@Autowired
	private AlumnoService alumnoService;

	@GetMapping("docente")
	public String docente(Model model) {
		Authentication auth = SecurityContextHolder
	            .getContext()
	            .getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		try {
			List<Docente> docentes = docenteService.findAll();
			Optional<Usuario> usuarioLogin = usuarioService.findByUsuario(userDetail.getUsername());
			model.addAttribute("usuarioLogin", usuarioLogin.get());
			model.addAttribute("docentes", docentes);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "/admin/docente/inicio";
	}
	
	@GetMapping("docente/crear")
	public String docenteCrear(Model model) {
		Authentication auth = SecurityContextHolder
	            .getContext()
	            .getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		Usuario usuario = new Usuario();
		try {
			Optional<Usuario> usuarioLogin = usuarioService.findByUsuario(userDetail.getUsername());
			model.addAttribute("usuarioLogin", usuarioLogin.get());
			model.addAttribute("usuario", usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "/admin/docente/crear";
	}
	
	@PostMapping("docente/save")
	public String saveDocente(@ModelAttribute("usuario") Usuario usuario, SessionStatus status, RedirectAttributes attributes) {
		try {	
			if (usuarioService.findByNumeroDocumento(usuario.getNumeroDocumento()).isPresent()) {
				System.out.println("El número de documento ya se encuentra registrado");
				attributes.addFlashAttribute("mensaje", "El número de documento ya se encuentra registrado");
				return "redirect:/admin/docente/crear";
			}else if (usuario.getNumeroDocumento().isBlank()) {
				attributes.addFlashAttribute("mensaje", "Complete el número de documento, por favor");
				return "redirect:/admin/docente/crear";
			} else if (usuario.getNumeroDocumento().toString().length() < 8 && usuario.getNumeroDocumento().toString().length() > 12) {
				attributes.addFlashAttribute("mensaje", "El número de documento debe de contener entre 8 a 12 caracteres");
				return "redirect:/admin/docente/crear";
			}else if (! usuario.getNumeroDocumento().toString().matches("[0-9]*")) {
				attributes.addFlashAttribute("mensaje", "Los valores del número de documento deben de ser numéricos");
				return "redirect:/admin/docente/crear";		
			}else if (usuario.getNumeroDocumento().isEmpty() || usuario.getNombres().isEmpty() || usuario.getApellidoPaterno().isEmpty() || usuario.getApellidoMaterno().isEmpty()) {
				attributes.addFlashAttribute("mensaje", "Complete todos los campos, por favor");
				return "redirect:/admin/docente/crear";
			}else if (usuario.getNombres().isBlank() ||  usuario.getNombres().length() <2) {
				attributes.addFlashAttribute("mensaje", "Complete el nombre con mínimo 2 caracteres");
				return "redirect:/admin/docente/crear";
			}else if (! usuario.getApellidoPaterno().toString().matches("[áéíóúÁÉÍÓÚa-zA-Zñ\s]*")) {
				attributes.addFlashAttribute("mensaje", "Los valores en el Apellido paterno deben de ser alfabéticos");
				return "redirect:/admin/docente/crear";
			}else if (! usuario.getApellidoMaterno().toString().matches("[áéíóúÁÉÍÓÚa-zA-Zñ\s]*")) {
				attributes.addFlashAttribute("mensaje", "Los valores en el Apellido materno deben de ser alfabéticos");
				return "redirect:/admin/docente/crear";
			}else if (! usuario.getNombres().toString().matches("[áéíóúÁÉÍÓÚa-zA-Zñ\s]*")) {
				attributes.addFlashAttribute("mensaje", "Los valores en el Nombre deben de ser alfabéticos");
				return "redirect:/admin/docente/crear";
			}else if (usuario.getApellidoPaterno().isBlank() || usuario.getApellidoPaterno().length() <2) {
				attributes.addFlashAttribute("mensaje", "Complete el apellido paterno con mínimo 2 caracteres");
				return "redirect:/admin/docente/crear";
			}else if (usuario.getApellidoMaterno().isBlank() || usuario.getApellidoMaterno().length() <2) {
				attributes.addFlashAttribute("mensaje", "Complete el apellido materno con mínimo 2 caracteres");
				return "redirect:/admin/docente/crear";
			}else{
				//Crear usuario
				String LNombre = usuario.getNombres().substring(0, 1).toLowerCase();
				String LApellidoM = usuario.getApellidoMaterno().substring(0, 1).toLowerCase();
				String LApellidoP = usuario.getApellidoPaterno().replaceAll("\\s","");
				String nUsuario = LNombre + LApellidoP.toLowerCase() + LApellidoM;
				usuario.setUsuario(nUsuario);
				
				//Contraseña
				BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
				String password = bcpe.encode(usuario.getNumeroDocumento().toString());
				usuario.setContraseña(password);
				
				//Rol
				usuario.setRol("docente");
				
				//Estado
				usuario.setEstado(true);
				
				//Save usuario
				usuarioService.save(usuario);
				
				//Save Docente
				Integer IDUsuario = usuarioService.findAllOrderByIdDesc().get(0).getId();
				Optional<Usuario> GetUsuario = usuarioService.findById(IDUsuario);
				Docente docente = new Docente();
				docente.setUsuario(GetUsuario.get());
				docenteService.save(docente);
				
				attributes.addFlashAttribute("mensaje", "Se registró exitosamente al docente");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "redirect:/admin/docente";
	}
	
	@GetMapping("docente/{id}")
	public String docenteShow(@PathVariable("id") Integer id, Model model) {
		Authentication auth = SecurityContextHolder
	            .getContext()
	            .getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		try {
			Optional<Usuario> usuarioLogin = usuarioService.findByUsuario(userDetail.getUsername());
			Optional<Docente> docente = docenteService.findById(id);
			Optional<Usuario> usuario = usuarioService.findById(docente.get().getUsuario().getId());
			
			model.addAttribute("usuarioLogin", usuarioLogin.get());
			if (usuario.isPresent()) {
				model.addAttribute("usuario", usuario.get());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "/admin/docente/editar";
	}
	
	@PostMapping("docente/update/{id}")
	public String docenteUpdate(@PathVariable("id") Integer id, @ModelAttribute("usuario") Usuario usuario, Model model, SessionStatus status, RedirectAttributes attributes) {
		try {
			if (usuario.getNumeroDocumento().isBlank()) {
				attributes.addFlashAttribute("mensaje", "Complete el número de documento, por favor");
				return "redirect:/admin/docente/"+id;
			} else if (usuario.getNumeroDocumento().toString().length() < 8 && usuario.getNumeroDocumento().toString().length() > 12) {
				attributes.addFlashAttribute("mensaje", "El número de documento debe de contener entre 8 a 12 caracteres");
				return "redirect:/admin/docente/"+id;
			}else if (! usuario.getNumeroDocumento().toString().matches("[0-9]*")) {
				attributes.addFlashAttribute("mensaje", "Los valores del número de documento deben de ser numéricos");
				return "redirect:/admin/docente/"+id;	
			}else if (usuario.getNumeroDocumento().isEmpty() || usuario.getNombres().isEmpty() || usuario.getApellidoPaterno().isEmpty() || usuario.getApellidoMaterno().isEmpty()) {
				attributes.addFlashAttribute("mensaje", "Complete todos los campos, por favor");
				return "redirect:/admin/docente/"+id;
			}else if (usuario.getNombres().isBlank() ||  usuario.getNombres().length() <2) {
				attributes.addFlashAttribute("mensaje", "Complete el nombre con mínimo 2 caracteres");
				return "redirect:/admin/docente/"+id;
			}else if (! usuario.getApellidoPaterno().toString().matches("[áéíóúÁÉÍÓÚa-zA-Zñ\s]*")) {
				attributes.addFlashAttribute("mensaje", "Los valores en el Apellido paterno deben de ser alfabéticos");
				return "redirect:/admin/docente/"+id;
			}else if (! usuario.getApellidoMaterno().toString().matches("[áéíóúÁÉÍÓÚa-zA-Zñ\s]*")) {
				attributes.addFlashAttribute("mensaje", "Los valores en el Apellido materno deben de ser alfabéticos");
				return "redirect:/admin/docente/"+id;
			}else if (! usuario.getNombres().toString().matches("[áéíóúÁÉÍÓÚa-zA-Zñ\s]*")) {
				attributes.addFlashAttribute("mensaje", "Los valores en el Nombre deben de ser alfabéticos");
				return "redirect:/admin/docente/"+id;
			}else if (usuario.getApellidoPaterno().isBlank() || usuario.getApellidoPaterno().length() <2) {
				attributes.addFlashAttribute("mensaje", "Complete el apellido paterno con mínimo 2 caracteres");
				return "redirect:/admin/docente/"+id;
			}else if (usuario.getApellidoMaterno().isBlank() || usuario.getApellidoMaterno().length() <2) {
				attributes.addFlashAttribute("mensaje", "Complete el apellido materno con mínimo 2 caracteres");
				return "redirect:/admin/docente/"+id;
			}else {
				//Crear usuario
				String LNombre = usuario.getNombres().substring(0, 1).toLowerCase();
				String LApellidoP = usuario.getApellidoPaterno().replaceAll("\\s","");
				String LApellidoM = usuario.getApellidoMaterno().substring(0, 1).toLowerCase();
				String nUsuario = LNombre + LApellidoP.toLowerCase() + LApellidoM;
				usuario.setUsuario(nUsuario);
				
				//Contraseña
				BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
				String password = bcpe.encode(usuario.getNumeroDocumento().toString());
				usuario.setContraseña(password);
				
				//Rol
				usuario.setRol("docente");
				
				//Save usuario
				usuarioService.update(usuario);
				status.setComplete();
				System.out.println("Actualizó");
				attributes.addFlashAttribute("mensaje", "Se actualizó correctamente");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "redirect:/admin/docente";
	}
	
	@GetMapping("alumno")
	public String alumno(Model model) {
		Authentication auth = SecurityContextHolder
	            .getContext()
	            .getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		try {
			List<Alumno> alumnos = alumnoService.findAll();
			Optional<Usuario> usuarioLogin = usuarioService.findByUsuario(userDetail.getUsername());
			model.addAttribute("usuarioLogin", usuarioLogin.get());
			model.addAttribute("alumnos", alumnos);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "/admin/alumno/inicio";
	}
	
	@GetMapping("alumno/crear")
	public String alumnoCrear(Model model) {
		Authentication auth = SecurityContextHolder
	            .getContext()
	            .getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		Usuario usuario = new Usuario();
		try {
			Optional<Usuario> usuarioLogin = usuarioService.findByUsuario(userDetail.getUsername());
			model.addAttribute("usuarioLogin", usuarioLogin.get());
			model.addAttribute("usuario", usuario);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "/admin/alumno/crear";
	}
	
	@PostMapping("alumno/save")
	public String saveAlumno(@ModelAttribute("usuario") Usuario usuario, SessionStatus status, RedirectAttributes attributes) {
		try {
			if (usuarioService.findByNumeroDocumento(usuario.getNumeroDocumento()).isPresent()) {
				System.out.println("El número de documento ya se encuentra registrado");
				attributes.addFlashAttribute("mensaje", "El número de documento ya se encuentra registrado");
				return "redirect:/admin/alumno/crear";
			}else if (usuario.getNumeroDocumento().isBlank()) {
				attributes.addFlashAttribute("mensaje", "Complete el número de documento, por favor");
				return "redirect:/admin/alumno/crear";
			} else if (usuario.getNumeroDocumento().toString().length() < 8 && usuario.getNumeroDocumento().toString().length() > 12) {
				attributes.addFlashAttribute("mensaje", "El número de documento debe de contener entre 8 a 12 caracteres");
				return "redirect:/admin/alumno/crear";
			}else if (! usuario.getNumeroDocumento().toString().matches("[0-9]*")) {
				attributes.addFlashAttribute("mensaje", "Los valores del número de documento deben de ser numéricos");
				return "redirect:/admin/alumno/crear";		
			}else if (usuario.getNumeroDocumento().isEmpty() || usuario.getNombres().isEmpty() || usuario.getApellidoPaterno().isEmpty() || usuario.getApellidoMaterno().isEmpty()) {
				attributes.addFlashAttribute("mensaje", "Complete todos los campos, por favor");
				return "redirect:/admin/alumno/crear";
			}else if (usuario.getNombres().isBlank() ||  usuario.getNombres().length() <2) {
				attributes.addFlashAttribute("mensaje", "Complete el nombre con mínimo 2 caracteres");
				return "redirect:/admin/alumno/crear";
			}else if (! usuario.getApellidoPaterno().toString().matches("[áéíóúÁÉÍÓÚa-zA-Zñ\s]*")) {
				attributes.addFlashAttribute("mensaje", "Los valores en el Apellido paterno deben de ser alfabéticos");
				return "redirect:/admin/alumno/crear";
			}else if (! usuario.getApellidoMaterno().toString().matches("[áéíóúÁÉÍÓÚa-zA-Zñ\s]*")) {
				attributes.addFlashAttribute("mensaje", "Los valores en el Apellido materno deben de ser alfabéticos");
				return "redirect:/admin/alumno/crear";
			}else if (! usuario.getNombres().toString().matches("[áéíóúÁÉÍÓÚa-zA-Zñ\s]*")) {
				attributes.addFlashAttribute("mensaje", "Los valores en el Nombre deben de ser alfabéticos");
				return "redirect:/admin/docente/crear";
			}else if (usuario.getApellidoPaterno().isBlank() || usuario.getApellidoPaterno().length() <2) {
				attributes.addFlashAttribute("mensaje", "Complete el apellido paterno con mínimo 2 caracteres");
				return "redirect:/admin/alumno/crear";
			}else if (usuario.getApellidoMaterno().isBlank() || usuario.getApellidoMaterno().length() <2) {
				attributes.addFlashAttribute("mensaje", "Complete el apellido materno con mínimo 2 caracteres");
				return "redirect:/admin/alumno/crear";
			}else{
				//Usuario
				String año = "2017";
				String periodo = "1";
				Random r = new Random();
				Integer num1 = r.nextInt(10);
				Integer num2 = r.nextInt(10);
				Integer num3 = r.nextInt(10);
				Integer num4 = r.nextInt(10);
				String nAleatorio= 	String.valueOf(num1) + String.valueOf(num2) + String.valueOf(num3) + String.valueOf(num4);
				String nUsuario = "u" + año + periodo + nAleatorio;
				usuario.setUsuario(nUsuario);
				System.out.println(nUsuario);
				
				//Contraseña
				BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
				String password = bcpe.encode(usuario.getNumeroDocumento().toString());
				usuario.setContraseña(password);
				
				//Rol				
				usuario.setRol("alumno");
				
				//Estado
				usuario.setEstado(true);
				
				//Authority
				usuario.addAuthority("ROLE_ALUMNO");
				
				//Save
				usuarioService.save(usuario);
				
				//Save Usuario
				Integer IDUsuario = usuarioService.findAllOrderByIdDesc().get(0).getId();
				Optional<Usuario> GetUsuario = usuarioService.findById(IDUsuario);
				Alumno alumno = new Alumno();
				alumno.setUsuario(GetUsuario.get());
				alumnoService.save(alumno);
				
				attributes.addFlashAttribute("mensaje", "Se registró exitosamente al alumno");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "redirect:/admin/alumno";
	}
	
	@GetMapping("alumno/{id}")
	public String alumnoShow(@PathVariable("id") Integer id, Model model) {
		Authentication auth = SecurityContextHolder
	            .getContext()
	            .getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		try {
			Optional<Usuario> usuarioLogin = usuarioService.findByUsuario(userDetail.getUsername());
			Optional<Alumno> alumno = alumnoService.findById(id);
			Optional<Usuario> usuario = usuarioService.findById(alumno.get().getUsuario().getId());
			
			model.addAttribute("usuarioLogin", usuarioLogin.get());
			if (usuario.isPresent()) {
				model.addAttribute("usuario", usuario.get());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "/admin/alumno/editar";
	}
	
	@PostMapping("alumno/update/{id}")
	public String alumnoUpdate(@PathVariable("id") Integer id, @ModelAttribute("alumno") Usuario usuario, Model model, SessionStatus status, RedirectAttributes attributes) {
		try {
			if (usuario.getNumeroDocumento().isBlank()) {
				attributes.addFlashAttribute("mensaje", "Complete el número de documento, por favor");
				return "redirect:/admin/alumno/"+id;
			} else if (usuario.getNumeroDocumento().toString().length() < 8) {
				attributes.addFlashAttribute("mensaje", "El número de documento debe de contener entre 8 a 12 caracteres");
				return "redirect:/admin/alumno/"+id;
			}else if (usuario.getNumeroDocumento().toString().length() > 12) {
				attributes.addFlashAttribute("mensaje", "El número de documento debe de contener entre 8 a 12 caracteres");
				return "redirect:/admin/alumno/"+id;
			}else if (! usuario.getNumeroDocumento().toString().matches("[0-9]*")) {
				attributes.addFlashAttribute("mensaje", "Los valores del número de documento deben de ser numéricos");
				return "redirect:/admin/alumno/"+id;	
			}else if (usuario.getNumeroDocumento().isEmpty() || usuario.getNombres().isEmpty() || usuario.getApellidoPaterno().isEmpty() || usuario.getApellidoMaterno().isEmpty()) {
				attributes.addFlashAttribute("mensaje", "Complete todos los campos, por favor");
				return "redirect:/admin/alumno/"+id;
			}else if (usuario.getNombres().isBlank() ||  usuario.getNombres().length() <2) {
				attributes.addFlashAttribute("mensaje", "Complete el nombre con mínimo 2 caracteres");
				return "redirect:/admin/alumno/"+id;
			}else if (! usuario.getApellidoPaterno().toString().matches("[áéíóúÁÉÍÓÚa-zA-Zñ\s]*")) {
				attributes.addFlashAttribute("mensaje", "Los valores en el Apellido paterno deben de ser alfabéticos");
				return "redirect:/admin/alumno/"+id;
			}else if (! usuario.getApellidoMaterno().toString().matches("[áéíóúÁÉÍÓÚa-zA-Zñ\s]*")) {
				attributes.addFlashAttribute("mensaje", "Los valores en el Apellido materno deben de ser alfabéticos");
				return "redirect:/admin/alumno/"+id;
			}else if (! usuario.getNombres().toString().matches("[áéíóúÁÉÍÓÚa-zA-Zñ\s]*")) {
				attributes.addFlashAttribute("mensaje", "Los valores en el Nombre deben de ser alfabéticos");
				return "redirect:/admin/alumno/"+id;
			}else if (usuario.getApellidoPaterno().isBlank() || usuario.getApellidoPaterno().length() <2) {
				attributes.addFlashAttribute("mensaje", "Complete el apellido paterno con mínimo 2 caracteres");
				return "redirect:/admin/alumno/"+id;
			}else if (usuario.getApellidoMaterno().isBlank() || usuario.getApellidoMaterno().length() <2) {
				attributes.addFlashAttribute("mensaje", "Complete el apellido materno con mínimo 2 caracteres");
				return "redirect:/admin/alumno/"+id;
			}else {
				//Usuario
				Optional<Usuario> usuarioAlumno = usuarioService.findById(id);
				usuario.setUsuario(usuarioAlumno.get().getUsuario());
				
				//Contraseña
				BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
				String password = bcpe.encode(usuario.getNumeroDocumento().toString());
				usuario.setContraseña(password);
				
				//Rol
				usuario.setRol("alumno");
				
				//Alumno Save
				usuarioService.update(usuario);
				
				status.setComplete();
				attributes.addFlashAttribute("mensaje", "Se actualizó correctamente");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "redirect:/admin/alumno";
	}
	
	
	@GetMapping("alumno/buscar/{codigo}")
	public String alumnoBuscar(Model model, @RequestParam("codigo") String codigo, SessionStatus status, RedirectAttributes attributes) {
		Authentication auth = SecurityContextHolder
	            .getContext()
	            .getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		try {
			Optional<Usuario> usuarioLogin = usuarioService.findByUsuario(userDetail.getUsername());
			System.out.println(codigo);
			List<Usuario> usuarioAlumnos = usuarioService.findByRolAndUsuarioContaining("alumno", codigo);
			if (codigo.toString().length() <3) {
				attributes.addFlashAttribute("mensajeError", "Complete el campo de búsqueda con mínimo 3 caracteres");
				System.out.println("1");
				return "redirect:/admin/alumno";
			}else if (codigo.isEmpty()) {
				attributes.addFlashAttribute("mensajeError", "Complete el campo si desea buscar, por favor");
				System.out.println("2");
				return "redirect:/admin/alumno";
			} else if(codigo.toString().isBlank()){
				attributes.addFlashAttribute("mensajeError", "Complete el campo si desea buscar, por favor");
				System.out.println("3");
				return "redirect:/admin/alumno";
			}else if (usuarioAlumnos.isEmpty()) {
				System.out.println("no encontró nada");
				attributes.addFlashAttribute("mensajeError", "No se encontraron registros en la búsqueda");
				return "redirect:/admin/alumno";
			}else {
				System.out.println("si encontró");
				List<Alumno> AlumnosBusqueda = new ArrayList<Alumno>();
				for (int i = 0; i < usuarioAlumnos.size(); i++) {
					System.out.println(usuarioAlumnos.size());
					System.out.println(usuarioAlumnos.get(i).getApellidoPaterno());
					Optional<Alumno> alumnoEncontrado = alumnoService.findByUsuario(usuarioAlumnos.get(i));
					AlumnosBusqueda.add(i, alumnoEncontrado.get());
				}
				model.addAttribute("AlumnosBusqueda", AlumnosBusqueda);
				model.addAttribute("usuarioLogin", usuarioLogin.get());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "/admin/alumno/busqueda";
	}
	
	@GetMapping("docente/buscar/{codigo}")
	public String docenteBuscar(Model model, @RequestParam("codigo") String codigo, SessionStatus status, RedirectAttributes attributes) {
		Authentication auth = SecurityContextHolder
	            .getContext()
	            .getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		try {
			Optional<Usuario> usuarioLogin = usuarioService.findByUsuario(userDetail.getUsername());
			System.out.println(codigo);
			List<Usuario> usuarioDocentes = usuarioService.findByRolAndUsuarioContaining("docente", codigo);
			if (codigo.toString().length() <3) {
				attributes.addFlashAttribute("mensajeError", "Complete el campo de búsqueda con mínimo 3 caracteres");
				System.out.println("1");
				return "redirect:/admin/docente";
			}else if (codigo.isEmpty()) {
				attributes.addFlashAttribute("mensajeError", "Complete el campo si desea buscar, por favor");
				System.out.println("2");
				return "redirect:/admin/docente";
			} else if(codigo.toString().isBlank()){
				attributes.addFlashAttribute("mensajeError", "Complete el campo si desea buscar, por favor");
				System.out.println("3");
				return "redirect:/admin/docente";
			}else if (usuarioDocentes.isEmpty()) {
				System.out.println("no encontró nada");
				attributes.addFlashAttribute("mensajeError", "No se encontraron registros en la búsqueda");
				return "redirect:/admin/docente";
			}else {
				System.out.println("si encontró");
				List<Docente> DocentesBusqueda = new ArrayList<Docente>();
				for (int i = 0; i < usuarioDocentes.size(); i++) {
					System.out.println(usuarioDocentes.size());
					System.out.println(usuarioDocentes.get(i).getApellidoPaterno());
					Optional<Docente> docenteEncontrado = docenteService.findByUsuario(usuarioDocentes.get(i));
					DocentesBusqueda.add(i, docenteEncontrado.get());
				}
				model.addAttribute("usuarioLogin", usuarioLogin.get());
				model.addAttribute("DocentesBusqueda", DocentesBusqueda);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return "/admin/docente/busqueda";
	}
}
