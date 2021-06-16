package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.entities.Kodyweryfikacyjne;
import pl.polsl.entities.Uzytkownicy;
import pl.polsl.entities.Wiadomosci;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageModel {

    private EntityManager entityManager;

    public List<Wiadomosci> getReceivedMessagesByTypeAndId(Integer id, Integer type) {
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createNamedQuery("Wiadomosci.getReceivedMessagesByTypeAndId", Wiadomosci.class)
                    .setParameter("ID", id)
                    .setParameter("TYPE", type)
                    .getResultList();
        } catch (Exception e) {
            Logger.getLogger(MessageModel.class.getName()).log(Level.WARNING, "Could not get messages", e);
            return Collections.emptyList();
        }
    }

    public List<Wiadomosci> getSentMessagesByTypeAndId(Integer id, Integer type) {
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createNamedQuery("Wiadomosci.getSentMessagesByTypeAndId", Wiadomosci.class)
                    .setParameter("ID", id)
                    .setParameter("TYPE", type)
                    .getResultList();
        } catch (Exception e) {
            Logger.getLogger(MessageModel.class.getName()).log(Level.WARNING, "Could not get messages", e);
            return Collections.emptyList();
        }
    }

    public Boolean insertMessage(Integer type, String topic, String message, Date date, Integer receiver, Integer sender) {
        Wiadomosci wiadomosci = new Wiadomosci();
        wiadomosci.setTyp(type);
        wiadomosci.setTemat(topic);
        wiadomosci.setTresc(message);
        wiadomosci.setData(date);
        wiadomosci.setOdbiorca(receiver);
        wiadomosci.setNadawca(sender);
        entityManager = MyManager.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(wiadomosci);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            Logger.getLogger(MessageModel.class.getName()).log(Level.WARNING, "Could not insert message", e);
            return false;
        }
    }

}
