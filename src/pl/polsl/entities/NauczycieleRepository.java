package pl.polsl.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.polsl.entities.Nauczyciele;

public interface NauczycieleRepository extends JpaRepository<Nauczyciele, Integer>, JpaSpecificationExecutor<Nauczyciele> {

}