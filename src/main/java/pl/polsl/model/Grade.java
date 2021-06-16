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

    public List<Oceny> findBySubject(Przedmioty p)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery<Oceny> query = entityManager.createNamedQuery("oceny.findBySubject", Oceny.class);
        query.setParameter("id", p.getID());
        return query.getResultList();
    }

import pl.polsl.entities.Klasy;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Przedmioty;
import pl.polsl.entities.Uczniowie;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

public class Grade implements ManageDataBase {
    EntityManager em;

    public List checkStudentGrades(Uczniowie student, Przedmioty subject)
    {
        em = MyManager.getEntityManager();
        TypedQuery query =
                em.createNamedQuery("oceny.findByStudentAndSubject", Grade.class);

        List<Klasy> results = query.setParameter("idPrzedmiotu", subject.getID()).setParameter("idUcznia", student.getID()).getResultList();

        return results;
    }


}
