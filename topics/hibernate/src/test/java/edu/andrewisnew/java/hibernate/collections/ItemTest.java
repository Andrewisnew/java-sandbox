package edu.andrewisnew.java.hibernate.collections;

import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class ItemTest {
    @Test
    public void test() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Collections");
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            try {
                EntityTransaction transaction = entityManager.getTransaction();
                transaction.begin();
                try {
                    entityManager.persist(new Item(Set.of("image1", "image2"), List.of("cool", "cool", "very cool"), List.of("video1", "video2", "video2"),
                            Map.of("c1", "v1", "c2", "v2"), new TreeMap<>(Map.of("t1", "v1", "t2", "v2")), new TreeSet<>(Set.of("tag1", "tag2"))));
                    transaction.commit();
                } catch (Exception e) {
                    transaction.rollback();
                }
                } finally {
                entityManager.close();
            }
        } finally {
            entityManagerFactory.close();
        }
    }

}