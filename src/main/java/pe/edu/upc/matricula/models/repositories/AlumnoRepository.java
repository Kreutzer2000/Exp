package pe.edu.upc.matricula.models.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.matricula.models.entities.Alumno;
import pe.edu.upc.matricula.models.entities.Usuario;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Integer>{

	Optional<Alumno> findByUsuario(Usuario usuario) throws Exception;
}
