package com.burgas.storeservlets.dao;

import com.burgas.storeservlets.entity.Product;
import com.burgas.storeservlets.exception.ProductDaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao extends Dao<Product> {

    @Override
    public Product get(int id) {
        String query = "select * from products where id = ?";

        try (PreparedStatement statement = getConnection().prepareStatement(query)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            double price = resultSet.getDouble("price");

            return new Product(name,description,price);

        } catch (SQLException e) {
            throw new ProductDaoException("Can't get product by id", e.getCause());
        }
    }

    @Override
    public List<Product> getAll() {

        List<Product>products = new ArrayList<>();
        String query = "select * from products";

        try (PreparedStatement statement = getConnection().prepareStatement(query)){
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                products.add(new Product(id,name,description,price));
            }

        } catch (SQLException e) {
            throw new ProductDaoException("Can't get products", e.getCause());
        }

        return products;
    }

    @Override
    public void insert(Product product) {
        String query = "insert into products(name, description, price) values(?, ?, ?)";

        try (PreparedStatement statement = getConnection().prepareStatement(query)){
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.execute();

        } catch (SQLException e) {
            throw new ProductDaoException("Can't insert product", e.getCause());
        }
    }

    @Override
    public void update(Product product, int id) {
        String query = "update products set name = ? and description = ? where id = ?";

        try (PreparedStatement statement = getConnection().prepareStatement(query)){
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setInt(3, id);
            statement.execute();

        } catch (SQLException e) {
            throw new ProductDaoException("Can't update product", e.getCause());
        }
    }

    @Override
    public void delete(int id) {
        String query = "delete from products where id = ?";

        try (PreparedStatement statement = getConnection().prepareStatement(query)){
            statement.setInt(1,id);
            statement.execute();

        } catch (SQLException e) {
            throw new ProductDaoException("Can't delete product", e.getCause());
        }
    }
}
