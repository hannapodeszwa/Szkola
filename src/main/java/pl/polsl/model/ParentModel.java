package pl.polsl.model;

import pl.polsl.MyManager;

import javax.persistence.EntityManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParentModel {
    EntityManager entityManager;

    public String getParentEmailByID(int id) {
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createNamedQuery("Rodzice.getParentEmailById", String.class)
                    .setParameter("ID", id)
                    .getSingleResult();
        } catch (Exception e) {
            Logger.getLogger(ParentModel.class.getName()).log(Level.WARNING, "Could not get value", e);
            return null;
        }
    }
}
