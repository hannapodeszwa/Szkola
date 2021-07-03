package pl.polsl.model;

import pl.polsl.MyManager;

import javax.persistence.EntityManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminModel {
    private EntityManager entityManager;

    public String getAdminEmailByID(Integer Id){
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createNamedQuery("administratorzy.getAdminEmailByID", String.class)
                    .setParameter("ID", Id)
                    .getSingleResult();
        } catch (Exception e) {
            Logger.getLogger(UserModel.class.getName()).log(Level.WARNING, "Could not get email by id", e);
            return null;
        }
    }
}
