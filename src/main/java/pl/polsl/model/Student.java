package pl.polsl.model;

import javax.persistence.*;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Student implements ManageDataBase {
    /**
     * Entity manager
     */
    EntityManager entityManager;


    public List<Uczniowie> getAllStudents()
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("uczniowie.findAll", Uczniowie.class);
        List<Uczniowie> results = query.getResultList();
        return results;
    }

    public Uczniowie getStudentById(Integer id)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("uczniowie.findById", Uczniowie.class);
        query.setParameter("id", id);
        Uczniowie results = (Uczniowie) query.getSingleResult();
        return results;
    }

    public String getStudentEmailById(Integer Id){
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createNamedQuery("uczniowie.getStudentEmailById", String.class)
                    .setParameter("ID", Id)
                    .getSingleResult();
        } catch (Exception e) {
            Logger.getLogger(UserModel.class.getName()).log(Level.WARNING, "Could not get email by id", e);
            return null;
        }
    }
    /*public Nauczyciele getStudentByName(String s)
    {
        em = MyManager.getEntityManager();
        String[] splited = s.split("\\s+");
        TypedQuery query = em.createNamedQuery("nauczyciele.findByName", Nauczyciele.class);
        query.setParameter("name", splited[0]);
        query.setParameter("surname", splited[1]);
        Nauczyciele results = (Nauczyciele) query.getSingleResult();
        return results;
    }*/

//    public void updateStudent(Uczniowie u) {
//        TypedQuery query = em.createNamedQuery("uczniowie.updateOne", Uczniowie.class).setParameter("ID", u.getID())
//                .setParameter("Klasa", u.getIdKlasy())
//                .setParameter("Imie", u.getImie())
//                .setParameter("DrugieImie", u.getDrugieImie())
//                .setParameter("Nazwisko", u.getNazwisko())
//                .setParameter("Adres", u.getAdres());
//    }



}
