package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.GodzinyLekcji;
import pl.polsl.entities.Nieobecnosci;
import pl.polsl.entities.Wiadomosci;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LessonTimeModel implements ManageDataBase {

    private EntityManager entityManager;

    public Integer getNumberById(Integer id) {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("godzinyLekcji.getNumber", GodzinyLekcji.class);
        query.setParameter("id", id);
        return (Integer) query.getSingleResult();
    }

    public List<GodzinyLekcji> getTime(){

        entityManager = MyManager.getEntityManager();
        TypedQuery<GodzinyLekcji> query = entityManager.createNamedQuery("godzinyLekcji.getAll", GodzinyLekcji.class);
        List<GodzinyLekcji> list = query.getResultList();

        return list;
    }
}
