package ru.akirakozov.sd.refactoring.dao;

import ru.akirakozov.sd.refactoring.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDao extends AbstractDao<Product> {
    public List<Product> findAll() throws SQLException {
        return select("*", "");
    }

    public Optional<Product> findMax() throws SQLException {
        return selectOnly("*", "order by price desc limit 1");
    }

    public Optional<Product> findMin() throws SQLException {
        return selectOnly("*", "order by price limit 1");
    }

    public int sumPrices() throws SQLException {
        return selectInt("sum(price)", "");
    }

    public int count() throws SQLException {
        return selectInt("count(*)", "");
    }

    public void insert(Product product) throws SQLException {
        insert(List.of("name", "price"), product.getName(), product.getPrice());
    }

    @Override
    protected String getName() {
        return "product";
    }

    @Override
    protected List<String> getFieldDescriptions() {
        return List.of(
                "id integer primary key autoincrement not null",
                "name text not null",
                "price int not null"
        );
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
