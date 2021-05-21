package pe.edu.upc.matricula.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import pe.edu.upc.matricula.models.entities.Usuario;
import pe.edu.upc.matricula.models.repositories.AuthorityRepository;
import pe.edu.upc.matricula.models.repositories.UsuarioRepository;

@Service
public class AddUserDB implements CommandLineRunner {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Override
	public void run (String... args) throws Exception{
		//SOLO SE DEBE  DE ACTIVAR UNA SOLA VEZ AL INICIAR EL PROGRAMA.
		
		/*
		BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
		String password = bcpe.encode("admin");
		
		Usuario administrador = new Usuario();
		administrador.setNombres("Ruben");
		administrador.setApellidoPaterno("Cerda");
		administrador.setApellidoMaterno("García");
		administrador.setUsuario("admin");
		administrador.setContraseña(password);
		administrador.setNumeroDocumento("00000001");
		administrador.setRol("ADMINISTRADOR");
		administrador.setEstado(true);
		
		administrador.addAuthority("ROLE_ADMINISTRADOR");
		
		usuarioRepository.save(administrador); 
		
		*/
		
	}
}
