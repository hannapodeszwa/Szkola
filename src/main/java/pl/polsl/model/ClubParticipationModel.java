package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.Kolanaukowe;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Udzialwkole;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ClubParticipationModel implements ManageDataBase {
    EntityManager entityManager;

    public List<Kolanaukowe> findByTeacher()
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("udzialwkole.findByTeacher", Kolanaukowe.class);
        List<Kolanaukowe> results = query.getResultList();
        return results;
    }


}
