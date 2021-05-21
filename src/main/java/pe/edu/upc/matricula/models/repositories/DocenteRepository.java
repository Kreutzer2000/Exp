package pe.edu.upc.matricula.models.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.matricula.models.entities.Docente;
import pe.edu.upc.matricula.models.entities.Usuario;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Integer>{
	
	Optional<Docente> findByUsuario(Usuario usuario) throws Exception;
}