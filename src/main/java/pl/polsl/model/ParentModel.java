package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.entities.Rodzicielstwo;
import pl.polsl.model.email.MailSenderModel;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static pl.polsl.model.Queries.*;

public class ParentModel {
    EntityManager entityManager;

    public String getParentEmailByID(int id) {
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createQuery(GET_PARENT_EMAIL_BY_ID, String.class)
                    .setParameter("ID", id)
                    .getSingleResult();
        } catch (Exception e) {
            Logger.getLogger(ParentModel.class.getName()).log(Level.WARNING, "Could not get value", e);
            return null;
        }
    }
}
