package pe.edu.upc.matricula.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.matricula.models.entities.Alumno;
import pe.edu.upc.matricula.models.entities.Matricula;
import pe.edu.upc.matricula.models.repositories.MatriculaRepository;
import pe.edu.upc.matricula.services.MatriculaService;

@Service
public class MatriculaServiceImpl implements MatriculaService , Serializable{

	private static final long serialVersionUID = 1L;

	@Autowired
	private MatriculaRepository matriculaRepository;
	
	@Transactional
	@Override
	public Matricula save(Matricula entity) throws Exception {
		return matriculaRepository.save(entity);
	}

	@Transactional
	@Override
	public Matricula update(Matricula entity) throws Exception {
		return matriculaRepository.save(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		matriculaRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Matricula> findById(Integer id) throws Exception {
		return matriculaRepository.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Matricula> findAll() throws Exception {
		return matriculaRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Matricula> findByAlumno(Alumno alumno) throws Exception {
		return matriculaRepository.findByAlumno(alumno);
	}
}
