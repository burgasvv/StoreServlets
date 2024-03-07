package com.burgas.storeservlets.dao;

import com.burgas.storeservlets.entity.Order;
import com.burgas.storeservlets.exception.OrderDaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDao extends Dao<Order>{

    @Override
    public Order get(int id) {
        String query = "select * from orders where id = ?";

        try (PreparedStatement statement = getConnection().prepareStatement(query)){
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            String orderNumber = resultSet.getString("order_number");
            String date = resultSet.getString("date");

            return new Order(orderNumber, date);

        } catch (SQLException e) {
            throw new OrderDaoException("Can't get order by id", e.getCause());
        }
    }

    @Override
    public List<Order> getAll() {
        List<Order>orders = new ArrayList<>();
        String query = "select * from orders";

        try (PreparedStatement statement = getConnection().prepareStatement(query)){
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String orderNumber = resultSet.getString("order_number");
                String date = resultSet.getString("date");
                orders.add(new Order(id, orderNumber, date));
            }

        } catch (SQLException e) {
            throw new OrderDaoException("Can't get all elements", e.getCause());
        }
        return orders;
    }

    @Override
    public void insert(Order order) {
        String query = "insert into orders(order_number, date) values(?, ?)";

        try (PreparedStatement statement = getConnection().prepareStatement(query)){
            statement.setString(1, order.getOrderNumber());
            statement.setString(2, order.getDate());
            statement.execute();

        } catch (SQLException e) {
            throw new OrderDaoException("Can't insert order", e.getCause());
        }
    }

    @Override
    public void update(Order order, int id) {
        String query = "update orders set order_number = ? and date = ? where id = ?";

        try (PreparedStatement statement = getConnection().prepareStatement(query)){
            statement.setString(1, order.getOrderNumber());
            statement.setString(2, order.getDate());
            statement.setInt(3, id);
            statement.execute();

        } catch (SQLException e) {
            throw new OrderDaoException("Can't update order", e.getCause());
        }
    }

    @Override
    public void delete(int id) {
        String query = "delete from orders where id = ?";

        try (PreparedStatement statement = getConnection().prepareStatement(query)){
            statement.setInt(1, id);
            statement.execute();

        } catch (SQLException e) {
            throw new OrderDaoException("Can't delete order", e.getCause());
        }
    }
}
