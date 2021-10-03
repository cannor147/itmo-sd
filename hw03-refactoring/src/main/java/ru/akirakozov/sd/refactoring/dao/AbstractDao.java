package ru.akirakozov.sd.refactoring.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractDao<T> {
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

    protected List<T> select(String query) throws SQLException {
        final ResultSet resultSet = executeQuery(query);
        final List<T> products = new ArrayList<>();
        while (resultSet.next()) {
            products.add(transform(resultSet));
        }
        return products;
    }

    protected Optional<T> selectOnly(String query) throws SQLException {
        final ResultSet resultSet = executeQuery(query);
        return resultSet.next() ? Optional.of(resultSet).map(this::transform) : Optional.empty();
    }

    protected int selectInt(String query) throws SQLException {
        final ResultSet resultSet = executeQuery(query);
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
        queryBuilder.append("VALUES (").append(arguments).append(")");
        executeUpdate(arguments);
    }

    protected abstract String getName();
    protected abstract T transform(ResultSet resultSet);
}
