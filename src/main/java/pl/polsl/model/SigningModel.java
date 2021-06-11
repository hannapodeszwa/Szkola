package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.entities.Uzytkownicy;

import javax.persistence.EntityManager;

import static pl.polsl.model.Queries.*;

public class SigningModel {

    EntityManager entityManager;

    public Uzytkownicy getUser(String login, String password) {
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createQuery(GET_USER_BY_LOGIN_AND_PASSWORD, Uzytkownicy.class)
                    .setParameter("LOGIN", login)
                    .setParameter("PASSWORD", password)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
