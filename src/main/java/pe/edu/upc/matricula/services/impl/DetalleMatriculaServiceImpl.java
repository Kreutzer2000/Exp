package pe.edu.upc.matricula.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.matricula.models.entities.DetalleMatricula;
import pe.edu.upc.matricula.models.entities.Matricula;
import pe.edu.upc.matricula.models.repositories.DetalleMatriculaRepository;
import pe.edu.upc.matricula.services.DetalleMatriculaService;

@Service
public class DetalleMatriculaServiceImpl implements DetalleMatriculaService, Serializable{

	private static final long serialVersionUID = 1L;

	@Autowired
	private DetalleMatriculaRepository detalleMatriculaRepository;
	
	@Transactional
	@Override
	public DetalleMatricula save(DetalleMatricula entity) throws Exception {
		return detalleMatriculaRepository.save(entity);
	}

	@Transactional
	@Override
	public DetalleMatricula update(DetalleMatricula entity) throws Exception {
		return detalleMatriculaRepository.save(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		detalleMatriculaRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<DetalleMatricula> findById(Integer id) throws Exception {
		return detalleMatriculaRepository.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<DetalleMatricula> findAll() throws Exception {
		return detalleMatriculaRepository.findAll();
	}

	@Override
	public List<DetalleMatricula> findByMatricula(Matricula matricula) throws Exception {
		return detalleMatriculaRepository.findByMatricula(matricula);
	}

}
