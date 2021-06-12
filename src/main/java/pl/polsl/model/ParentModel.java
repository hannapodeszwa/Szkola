package pl.polsl.model;

import pl.polsl.MyManager;
import pl.polsl.entities.Rodzicielstwo;

import javax.persistence.EntityManager;
import java.util.List;

import static pl.polsl.model.Queries.*;

public class ParentModel {
    EntityManager entityManager;

    public String getParentEmailByID(int id) {
        entityManager = MyManager.getEntityManager();
        try {
            return entityManager.createQuery(GET_PARENT_EMAIL_BY_ID, String.class)
                    .setParameter("ID", id)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
