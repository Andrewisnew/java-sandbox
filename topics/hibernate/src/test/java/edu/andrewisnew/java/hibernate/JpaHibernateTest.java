package edu.andrewisnew.java.hibernate;

import edu.andrewisnew.java.hibernate.secondary_table.Book;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Metamodel;
import java.util.Currency;
import java.util.List;

public class JpaHibernateTest {
    @Test
    public void testPersist() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("HiberPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
//        em.persist(new User("ASAS", 13, 114));
        List<User> resultList = em.createNativeQuery("select * from users", User.class).getResultList();
        System.out.println("List %s".formatted(resultList));
        transaction.commit(); //при проблемах валидации упадет здесь.
        // Чтобы валидировалось нужно в CP добавить валидатор и желательно validation-mode CALLBACK чтобы создание EntityManagerFactory падало при отсутствии в CP
        em.close();

        Metamodel metamodel = emf.getMetamodel(); //модель данных
    }


    @Test
    public void testQuery() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("HiberPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = query.from(User.class);
        CriteriaQuery<User> userCriteriaQuery = query.select(userRoot);
        List<User> resultList = em.createQuery(userCriteriaQuery).getResultList();
        System.out.println(resultList);

        Path<String> namePath = userRoot.get("name");
        CriteriaQuery<User> criteriaQuery = query.where(
                criteriaBuilder.like(namePath, criteriaBuilder.parameter(String.class, "pattern"))
        );
        resultList = em.createQuery(criteriaQuery)
                .setParameter("pattern", "%AS%")
                .getResultList();
        System.out.println(resultList);

        em.close();
    }

    @Test
    public void testRefresh() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("HiberPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Apple red = new Apple("red");
        em.persist(red);
        transaction.commit();
//        em.close();
//        em = emf.createEntityManager();
        transaction = em.getTransaction();
        transaction.begin();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaUpdate<Apple> query = criteriaBuilder.createCriteriaUpdate(Apple.class);
        Path<String> attribute = query.from(Apple.class).get("color");
        query.set(attribute, "green").where(criteriaBuilder.equal(attribute, "red"));
        em.createQuery(query).executeUpdate();
        transaction.commit();
//        em.refresh(red);
        System.err.println(red);
//        em.close();
//        em = emf.createEntityManager();
        transaction = em.getTransaction();
        transaction.begin();
        CriteriaQuery<Apple> selectQuery = criteriaBuilder.createQuery(Apple.class);
        selectQuery.select(selectQuery.from(Apple.class));
        System.out.println(em.createQuery(selectQuery).getResultList());
        transaction.commit();
        em.close();
    }

    @Test
    public void testImmutable() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("HiberPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Apple red = new Apple("red");
        em.persist(red);
        transaction.commit();
        em.close();

        em = emf.createEntityManager();
        transaction = em.getTransaction();
        transaction.begin();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        red.setColor("green");
        em.merge(red);
        transaction.commit();
        System.err.println(red);
        em.close();

        em = emf.createEntityManager();
        transaction = em.getTransaction();
        transaction.begin();
        CriteriaQuery<Apple> selectQuery = criteriaBuilder.createQuery(Apple.class);
        selectQuery.select(selectQuery.from(Apple.class));
        System.out.println(em.createQuery(selectQuery).getResultList());
        transaction.commit();
        em.close();
    }

    @Test
    public void testGreenAppleSubselect() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("HiberPU");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            entityManager.persist(new Apple("red"));
            entityManager.persist(new Apple("red"));
            entityManager.persist(new Apple("red"));
            entityManager.persist(new Apple("green"));
            entityManager.persist(new Apple("green"));
            System.out.println(entityManager.createQuery("select a from GreenApple a").getResultList());
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    @Test
    public void testItem() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("HiberPU");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Currency currency = Currency.getAvailableCurrencies().iterator().next();
        Item item = new Item(4, 7, Item.Color.RED, new Item.Category("shape", "square"), new Item.Category("dimension", "2D"), new Item.Comment("bla"), new Item.MonetaryAmount(10, currency), new Item.MonetaryAmount(20, currency), new Item.MonetaryAmount(30, currency));
        entityManager.persist(item);
        entityManager.flush();//иначе рефреш упадёт не найдя в базе
        entityManager.refresh(item);//чтобы подтянуть
        System.out.println(item);
        transaction.commit();
        entityManager.close();
        entityManager = emf.createEntityManager();
        transaction = entityManager.getTransaction();
        transaction.begin();
        System.out.println(entityManager.createQuery("select it from Item it").getResultList());
        System.out.println(entityManager.createQuery("select it from Item it where it.additionalCategory.group = 'dimension'").getResultList());
        transaction.commit();
        entityManager.close();
        emf.close();
    }

    @Test
    public void testBook() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("HiberPU");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(new Book("Idiot", "Longlonglonglonglong"));
        entityManager.persist(new Book("Idiot", "Longlonglonglonglong"));
        entityManager.flush();
        System.out.println(entityManager.createQuery("from Book").getResultList());
        transaction.commit();
        entityManager.close();
        emf.close();
    }
}
