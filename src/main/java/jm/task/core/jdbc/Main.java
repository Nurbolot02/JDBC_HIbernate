package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService;
        User user = new User(1L, "Нурболот", "Гуламидинов", (byte) 20);
        User user2 = new User(2L, "Олег", "Олегов", (byte) 30);
        User user3 = new User(3L, "Владимир", "Владимиров", (byte) 40);
        User user4 = new User(4L, "Ольга", "Олесова", (byte) 30);

        userService = new UserServiceImpl(new UserDaoJDBCImpl());
        test(userService, user, user2, user3, user4);


        System.out.println("\nHibernate\n");

        userService = new UserServiceImpl(new UserDaoHibernateImpl());
        test(userService, user, user2, user3, user4);

    }

    private static void test(UserServiceImpl userService, User user, User user2, User user3, User user4) {
        List<User> userList;
        userService.createUsersTable();
        userService.saveUser(user);
        userService.saveUser(user2);
        userService.saveUser(user3);
        userService.saveUser(user4);
        userList = userService.getAllUsers();
        userList.forEach(System.out::println);

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
