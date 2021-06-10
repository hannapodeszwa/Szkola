package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Uczniowie;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class Teacher implements ManageDataBase {

    /**
     * Entity manager
     */
    EntityManager em;


    public List displayTeachers()
    {
        em = MyManager.getEntityManager();

        TypedQuery query =
                em.createNamedQuery("nauczyciele.findAll", Nauczyciele.class);
        List<Nauczyciele> results = query.getResultList();
        return results;
    }

}
