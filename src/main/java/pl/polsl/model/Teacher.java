package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.entities.Kodyweryfikacyjne;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Uczniowie;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Teacher {

    /**
     * Entity manager
     */
    EntityManager entityManager;


    public List displayTeachers()
    {
        entityManager = MyManager.getEntityManager();

        TypedQuery query =
                entityManager.createNamedQuery("nauczyciele.findAll", Nauczyciele.class);
        List<Nauczyciele> results = query.getResultList();
        return results;
    }

    public void update(Object object) {
        entityManager = MyManager.getEntityManager();
        try {
            entityManager.getTransaction().begin();

            entityManager.merge(object);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }


    /**
     * Insert new object to database
     *
     * @param object new object
     */
    public void persist(Object object) {
        entityManager = MyManager.getEntityManager();
        try {
            entityManager.getTransaction().begin();

            entityManager.persist(object);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }
}
