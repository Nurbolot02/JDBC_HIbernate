package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.MySqlConnector;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Util util = new Util(
            new MySqlConnector("root", "123456", "jdbc:mysql://localhost:3306/users", "com.mysql.jdbc.Driver"));
    private List<User> userList = new LinkedList<>();

    public UserDaoJDBCImpl() {

    }

    public UserDaoJDBCImpl(Util util) {
        this.util = util;
    }

    public void createUsersTable() {
        try {
            Statement statement = Util.getModelConnector().getConnection().createStatement();
            statement.executeUpdate(
                    "create table user\n" +
                            "(\n" +
                            "    id int not null unique auto_increment primary key,\n" +
                            "    name varchar(20) not null,\n" +
                            "    lastName varchar(20) not null,\n" +
                            "    age int not null\n" +
                            ");"
            );
            System.out.println("Таблица пользователей был создан!");
        } catch (SQLException e) {
            System.out.println("Таблица уже существует!");
        }
    }

    public void dropUsersTable() {
        try {
            Statement statement = Util.getModelConnector().getConnection().createStatement();
            statement.executeUpdate(
                    "drop table user;"
            );
            System.out.println("Таблица пользователей был удалён!");
        } catch (SQLException e) {
            System.out.println("Таблица не существует!");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (
                Statement statement = Util.getModelConnector().getConnection().createStatement();
        ) {

            statement.executeUpdate(
                    String.format("insert into user(name, lastName, age) values (\"%s\", \"%s\", %d);", name, lastName, age)
            );
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
            System.out.println("Таблица не существует!");
        }
    }

    public void removeUserById(long id) {
        try (
                Statement statement = Util.getModelConnector().getConnection().createStatement();
        ) {

            statement.executeUpdate(
                    String.format("delete from user where idUser = %d;", id)
            );
            System.out.printf("Пользователь с id: %d был удалён!\n", id);
        } catch (SQLException e) {
            System.out.printf("пользователь с id: %d не существует!\n", id);
        }
    }

    public List<User> getAllUsers() {
        try (
                Statement statement = Util.getModelConnector().getConnection().createStatement();
        ) {
            ResultSet resultSet = statement.executeQuery("select * from user");
            while (resultSet.next()) {
                userList.add(new User(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4)));
            }
            System.out.println("Все пользователи прочитаны из базы данных!");
        } catch (SQLException e) {
            System.out.println("Таблица не существует!");
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (
                Statement statement = util.getModelConnector().getConnection().createStatement();
        ) {

            statement.executeUpdate(
                    "truncate table user;"
            );
            System.out.println("таблица пользователей очищена!");
        } catch (SQLException e) {
            System.out.println("Таблица не существует!");
        }
    }
}
