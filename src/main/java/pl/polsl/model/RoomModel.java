package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Rozklady;
import pl.polsl.entities.Sale;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class RoomModel implements ManageDataBase {
    EntityManager entityManager;

    public String getNameById(Integer id)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("sale.getNameById", Sale.class);
        query.setParameter("id", id);
        String results = (String)query.getSingleResult();
        return results;
    }
}
