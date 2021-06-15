package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.controller.ManageDataBase;
import pl.polsl.entities.Nauczyciele;
import pl.polsl.entities.Rodzice;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParentModel implements ManageDataBase {
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

    public List displayParents()
    {
        entityManager = MyManager.getEntityManager();

        TypedQuery query =
                entityManager.createNamedQuery("rodzice.findAll", Rodzice.class);
        List<Rodzice> results = query.getResultList();
        return results;
    }
}
