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



    public List<Udzialwkole> findByClub(Kolanaukowe k)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("udzialwkole.findByClub", Udzialwkole.class);
        query.setParameter("idKola", k.getID());
        List<Udzialwkole> results = query.getResultList();
        return results;
    }

    public List<Udzialwkole> findByTeacher(Nauczyciele n)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("udzialwkole.findByTeacher", Udzialwkole.class);
        query.setParameter("id", n.getID());
        List<Udzialwkole> results = query.getResultList();
        return results;
    }


}
