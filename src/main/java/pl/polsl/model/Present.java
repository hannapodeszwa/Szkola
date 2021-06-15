package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.Klasy;
import pl.polsl.entities.Nieobecnosci;
import pl.polsl.entities.Przedmioty;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class Present implements ManageDataBase {


    EntityManager entityManager;

    public List displayPresent()
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("nieobecnosci.findAll", Nieobecnosci.class);
        List<Nieobecnosci> results = query.getResultList();
        return results;
    }


    public String getSubjectName(Integer id)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("przedmioty.findById", Przedmioty.class);
        query.setParameter("id", id);
        Przedmioty results = (Przedmioty) query.getSingleResult();
        return results.getNazwa();
    }

}
