package jm.task.core.jdbc.dao;

import jakarta.persistence.EntityManager;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.ResultSet;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory = Util.getSessionFactory();
    private UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();
    private List<User> userList;

    public UserDaoHibernateImpl() {

    }

    public UserDaoHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void createUsersTable() {
        userDaoJDBC.createUsersTable();
    }

    @Override
    public void dropUsersTable() {
        userDaoJDBC.dropUsersTable();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);
        saveUser(user);
    }

    public void saveUser(User user) {
        try (
                Session session = sessionFactory.openSession();
        ) {

            session.getTransaction().begin();
            session.persist(user);
            session.getTransaction().commit();
            System.out.printf("пользователя %s сохранен в базу данных\n", user.getName());
        } catch (Exception e) {
            System.out.printf("ну удалось сохранить пользователя %s\n", user.getName());
        }
    }

    @Override
    public void removeUserById(long id) {
        try (
                Session session = sessionFactory.openSession()
        ) {
            session.getTransaction().begin();
            User user = session.byId(User.class).load(id);
            session.remove(user);
            session.getTransaction().commit();
            System.out.printf("пользователь с id: %d удалён!\n", id);
        } catch (Exception e) {
            System.out.printf("пользователь с id: %d не найден и не удалён!\n", id);
        }

    }

    @Override
    public List<User> getAllUsers() {
        EntityManager entityManager = sessionFactory.createEntityManager();
        List<User> users = entityManager.createNativeQuery(
                        "SELECT * FROM user", User.class)
                .getResultList();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        userDaoJDBC.cleanUsersTable();
    }
}
