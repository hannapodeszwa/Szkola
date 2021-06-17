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

    public Przedmioty getSubjectById(Integer id)
    {
        em = MyManager.getEntityManager();
        TypedQuery query = em.createNamedQuery("przedmioty.findById", Przedmioty.class);
        query.setParameter("id", id);
        Przedmioty results = (Przedmioty) query.getResultList().get(0);
        return results;
    }

    public String getSubjectName(Integer id)
    {
        em = MyManager.getEntityManager();
        TypedQuery query = em.createNamedQuery("przedmioty.findById", Przedmioty.class);
        query.setParameter("id", id);
        Przedmioty results = (Przedmioty) query.getSingleResult();
        return results.getNazwa();
    }

    public Przedmioty getSubjectByName(String name){
        em = MyManager.getEntityManager();
        System.out.println(name);
        TypedQuery query = em.createNamedQuery("przedmioty.getSubjectByName", Przedmioty.class);
        query.setParameter("nazwa", name);
        Przedmioty result = (Przedmioty) query.getSingleResult();

        return result;
    }

}
