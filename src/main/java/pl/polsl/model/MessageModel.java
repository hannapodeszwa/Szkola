package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.entities.Wiadomosci;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageModel {

    private EntityManager entityManager;

    public List<Wiadomosci> getReceivedMessagesByTypeAndId(Integer id, Integer type){
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createNamedQuery("Wiadomosci.getReceivedMessagesByTypeAndId", Wiadomosci.class)
                    .setParameter("ID", id)
                    .setParameter("TYPE", type)
                    .getResultList();
        } catch (Exception e) {
            Logger.getLogger(UserModel.class.getName()).log(Level.WARNING, "Could not get messages", e);
            return Collections.emptyList();
        }
    }

    public List<Wiadomosci> getSentMessagesByTypeAndId(Integer id, Integer type){
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createNamedQuery("Wiadomosci.getSentMessagesByTypeAndId", Wiadomosci.class)
                    .setParameter("ID", id)
                    .setParameter("TYPE", type)
                    .getResultList();
        } catch (Exception e) {
            Logger.getLogger(UserModel.class.getName()).log(Level.WARNING, "Could not get messages", e);
            return Collections.emptyList();
        }
    }

}
