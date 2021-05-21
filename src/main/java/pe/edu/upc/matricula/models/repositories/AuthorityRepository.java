package pe.edu.upc.matricula.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.edu.upc.matricula.models.entities.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long>{

}
