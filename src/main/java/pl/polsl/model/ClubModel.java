package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.Kolanaukowe;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Udzialwkonkursie;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ClubModel implements ManageDataBase {
    EntityManager entityManager;

    public List<Kolanaukowe> findByTeacher(Nauczyciele n)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("kolanaukowe.findByTeacher", Kolanaukowe.class);
        query.setParameter("id", n.getID());
        List<Kolanaukowe> results = query.getResultList();
        return results;
    }
}
