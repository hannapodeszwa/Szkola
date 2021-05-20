package pl.polsl.model;

import javax.persistence.*;

import pl.polsl.MyManager;
import pl.polsl.entities.*;

import java.util.List;

public class Student {
    /**
     * Entity manager
     */
    EntityManager em;


    public List displayStudents()
    {
       // Uczniowie u = new Uczniowie();
        //Query q= em.createNativeQuery("SELECT * FROM UCZNIOWIE");

        em = MyManager.getEntityManager();

        TypedQuery query =
                em.createNamedQuery("uczniowie.findAll", Uczniowie.class);
        List<Uczniowie> results = query.getResultList();
return results;
    }


    /**
     * Insert new object to database
     *
     * @param object new object
     */
    public void persist(Object object) {
        try {
            em.getTransaction().begin();

            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

}
