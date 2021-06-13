package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.Nieobecnosci;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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


}
