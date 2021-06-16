package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.Oceny;
import pl.polsl.entities.Przedmioty;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class Grade implements ManageDataBase {

    EntityManager entityManager;

    public List<Oceny> getGradeByStudent(Integer id)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery<Oceny> query = entityManager.createNamedQuery("oceny.getGradeByIdStudent", Oceny.class);
        query.setParameter("id", id);
        return query.getResultList();
    }


}
