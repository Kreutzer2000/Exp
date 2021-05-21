package pe.edu.upc.matricula.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pe.edu.upc.matricula.models.entities.Usuario;
import pe.edu.upc.matricula.models.repositories.UsuarioRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
		try {
			Optional<Usuario> optional = this.usuarioRepository.findByUsuario(usuario);
			if (optional.isPresent()) {
				UsuarioDetails usuarioDetails = new UsuarioDetails(optional.get());
				return usuarioDetails;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new UsernameNotFoundException("El usuario ingresado no existe");
	}
	
}
