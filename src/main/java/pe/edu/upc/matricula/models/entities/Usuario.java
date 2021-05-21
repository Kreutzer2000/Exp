package pe.edu.upc.matricula.models.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "users")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nombres", length = 25, nullable = false)
	private String nombres;
	
	@Column(name = "apellido_paterno", length = 20, nullable = false)
	private String apellidoPaterno;
	
	@Column(name = "apellido_materno", length = 20, nullable = false)
	private String apellidoMaterno;
	
	@Column(name = "rol", length = 15, nullable = false)
	private String rol;
	
	@Column(name = "numero_documento", length = 12, unique = true, nullable = false)
	private String numeroDocumento;
	
	@Column(name = "usuario", length = 50, unique = true)
	private String usuario;
	
	@Column(name = "contraseña", length = 60, nullable = false)
	private String contraseña;
	
	private boolean enable;
	
	@Column(name = "estado", nullable = false)
	private Boolean estado;
	
	@OneToOne(mappedBy = "usuario")
	private Docente docente;
	
	@OneToOne(mappedBy = "usuario")
	private Alumno alumno;
	
	@OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Authority> authorities;
	
	public Usuario() {
		this.authorities = new ArrayList<>();
		this.enable = true;
	}
	
	public Usuario( String usuario, String contraseña ) {
		this.usuario = usuario;
		this.contraseña = contraseña;
		this.enable = true;
		this.authorities = new ArrayList<>();
	}
	
	public void addAuthority( String auth ) {
		Authority authority = new Authority();
		authority.setAuthority( auth ) ;
		authority.setUsuario( this );
		
		this.authorities.add( authority );
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Docente getDocente() {
		return docente;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
}
