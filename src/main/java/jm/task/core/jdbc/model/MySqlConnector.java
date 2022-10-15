package jm.task.core.jdbc.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnector implements ModelConnector {
    private String userName;
    private String password;
    private String connectionUrl;
    private String forName;

    private MySqlConnector() {
    }

    public MySqlConnector(String userName, String password, String connectionUrl, String forName) {
        this.userName = userName;
        this.password = password;
        this.connectionUrl = connectionUrl;
        this.forName = forName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public void setConnectionUrl(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public String getForName() {
        return forName;
    }

    public void setForName(String forName) {
        this.forName = forName;
    }

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(connectionUrl, userName, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
