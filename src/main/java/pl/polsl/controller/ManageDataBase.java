package pl.polsl.controller;

import pl.polsl.MyManager;

import javax.persistence.EntityManager;

public interface ManageDataBase {


    /**
     * Modifies existing student in database
     *
     * @param object new object
     */
    public default void update(Object object) {
        EntityManager em = MyManager.getEntityManager();
        try {
            em.getTransaction().begin();

            em.merge(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }


    /**
     * Insert new object to database
     *
     * @param object new object
     */
    public default void persist(Object object) {
        EntityManager  em = MyManager.getEntityManager();
        try {
            em.getTransaction().begin();

            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    public default void delete(Object object) {
        EntityManager   em = MyManager.getEntityManager();
        try {
            em.getTransaction().begin();

            em.remove(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }


}
