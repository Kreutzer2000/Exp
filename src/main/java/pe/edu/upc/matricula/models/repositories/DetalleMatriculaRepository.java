package pe.edu.upc.matricula.models.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.matricula.models.entities.DetalleMatricula;
import pe.edu.upc.matricula.models.entities.Matricula;

@Repository
public interface DetalleMatriculaRepository extends JpaRepository<DetalleMatricula, Integer>{
	
	List<DetalleMatricula> findByMatricula(Matricula matricula) throws Exception;
}
