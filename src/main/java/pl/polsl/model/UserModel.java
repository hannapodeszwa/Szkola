package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.entities.Uzytkownicy;

import javax.persistence.EntityManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserModel {

    EntityManager entityManager;

    public Uzytkownicy getUserByLoginAndPassword(String login, String password) {
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createNamedQuery("Uzytkownicy.getUserByLoginAndPassword", Uzytkownicy.class)
                    .setParameter("LOGIN", login)
                    .setParameter("PASSWORD", password)
                    .getSingleResult();
        } catch (Exception e) {
            Logger.getLogger(UserModel.class.getName()).log(Level.WARNING, "Could not get user by login and password", e);
            return null;
        }
    }

    public Uzytkownicy getUserByLogin(String login) {
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createNamedQuery("Uzytkownicy.getUserByLogin", Uzytkownicy.class)
                    .setParameter("LOGIN", login)
                    .getSingleResult();
        } catch (Exception e) {
            Logger.getLogger(UserModel.class.getName()).log(Level.WARNING, "Could not get user by login", e);
            return null;
        }
    }

    public void updatePasswordByIdAndRole(String role, Integer id, String password) {
        entityManager = MyManager.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.createNamedQuery("Uzytkownicy.updatePasswordByIdAndRole", Uzytkownicy.class)
                    .setParameter("PASSWORD", password)
                    .setParameter("ROLE", role)
                    .setParameter("ID", id)
                    .executeUpdate();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(UserModel.class.getName()).log(Level.WARNING, "Could not update password");
            entityManager.getTransaction().rollback();
        }
    }
}
