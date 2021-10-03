package ru.akirakozov.sd.refactoring.dao;

import ru.akirakozov.sd.refactoring.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDao extends AbstractDao<Product> {
    public void createTable() throws SQLException {
        executeUpdate("CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)");
    }

    public List<Product> findAll() throws SQLException {
        return select("SELECT * FROM PRODUCT");
    }

    public Optional<Product> findMax() throws SQLException {
        return selectOnly("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
    }

    public Optional<Product> findMin() throws SQLException {
        return selectOnly("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
    }

    public int sumPrices() throws SQLException {
        return selectInt("SELECT SUM(price) FROM PRODUCT");
    }

    public int count() throws SQLException {
        return selectInt("SELECT COUNT(*) FROM PRODUCT");
    }

    public void insert(Product product) throws SQLException {
        executeUpdate("INSERT INTO PRODUCT" +
                "(NAME, PRICE) VALUES (\"" + product.getName() + "\"," + product.getPrice() + ")");
    }

    @Override
    protected Product transform(ResultSet resultSet) {
        try {
            final Product product = new Product();
            product.setName(resultSet.getString("name"));
            product.setPrice(resultSet.getInt("price"));
            return product;
        } catch (SQLException e) {
            return null;
        }
    }
}
