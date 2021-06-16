package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.Nieobecnosci;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class Present implements ManageDataBase {


    EntityManager entityManager;

    public List<Nieobecnosci> displayPresent(Integer id)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery<Nieobecnosci> query = entityManager.createNamedQuery("nieobecnosci.findAll", Nieobecnosci.class);
        query.setParameter("id", id);
        return query.getResultList();
    }


}
