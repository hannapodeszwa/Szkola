package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.entities.Kodyweryfikacyjne;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VerificationCodesModel {
    EntityManager entityManager;

    public void insertVerificationCode(String code, String login, String email) {
        Kodyweryfikacyjne verificationCode = new Kodyweryfikacyjne();
        verificationCode.setEmail(email);
        verificationCode.setKod(code);
        verificationCode.setLogin(login);
        entityManager = MyManager.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(verificationCode);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            Logger.getLogger(VerificationCodesModel.class.getName()).log(Level.WARNING, "Could not insert value", e);
        }
    }

    public Kodyweryfikacyjne getVerificationCode(String code) {
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createNamedQuery("Kodyweryfikacyjne.getVerificationCodeByCode", Kodyweryfikacyjne.class)
                    .setParameter("CODE", code)
                    .getSingleResult();
        } catch (NoResultException e) {
            Logger.getLogger(VerificationCodesModel.class.getName()).log(Level.WARNING, "Could not get value", e);
            return null;
        }
    }
}
