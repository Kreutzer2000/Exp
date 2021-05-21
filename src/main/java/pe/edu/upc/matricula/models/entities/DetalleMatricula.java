package pe.edu.upc.matricula.models.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "detalle_matricula")
public class DetalleMatricula {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "docenteCurso_id")
	private DocenteCurso docenteCurso;
	
	@ManyToOne
	@JoinColumn(name = "matricula_id")
	private Matricula matricula;
	
	public DetalleMatricula() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DocenteCurso getDocenteCurso() {
		return docenteCurso;
	}

	public void setDocenteCurso(DocenteCurso docenteCurso) {
		this.docenteCurso = docenteCurso;
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}
	
	
}
