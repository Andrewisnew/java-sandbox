package edu.andrewisnew.java.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

public class HibernateTest {
    @Test
    public void testPersist() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry =
                new StandardServiceRegistryBuilder()
                        .build();
        try (SessionFactory sessionFactory = createSessionFactory(registry)) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(new User("Anton", 23, 400));
//                session.persist(new User("A", 23, 40));
//                session.persist(new User("Olga", 21, 43));
                List<User> list = session.createCriteria(User.class).list();
                System.out.println(list);
                ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                Validator validator = factory.getValidator();
                for (User user : list) {
                    Set<ConstraintViolation<User>> validate = validator.validate(user);
                    System.out.println("Validation result " + validate);

                }
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we
            // had trouble building the SessionFactory so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    private SessionFactory createSessionFactory(ServiceRegistry registry) {
        return new MetadataSources(registry)
                .addAnnotatedClass(User.class)
                .buildMetadata()
                .buildSessionFactory();
    }
}
