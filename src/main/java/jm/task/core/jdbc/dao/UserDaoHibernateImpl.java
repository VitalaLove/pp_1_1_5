package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSession();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS User (" +
                    "id BIGINT AUTO_INCREMENT NOT NULL," +
                    "name VARCHAR(45) NULL," +
                    "lastName VARCHAR(45) NULL," +
                    "age TINYINT NOT NULL," +
                    "PRIMARY KEY (ID));";
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (RuntimeException e) {

        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS `User`;";
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (RuntimeException e) {

        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (RuntimeException e) {

        }

    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (HibernateException e) {

        }
    }


    @Override
    public List getAllUsers() {
        try {
            Session session = sessionFactory.openSession();
            List<User> result = session.createCriteria(User.class).list();
            return result;
        } catch (RuntimeException e) {
            throw e;
        }
    }

    @Override
    public void cleanUsersTable() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            String sql = "TRUNCATE TABLE `User`;";
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (RuntimeException e) {

        }
    }
}