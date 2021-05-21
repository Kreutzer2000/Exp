package pe.edu.upc.matricula.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.matricula.models.entities.Alumno;
import pe.edu.upc.matricula.models.entities.Usuario;
import pe.edu.upc.matricula.models.repositories.AlumnoRepository;
import pe.edu.upc.matricula.services.AlumnoService;

@Service
public class AlumnoServiceImpl implements AlumnoService, Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private AlumnoRepository alumnoRepository;
	
	@Transactional
	@Override
	public Alumno save(Alumno entity) throws Exception {
		return alumnoRepository.save(entity);
	}

	@Transactional
	@Override
	public Alumno update(Alumno entity) throws Exception {
		return alumnoRepository.save(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		alumnoRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Alumno> findById(Integer id) throws Exception {
		return alumnoRepository.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Alumno> findAll() throws Exception {
		return alumnoRepository.findAll();
	}

	@Override
	public Optional<Alumno> findByUsuario(Usuario usuario) throws Exception {
		return alumnoRepository.findByUsuario(usuario);
	}

}
