package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.Klasy;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Rodzice;
import pl.polsl.entities.Wiadomosci;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageModel implements ManageDataBase {

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

    public List<Wiadomosci> findByTeacher(Nauczyciele n)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query =
                entityManager.createNamedQuery("wiadomosci.findByTeacher", Wiadomosci.class);
        List<Wiadomosci> results = query.setParameter("ID", n.getID()).getResultList();

        return results;
    }

    public List<Wiadomosci> findByParent(Rodzice r)
    {
        entityManager = MyManager.getEntityManager();
        TypedQuery query =
                entityManager.createNamedQuery("wiadomosci.findByParent", Wiadomosci.class);
        List<Wiadomosci> results = query.setParameter("ID", r.getID()).getResultList();

        return results;
    }

}
