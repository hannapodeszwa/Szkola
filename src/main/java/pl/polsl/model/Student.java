package pl.polsl.model;

import javax.persistence.*;

import pl.polsl.MyManager;
import pl.polsl.entities.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Student {
    /**
     * Entity manager
     */
    EntityManager entityManager;


    public List displayStudents()
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("uczniowie.findAll", Uczniowie.class);
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
