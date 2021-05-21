package pe.edu.upc.matricula.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.matricula.models.entities.Curso;
import pe.edu.upc.matricula.models.repositories.CursoRepository;
import pe.edu.upc.matricula.services.CursoService;

@Service
public class CursoServiceImpl implements CursoService , Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Transactional
	@Override
	public Curso save(Curso entity) throws Exception {
		return cursoRepository.save(entity);
	}

	@Transactional
	@Override
	public Curso update(Curso entity) throws Exception {
		return cursoRepository.save(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		cursoRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Curso> findById(Integer id) throws Exception {
		return cursoRepository.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Curso> findAll() throws Exception {
		return cursoRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Curso> findByCodigo(String codigo) throws Exception {
		return cursoRepository.findByCodigo(codigo);
	}

	@Override
	public List<Curso> findAllByEstado(Boolean estado) throws Exception {
		return cursoRepository.findAllByEstado(estado);
	}
}
