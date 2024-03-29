package ru.akirakozov.sd.refactoring.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SuppressWarnings("SameParameterValue")
public abstract class AbstractDao<T> {
    public static final String SQLITE_TEST_DB = "jdbc:sqlite:test.db";

    protected ResultSet executeQuery(String query) throws SQLException {
        try (Connection c = DriverManager.getConnection(SQLITE_TEST_DB)) {
            try (Statement statement = c.createStatement()) {
                return statement.executeQuery(query);
            }
        }
    }

    protected void executeUpdate(String query) throws SQLException {
        try (Connection c = DriverManager.getConnection(SQLITE_TEST_DB)) {
            try (Statement statement = c.createStatement()) {
                statement.executeQuery(query);
            }
        }
    }

    public void createTable() throws SQLException {
        final String fields = String.join(", ", getFieldDescriptions());
        executeUpdate(String.format("create table if not exists %s (%s)", getName(), fields));
    }

    protected List<T> select(String target, String condition) throws SQLException {
        final ResultSet resultSet = executeQuery(String.format("select %s from %s %s", target, getName(), condition));
        final List<T> products = new ArrayList<>();
        while (resultSet.next()) {
            products.add(transform(resultSet));
        }
        return products;
    }

    protected Optional<T> selectOnly(String target, String condition) throws SQLException {
        final ResultSet resultSet = executeQuery(String.format("select %s from %s %s", target, getName(), condition));
        return resultSet.next() ? Optional.of(resultSet).map(this::transform) : Optional.empty();
    }

    protected int selectInt(String target, String condition) throws SQLException {
        final ResultSet resultSet = executeQuery(String.format("select %s from %s %s", target, getName(), condition));
        return resultSet.next() ? resultSet.getInt(1) : 0;
    }

    protected void insert(List<String> fieldNames, Object... fieldValues) throws SQLException {
        if (fieldNames.size() != fieldValues.length) {
            throw new IllegalArgumentException("Wrong size of field arguments.");
        }

        final String arguments = Arrays.stream(fieldValues)
                .map(Object::toString)
                .map(x -> '"' + x + '"')
                .collect(Collectors.joining(", "));
        final StringBuilder queryBuilder = new StringBuilder("insert into ").append(getName());
        queryBuilder.append(" (").append(String.join(", ", fieldNames)).append(") ");
        queryBuilder.append("values (").append(arguments).append(")");
        executeUpdate(arguments);
    }

    protected abstract String getName();
    protected abstract List<String> getFieldDescriptions();
    protected abstract T transform(ResultSet resultSet);
}
