package pl.polsl.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.polsl.entities.Oceny;

public interface OcenyRepository extends JpaRepository<Oceny, Integer>, JpaSpecificationExecutor<Oceny> {

}