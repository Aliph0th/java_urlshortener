package com.webjavabsu2ndcourse4ndterm.services;

import com.webjavabsu2ndcourse4ndterm.shortener.models.Url;
import com.webjavabsu2ndcourse4ndterm.shortener.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.postgresql.Driver;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class DatabaseService {
    private static final Logger logger = LogManager.getLogger(DatabaseService.class);
    //private static final Dotenv dotenv = Dotenv.load();
    private static SessionFactory sessionFactory = null;
    public DatabaseService() {
        try {
        if(sessionFactory == null) {
            DriverManager.registerDriver(new Driver());
            Configuration cfg = new Configuration().configure();
            cfg.setProperty("hibernate.connection.username", "postgres");
            cfg.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/postgres");
            cfg.setProperty("hibernate.connection.password", "postgresauthpassword");

            cfg.addAnnotatedClass(User.class);
            cfg.addAnnotatedClass(Url.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
            sessionFactory = cfg.buildSessionFactory(builder.build());
        }
    } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Url insertUrl(String originalUrl, String shortUrl, LocalDateTime expiresAt, Long userId) {
        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(shortUrl);
        url.setExpiresAt(expiresAt);

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(url);
            transaction.commit();
            return url;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return null;
        }
    }

    public String getOriginalUrl(String shortUrl) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Url WHERE shortUrl = :shortUrl", Url.class)
                    .setParameter("shortUrl", shortUrl)
                    .uniqueResultOptional()
                    .map(Url::getOriginalUrl)
                    .orElse(null);
        }
    }

    public List<Url> getAllUrls() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Url", Url.class).list();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public void deleteUrl(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Url url = session.get(Url.class, id);
            if (url != null) {
                session.delete(url);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }
    }

    public User insertUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
            return null;
        }
    }

    public User getUserById(Long userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, userId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public boolean deleteUser(Long userId) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, userId);
            if (user != null) {
                session.delete(user);
                transaction.commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
            return false;
        }
    }
}