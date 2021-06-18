package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Udzialwkonkursie;
import pl.polsl.entities.Uwagi;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CompetiotionModel implements ManageDataBase {
    EntityManager entityManager;

    public List<Udzialwkonkursie> findByTeacher(Nauczyciele n)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("udzialwkonkursie.findByTeacher", Udzialwkonkursie.class);
        query.setParameter("id", n.getID());
        List<Udzialwkonkursie> results = query.getResultList();
        return results;
    }
}
