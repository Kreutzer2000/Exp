package pe.edu.upc.matricula.models.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cursos")
public class Curso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nombre", length = 50, nullable = false)
	private String nombre;
	
	@Column(name ="codigo", length = 4, unique = true, nullable = false)
	private String codigo;
	
	@Column(name = "estado")
	private Boolean estado;
	
	@OneToMany(mappedBy = "curso")
	private List<DocenteCurso> docenteCursos;
	
	public Curso() {
		docenteCursos = new ArrayList<DocenteCurso>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public List<DocenteCurso> getDocenteCursos() {
		return docenteCursos;
	}

	public void setDocenteCursos(List<DocenteCurso> docenteCursos) {
		this.docenteCursos = docenteCursos;
	}
}
