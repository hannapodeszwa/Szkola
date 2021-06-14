package pl.polsl.model;

import pl.polsl.MyManager;

import javax.persistence.EntityManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParentModel {
    EntityManager entityManager;

    public String getParentEmailByID(Integer id) {
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createNamedQuery("rodzice.getParentEmailById", String.class)
                    .setParameter("ID", id)
                    .getSingleResult();
        } catch (Exception e) {
            Logger.getLogger(ParentModel.class.getName()).log(Level.WARNING, "Could not get parent's email by id", e);
            return null;
        }
    }
}
