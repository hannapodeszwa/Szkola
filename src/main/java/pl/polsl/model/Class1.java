package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.Klasy;
import pl.polsl.entities.Uczniowie;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class Class1 implements ManageDataBase {
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



}
