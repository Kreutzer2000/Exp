package pe.edu.upc.matricula.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.matricula.models.entities.Usuario;
import pe.edu.upc.matricula.models.repositories.UsuarioRepository;
import pe.edu.upc.matricula.services.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService, Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Transactional
	@Override
	public Usuario save(Usuario entity) throws Exception {
		return usuarioRepository.save(entity);
	}

	@Transactional
	@Override
	public Usuario update(Usuario entity) throws Exception {
		return usuarioRepository.save(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		usuarioRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Usuario> findById(Integer id) throws Exception {
		return usuarioRepository.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Usuario> findAll() throws Exception {
		return usuarioRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Usuario> findByNumeroDocumento(String numeroDocumento) throws Exception {
		return usuarioRepository.findByNumeroDocumento(numeroDocumento);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Usuario> findByUsuario(String usuario) throws Exception {
		return usuarioRepository.findByUsuario(usuario);
	}

	@Override
	public List<Usuario> findAllOrderByIdDesc() throws Exception {
		return usuarioRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
	}

	@Override
	public List<Usuario> findByRol(String rol) throws Exception {
		return usuarioRepository.findByRolOrderByApellidoPaternoAsc(rol);
	}

	@Override
	public List<Usuario> findAllByEstado(Boolean estado) throws Exception {
		return usuarioRepository.findAllByEstado(estado);
	}

	@Override
	public List<Usuario> findAllByEstadoAndRol(Boolean estado, String rol) throws Exception {
		return usuarioRepository.findAllByEstadoAndRol(estado, rol);
	}

	@Override
	public List<Usuario> findByRolAndUsuarioContaining(String rol, String usuario) throws Exception {
		return usuarioRepository.findByRolAndUsuarioContaining(rol, usuario);
	}

	@Override
	public List<Usuario> findByUsuarioContaining(String usuario) throws Exception {
		return usuarioRepository.findByUsuarioContaining(usuario);
	}

}
