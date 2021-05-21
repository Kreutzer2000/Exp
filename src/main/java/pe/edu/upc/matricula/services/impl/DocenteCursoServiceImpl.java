package pe.edu.upc.matricula.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.matricula.models.entities.DocenteCurso;
import pe.edu.upc.matricula.models.repositories.DocenteCursoRepository;
import pe.edu.upc.matricula.services.DocenteCursoService;

@Service
public class DocenteCursoServiceImpl implements DocenteCursoService, Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private DocenteCursoRepository docenteCursoRepository;
	
	@Transactional
	@Override
	public DocenteCurso save(DocenteCurso entity) throws Exception {
		return docenteCursoRepository.save(entity);
	}

	@Transactional
	@Override
	public DocenteCurso update(DocenteCurso entity) throws Exception {
		return docenteCursoRepository.save(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		docenteCursoRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<DocenteCurso> findById(Integer id) throws Exception {
		return docenteCursoRepository.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<DocenteCurso> findAll() throws Exception {
		return docenteCursoRepository.findAll();
	}

	@Override
	public List<DocenteCurso> findByDocente(Integer docente) throws Exception {
		return docenteCursoRepository.findByDocente(docente);
	}

	@Override
	public List<DocenteCurso> findByCurso(Integer curso) throws Exception {
		return docenteCursoRepository.findByCurso(curso);
	}
}