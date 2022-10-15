package jm.task.core.jdbc.model;

import java.sql.Connection;

public interface ModelConnector {
    Connection getConnection();
}
