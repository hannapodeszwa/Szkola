package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.entities.Rodzicielstwo;
import pl.polsl.model.email.MailSenderModel;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static pl.polsl.model.Queries.*;

public class ParenthoodModel {
    EntityManager entityManager;

    public List<Rodzicielstwo> getParentsByChildID(int id) {
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createNativeQuery(GET_PARENTS_BY_CHILD_ID, Rodzicielstwo.class)
                    .setParameter(1, id)
                    .getResultList();
        } catch (Exception e) {
            Logger.getLogger(MailSenderModel.class.getName()).log(Level.WARNING, "Could not get value", e);
            return null;
        }
    }
}
