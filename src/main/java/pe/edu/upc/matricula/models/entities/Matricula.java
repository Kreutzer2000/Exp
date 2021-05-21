package pe.edu.upc.matricula.models.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "matriculas")
public class Matricula {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name = "alumno_id")
	private Alumno alumno;
	
	@OneToMany(mappedBy = "matricula")
	private List<DetalleMatricula> detalleMatricula;
	
	public Matricula() {
		detalleMatricula = new ArrayList<DetalleMatricula>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public List<DetalleMatricula> getDetalleMatricula() {
		return detalleMatricula;
	}

	public void setDetalleMatricula(List<DetalleMatricula> detalleMatricula) {
		this.detalleMatricula = detalleMatricula;
	}
	
	
}
