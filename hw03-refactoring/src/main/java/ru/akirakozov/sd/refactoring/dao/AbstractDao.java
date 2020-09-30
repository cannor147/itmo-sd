package ru.akirakozov.sd.refactoring.dao;

import java.sql.*;

public abstract class AbstractDao {
    protected ResultSet executeQuery(String query) throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            try (Statement statement = c.createStatement()) {
                return statement.executeQuery(query);
            }
        }
    }

    protected void executeUpdate(String query) throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            try (Statement statement = c.createStatement()) {
                statement.executeQuery(query);
            }
        }
    }
}
