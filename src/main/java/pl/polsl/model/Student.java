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
        em = MyManager.getEntityManager();
        TypedQuery query = em.createNamedQuery("uczniowie.findAll", Uczniowie.class);
        List<Uczniowie> results = query.getResultList();
        return results;
    }

//    public void updateStudent(Uczniowie u) {
//        TypedQuery query = em.createNamedQuery("uczniowie.updateOne", Uczniowie.class).setParameter("ID", u.getID())
//                .setParameter("Klasa", u.getIdKlasy())
//                .setParameter("Imie", u.getImie())
//                .setParameter("DrugieImie", u.getDrugieImie())
//                .setParameter("Nazwisko", u.getNazwisko())
//                .setParameter("Adres", u.getAdres());
//    }

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
