package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.entities.Rodzicielstwo;
import pl.polsl.model.email.MailSenderModel;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ParenthoodModel {
    EntityManager entityManager;

    public List<Rodzicielstwo> getParentsByChildID(int id) {
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createNamedQuery("Rodzicielstwo.getParentsByChildId", Rodzicielstwo.class)
                    .setParameter("ID", id)
                    .getResultList();
        } catch (Exception e) {
            Logger.getLogger(ParenthoodModel.class.getName()).log(Level.WARNING, "Could not get parents by child", e);
            return null;
        }
    }
}
