package edu.andrewisnew.java.hibernate.inheritance.inheritance_joined;

import edu.andrewisnew.java.hibernate.inheritance.Color;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaHibernateTest {
    @Test
    public void testPersist() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("InheritanceJoined");
        try {
            EntityManager entityManager = emf.createEntityManager();
            try {
                EntityTransaction transaction = entityManager.getTransaction();
                transaction.begin();
                try {
                    entityManager.persist(new Rectangle(Color.RED, 4, 1));
                    Circle circle = new Circle(Color.RED, 5);
                    entityManager.persist(circle);
                    transaction.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    transaction.rollback();
                }
            } finally {
                entityManager.close();
            }

            entityManager = emf.createEntityManager();
            try {
                System.out.println(entityManager.createQuery("from Rectangle").getResultList());
                System.out.println(entityManager.createQuery("from Circle").getResultList());
                System.out.println(entityManager.createQuery("from Shape").getResultList());
            } finally {
                entityManager.close();
            }
        } finally {
            emf.close();
        }
    }
}