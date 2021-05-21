package pe.edu.upc.matricula.services;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.matricula.models.entities.Usuario;

public interface UsuarioService extends CrudService<Usuario, Integer> {

	Optional<Usuario> findByNumeroDocumento(String numeroDocumento) throws Exception;

	Optional<Usuario> findByUsuario(String usuario) throws Exception;
	
	List<Usuario>findAllOrderByIdDesc() throws Exception;
	
	List<Usuario> findByRol(String rol) throws Exception;
	
	List<Usuario> findAllByEstado(Boolean estado) throws Exception;
	
	List<Usuario> findAllByEstadoAndRol(Boolean estado, String rol) throws Exception;
	
	List<Usuario> findByRolAndUsuarioContaining(String rol, String usuario) throws Exception;
	
	List<Usuario> findByUsuarioContaining(String usuario) throws Exception;
}
