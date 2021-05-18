package pl.polsl.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.polsl.entities.Uczniowie;

public interface UczniowieRepository extends JpaRepository<Uczniowie, Integer>, JpaSpecificationExecutor<Uczniowie> {

}