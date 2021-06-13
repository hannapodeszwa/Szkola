package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.Klasy;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Uczniowie;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Teacher implements ManageDataBase {

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

    public List checkTutor(Nauczyciele teacher)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query =
                entityManager.createNamedQuery("klasy.findByTutor", Klasy.class);
        List<Klasy> results = query.setParameter("idWychowawcy", teacher.getID()).getResultList();

        return results;
    }
    public Nauczyciele getTeacherById(Integer id)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("nauczyciele.findById", Nauczyciele.class);
        query.setParameter("id", id);
        Nauczyciele results = (Nauczyciele) query.getSingleResult();
        return results;
    }
   /* public Nauczyciele getTeacherByName(String s)
    {
        em = MyManager.getEntityManager();
        String[] splited = s.split("\\s+");
        TypedQuery query = em.createNamedQuery("nauczyciele.findByName", Nauczyciele.class);
        query.setParameter("name", splited[0]);
        query.setParameter("surname", splited[1]);
        Nauczyciele results = (Nauczyciele) query.getSingleResult();
        return results;
    }*/

}
