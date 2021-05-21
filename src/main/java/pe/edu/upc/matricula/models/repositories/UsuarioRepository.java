package pe.edu.upc.matricula.models.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.matricula.models.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	
	Optional<Usuario>findByNumeroDocumento(String numeroDocumento) throws Exception;
	
	Optional<Usuario>findByUsuario(String usuario) throws Exception;
	
	List<Usuario> findByRolOrderByApellidoPaternoAsc(String rol) throws Exception;
	
	List<Usuario> findAllByEstado(Boolean estado) throws Exception;
	
	List<Usuario> findAllByEstadoAndRol(Boolean estado, String Rol) throws Exception;
	
	List<Usuario> findByRolAndUsuarioContaining(String rol, String usuario) throws Exception;
	
	List<Usuario> findByUsuarioContaining(String usuario) throws Exception;
	
}
