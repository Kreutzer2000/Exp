package pe.edu.upc.matricula.models.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "docente_curso")
public class DocenteCurso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "docente_id")
	private Docente docente;
	
	@ManyToOne
	@JoinColumn(name = "curso_id")
	private Curso curso;
	
	@OneToMany(mappedBy = "docenteCurso")
	private List<DetalleMatricula> detalleMatricula;

	public DocenteCurso() {
		detalleMatricula = new ArrayList<DetalleMatricula>();
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Docente getDocente() {
		return docente;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	public List<DetalleMatricula> getDetalleMatricula() {
		return detalleMatricula;
	}
	public void setDetalleMatricula(List<DetalleMatricula> detalleMatricula) {
		this.detalleMatricula = detalleMatricula;
	}
	
	
	
}
