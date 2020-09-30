package ru.akirakozov.sd.refactoring.dao;

import ru.akirakozov.sd.refactoring.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDao {
    public void createTable() throws SQLException {
        executeUpdate("CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)");
    }

    public List<Product> findAll() throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM PRODUCT");
        List<Product> products = new ArrayList<>();
        while (rs.next()) {
            Product product = new Product();
            product.setName(rs.getString("name"));
            product.setPrice(rs.getInt("price"));
            products.add(product);
        }
        return products;
    }

    public Optional<Product> findMax() throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
        if (rs.next()) {
            Product product = new Product();
            product.setName(rs.getString("name"));
            product.setPrice(rs.getInt("price"));
            return Optional.of(product);
        } else {
            return Optional.empty();
        }
    }

    public Optional<Product> findMin() throws SQLException {
        ResultSet rs = executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
        if (rs.next()) {
            Product product = new Product();
            product.setName(rs.getString("name"));
            product.setPrice(rs.getInt("price"));
            return Optional.of(product);
        } else {
            return Optional.empty();
        }
    }

    public int sumPrices() throws SQLException {
        ResultSet rs = executeQuery("SELECT SUM(price) FROM PRODUCT");
        return rs.next() ? 0 : rs.getInt(1);
    }

    public int count() throws SQLException {
        ResultSet rs = executeQuery("SELECT COUNT(*) FROM PRODUCT");
        return rs.next() ? 0 : rs.getInt(1);
    }

    public void insert(Product product) throws SQLException {
        executeUpdate("INSERT INTO PRODUCT" +
                "(NAME, PRICE) VALUES (\"" + product.getName() + "\"," + product.getPrice() + ")");
    }

    private ResultSet executeQuery(String query) throws SQLException {
        ResultSet resultSet;
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement statement = c.createStatement();
            resultSet = statement.executeQuery(query);
            statement.close();
        }
        return resultSet;
    }

    private void executeUpdate(String query) throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement statement = c.createStatement();
            statement.executeQuery(query);
            statement.close();
        }
    }
}
