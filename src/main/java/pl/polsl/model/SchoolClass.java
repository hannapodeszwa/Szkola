package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.Klasy;
import pl.polsl.entities.Uczniowie;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class SchoolClass implements ManageDataBase {
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

    public Integer getClassId(String num)
    {
        em = MyManager.getEntityManager();
        TypedQuery query = em.createNamedQuery("klasy.findByNumber", Klasy.class);
        query.setParameter("number", num);
        Klasy results = (Klasy) query.getSingleResult();
        return results.getID();
    }


    public String getClassNumber(Integer id)
    {
        em = MyManager.getEntityManager();
        TypedQuery query = em.createNamedQuery("klasy.findById", Klasy.class);
        query.setParameter("id", id);
        Klasy results = (Klasy) query.getSingleResult();
        return results.getNumer();
    }



}
