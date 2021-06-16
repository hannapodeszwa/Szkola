package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.Klasy;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Rozklady;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ScheduleModel implements ManageDataBase {

    EntityManager entityManager;

    public List<Rozklady> findByTeacher(Nauczyciele n)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("rozklady.findByTeacher", Rozklady.class);
        query.setParameter("id", n.getID());
        List<Rozklady> results = query.getResultList();
        return results;
    }

    public List<Rozklady> findByClass(Klasy k)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("rozklady.findByClass", Rozklady.class);
        query.setParameter("id", k.getID());
        List<Rozklady> results = query.getResultList();
        return results;
    }

}
