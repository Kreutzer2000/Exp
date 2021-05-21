package pe.edu.upc.matricula.services;

import java.util.List;

import pe.edu.upc.matricula.models.entities.DetalleMatricula;
import pe.edu.upc.matricula.models.entities.Matricula;

public interface DetalleMatriculaService extends CrudService<DetalleMatricula, Integer>{

	List<DetalleMatricula> findByMatricula(Matricula matricula) throws Exception;
}
