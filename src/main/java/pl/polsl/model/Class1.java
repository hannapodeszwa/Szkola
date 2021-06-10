package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.entities.Klasy;
import pl.polsl.entities.Uczniowie;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class Class1 {
    /**
     * Entity manager
     */
    EntityManager em;

    public List displayClass()
    {
        em = MyManager.getEntityManager();
        TypedQuery query = em.createNamedQuery("klasy.findAll", Klasy.class);
        List<Klasy> results = query.getResultList();
        return results;
    }

    /**
     * Modifies existing student in database
     *
     * @param object new object
     */
    public void update(Object object) {
        em = MyManager.getEntityManager();
        try {
            em.getTransaction().begin();

            em.merge(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }


    /**
     * Insert new object to database
     *
     * @param object new object
     */
    public void persist(Object object) {
        em = MyManager.getEntityManager();
        try {
            em.getTransaction().begin();

            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

}
