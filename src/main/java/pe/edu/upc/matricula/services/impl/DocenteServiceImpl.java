package pe.edu.upc.matricula.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.matricula.models.entities.Docente;
import pe.edu.upc.matricula.models.entities.Usuario;
import pe.edu.upc.matricula.models.repositories.DocenteRepository;
import pe.edu.upc.matricula.services.DocenteService;

@Service
public class DocenteServiceImpl implements DocenteService, Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private DocenteRepository docenteRepository;
	
	@Transactional
	@Override
	public Docente save(Docente entity) throws Exception {
		return docenteRepository.save(entity);
	}

	@Transactional
	@Override
	public Docente update(Docente entity) throws Exception {
		return docenteRepository.save(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		docenteRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Docente> findById(Integer id) throws Exception {
		return docenteRepository.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Docente> findAll() throws Exception {
		return docenteRepository.findAll();
	}

	@Override
	public List<Docente> FindAllOrderByApellidoPaterno() throws Exception {
		return docenteRepository.findAll(Sort.by(Sort.Direction.ASC, "apellidoPaterno"));
	}

	@Override
	public Optional<Docente> findByUsuario(Usuario usuario) throws Exception {
		return docenteRepository.findByUsuario(usuario);
	}
}