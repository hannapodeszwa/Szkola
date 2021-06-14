package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.Przedmioty;
import pl.polsl.entities.Uczniowie;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class Subject implements ManageDataBase {
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



}
