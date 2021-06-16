package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.Godzinylekcji;
import pl.polsl.entities.Nieobecnosci;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;


public class ScheduleTable implements ManageDataBase {

    EntityManager entityManager;
    Integer number;
    String hours;
    String mon;
    String tue;
    String wen;
    String thu;
    String fri;


    Integer numberOfHours(){

        entityManager = MyManager.getEntityManager();
        TypedQuery<Godzinylekcji> query = entityManager.createNamedQuery("nieobecnosci.findAll", Godzinylekcji.class);
        List<Godzinylekcji> list = query.getResultList();

        return 0;
    }



}
