package com.burgas.storeservlets.service;

import com.burgas.storeservlets.entity.Product;
import com.burgas.storeservlets.entity.OrderProduct;
import com.burgas.storeservlets.entity.Order;
import com.burgas.storeservlets.exception.OrderServiceException;
import com.burgas.storeservlets.manager.DbManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderService {

    /**
     * Поиск наименования, описания, цены, количества и даты по номеру заказа
     *
     * @param orderNumber номер заказа
     * @return лист заказов
     */
    public List<OrderProduct> getInfo(String orderNumber) {

        List<OrderProduct> goods = new ArrayList<>();

        String query = "select name,description,price,product_count,date\n" +
                "from orders\n" +
                "         join order_products op on orders.id = op.order_id\n" +
                "         join products p on p.id = op.product_id\n" +
                "where order_number = ?";

        try (Connection connection = DbManager.createConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, orderNumber);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                String date = resultSet.getString("date");
                int productCount = resultSet.getInt("product_count");

                goods.add(
                        new OrderProduct(
                                new Product(name, description, price),
                                new Order(orderNumber, date),
                                productCount
                        )
                );
            }
            return goods;

        } catch (SQLException e) {
            throw new OrderServiceException("Can't get order number", e.getCause());
        }
    }

    /**
     * Поиск заказа по итоговой цене и количеству товара
     *
     * @param price        итоговая цена
     * @param productCount количество товара
     * @return номера заказов
     */
    public List<String> getBy(int price, int productCount) {

        List<String> orderNumbers = new ArrayList<>();

        String query = "select orders.order_number from orders\n" +
                "join order_products op on orders.id = op.order_id join products p on p.id = op.product_id\n" +
                "group by orders.order_number having sum(product_count * price) < ? and count(product_id) = ?";

        try (Connection connection = DbManager.createConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, price);
            statement.setInt(2, productCount);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String orderNumber = resultSet.getString("order_number");
                orderNumbers.add(orderNumber);
            }

            return orderNumbers;

        } catch (SQLException e) {
            throw new OrderServiceException("Can't get price and product count", e.getCause());
        }
    }

    /**
     * Поиск заказов по фигурирующим в них наименованиям товарам
     *
     * @param productName наименование товара
     * @return номера заказов
     */
    public List<String> getByProductName(String productName) {

        List<String> orderNumbers = new ArrayList<>();

        String query = "select orders.order_number\n" +
                "from orders\n" +
                "         join order_products op on orders.id = op.order_id\n" +
                "         join products p on p.id = op.product_id\n" +
                "where name = ?";

        try (Connection connection = DbManager.createConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, productName);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String orderNumber = resultSet.getString("order_number");
                orderNumbers.add(orderNumber);
            }

            return orderNumbers;

        } catch (SQLException e) {
            throw new OrderServiceException("Can't get product name", e.getCause());
        }
    }

    /**
     * Поиск заказов по наименованию товара и дате
     *
     * @param productName наименование товара
     * @return номера заказов
     */
    public List<String> getByProductNameAndDate(String productName) {

        List<String> orderNumbers = new ArrayList<>();

        String query = "select orders.order_number from orders\n" +
                "join order_products op on orders.id = op.order_id\n" +
                "join products p on p.id = op.product_id\n" +
                "where name = ? and (select date::date) = now()::timestamp::date\n" +
                "group by orders.order_number";

        try (Connection connection = DbManager.createConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, productName);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String orderNumber = resultSet.getString("order_number");
                orderNumbers.add(orderNumber);
            }
            return orderNumbers;

        } catch (SQLException e) {
            throw new OrderServiceException("Can't get product name and date", e.getCause());
        }
    }


    /**
     * Формирование нового заказа по сегоднящним продажам
     *
     * @param orderNumber номер заказа
     * @return информацию о заказе
     */
    public List<OrderProduct> createAndGet(String orderNumber) {

        String insertOrder = "insert into orders(order_number, date) VALUES (?,now())";

        String insertProducts = "do $$\n" +
                "    declare\n" +
                "        count integer := 1;\n" +
                "        idOrder integer = (select max(orders.id) from orders);\n" +
                "        end_loop integer = (select count(*) from (\n" +
                "                     select order_products.product_id as idProduct from order_products\n" +
                "                        join orders o on o.id = order_products.order_id\n" +
                "                        join products p on p.id = order_products.product_id\n" +
                "                     where (select date::date) = now()::date\n" +
                "                 ) as opopiP);\n" +
                "    begin\n" +
                "        while count <= end_loop\n" +
                "            loop\n" +
                "                insert into order_products(order_id, product_id, product_count)\n" +
                "                values (idOrder,\n" +
                "                        (select array(select opopiP2.idproduct from\n" +
                "                       (select order_products.product_id as idProduct from order_products\n" +
                "                                join orders o on o.id = order_products.order_id\n" +
                "                                join products p on p.id = order_products.product_id\n" +
                "                             where (select date::date) = now()::date) as opopiP2))[count],\n" +
                "                        (select floor(random() * 10 + 1)::integer));\n" +
                "                count := count + 1;\n" +
                "            end loop;\n" +
                "    end;\n" +
                "$$";

        try (Connection connection = DbManager.createConnection();
             PreparedStatement insertOrderStatement = connection.prepareStatement(insertOrder);
             PreparedStatement insertProductStatement = connection.prepareStatement(insertProducts)) {

            connection.setAutoCommit(false);

            insertOrderStatement.setString(1, orderNumber);
            insertOrderStatement.execute();

            insertProductStatement.executeUpdate();

            connection.commit();

            return getInfo(orderNumber);

        } catch (SQLException e) {
            throw new OrderServiceException("Can't create and get order number", e.getCause());
        }
    }

    /**
     * Удаляет заказы по наименованию и количеству товара
     *
     * @param name  наименование товара
     * @param count количество товара
     * @return удаленные заказы
     */
    public List<String> getAndDelete(String name, int count) {

        List<String> orderNumbers = new ArrayList<>();

        String select = "select order_number from orders\n" +
                "join order_products op on orders.id = op.order_id join products p on p.id = op.product_id\n" +
                "where name = ? and product_count = ? and order_id = orders.id;";

        String delete = "delete from orders using products, order_products " +
                "where name = ? and product_count = ? and order_id = orders.id";

        try (Connection connection = DbManager.createConnection();
             PreparedStatement selectStatement = connection.prepareStatement(select);
             PreparedStatement deleteStatement = connection.prepareStatement(delete)) {

            connection.setAutoCommit(false);

            selectStatement.setString(1, name);
            selectStatement.setInt(2, count);
            ResultSet resultSetSelect = selectStatement.executeQuery();

            while (resultSetSelect.next()) {
                String orderNumber = resultSetSelect.getString("order_number");
                orderNumbers.add(orderNumber);
            }

            deleteStatement.setString(1, name);
            deleteStatement.setInt(2, count);
            deleteStatement.execute();

            connection.commit();

            return orderNumbers;

        } catch (SQLException e) {
            throw new OrderServiceException("Can't delete order number", e.getCause());
        }
    }
}
