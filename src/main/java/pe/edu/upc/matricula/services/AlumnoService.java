package pe.edu.upc.matricula.services;

import java.util.Optional;

import pe.edu.upc.matricula.models.entities.Alumno;
import pe.edu.upc.matricula.models.entities.Usuario;

public interface AlumnoService extends CrudService<Alumno, Integer>{

	Optional<Alumno> findByUsuario(Usuario usuario) throws Exception;
}
