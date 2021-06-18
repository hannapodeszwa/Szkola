package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.Uwagi;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class NoteModel implements ManageDataBase {

    EntityManager entityManager;

    public List<Uwagi> getStudentNote(Integer id)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query = entityManager.createNamedQuery("uwagi.getStudentNote", Uwagi.class);
        query.setParameter("id", id);
        return (List<Uwagi>) query.getResultList();
    }

}
