package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.entities.Przedmioty;
import pl.polsl.entities.Uczniowie;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class Subject {
    /**
     * Entity manager
     */
    EntityManager em;

    public List displaySubjects()
    {
        em = MyManager.getEntityManager();
        TypedQuery query = em.createNamedQuery("przedmioty.findAll", Przedmioty.class);
        List<Przedmioty> results = query.getResultList();
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
