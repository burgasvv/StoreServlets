package com.burgas.storeservlets.dao;

import com.burgas.storeservlets.entity.Order;
import com.burgas.storeservlets.entity.OrderProduct;
import com.burgas.storeservlets.entity.Product;
import com.burgas.storeservlets.exception.OrderProductDaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderProductDao extends Dao<OrderProduct> {

    @Override
    public OrderProduct get(int id) {
        String query = "select * from order_products " +
                "join public.orders o on o.id = order_products.order_id " +
                "join public.products p on p.id = order_products.product_id " +
                "where order_products.id = ?";

        try (PreparedStatement statement = getConnection().prepareStatement(query)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            int price = resultSet.getInt("price");
            String orderNumber = resultSet.getString("order_number");
            String date = resultSet.getString("date");
            int productCount = resultSet.getInt("product_count");

            return new OrderProduct(
                    new Product(name,description,price),
                    new Order(orderNumber,date),
                    productCount
            );
        } catch (SQLException e) {
            throw new OrderProductDaoException("Can't get order_product", e.getCause());
        }
    }

    @Override
    public List<OrderProduct> getAll() {
        List<OrderProduct>orderProducts = new ArrayList<>();
        String query = "select * from order_products " +
                "join public.products p on p.id = order_products.product_id " +
                "join public.orders o on o.id = order_products.order_id";

        try (PreparedStatement statement = getConnection().prepareStatement(query)){
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int price = resultSet.getInt("price");
                String orderNumber = resultSet.getString("order_number");
                String date = resultSet.getString("date");
                int productCount = resultSet.getInt("product_count");
                orderProducts.add(
                        new OrderProduct(
                                new Product(id, name, description, price),
                                new Order(id, orderNumber, date),
                                productCount
                        )
                );
            }

            return orderProducts;

        } catch (SQLException e) {
            throw new OrderProductDaoException("Can't get order products", e.getCause());
        }
    }

    @Override
    public void insert(OrderProduct orderProduct) {
        String query = "insert into order_products(order_id, product_id, product_count) values(?, ?, ?)";

        try (PreparedStatement statement = getConnection().prepareStatement(query)){
            statement.setInt(1, orderProduct.getOrder().getId());
            statement.setInt(2, orderProduct.getProduct().getId());
            statement.setInt(3, orderProduct.getProductCount());
            statement.execute();

        } catch (SQLException e) {
            throw new OrderProductDaoException("Can't insert order_product", e.getCause());
        }
    }

    @Override
    public void update(OrderProduct orderProduct, int id) {
        String query = "update order_products set order_id = ? and product_id = ? where id = ?";

        try (PreparedStatement statement = getConnection().prepareStatement(query)){
            statement.setInt(1,orderProduct.getOrder().getId());
            statement.setInt(2, orderProduct.getProduct().getId());
            statement.setInt(3, id);
            statement.execute();

        } catch (SQLException e) {
            throw new OrderProductDaoException("Can't update order_product", e.getCause());
        }
    }

    @Override
    public void delete(int id) {
        String query = "delete from order_products where id = ?";

        try (PreparedStatement statement = getConnection().prepareStatement(query)){
            statement.setInt(1, id);
            statement.execute();

        } catch (SQLException e) {
            throw new OrderProductDaoException("Can't delete order_product", e.getCause());
        }
    }
}
