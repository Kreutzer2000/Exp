package pe.edu.upc.matricula.models.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.matricula.models.entities.Alumno;
import pe.edu.upc.matricula.models.entities.Matricula;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Integer>{
	
	Optional<Matricula> findByAlumno(Alumno alumno) throws Exception;
}
