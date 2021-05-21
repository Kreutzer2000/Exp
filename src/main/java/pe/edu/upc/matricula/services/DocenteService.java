package pe.edu.upc.matricula.services;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.matricula.models.entities.Docente;
import pe.edu.upc.matricula.models.entities.Usuario;

public interface DocenteService extends CrudService<Docente, Integer>{

	List<Docente>FindAllOrderByApellidoPaterno() throws Exception;
	
	Optional<Docente> findByUsuario(Usuario usuario) throws Exception;
}
