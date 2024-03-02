package service;

import entity.Product;
import entity.OrderProduct;
import entity.Order;
import manager.DbManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StoreService {

    public List<OrderProduct> getInfoByOrderNumber(final String orderNumber) {

        List<OrderProduct>goods = new ArrayList<>();

        try (Connection connection = DbManager.createConnection()){

            String query = "select name,description,price,product_count,date\n" +
                    "from orders\n" +
                    "         join order_products op on orders.id = op.order_id\n" +
                    "         join products p on p.id = op.product_id\n" +
                    "where order_number = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,orderNumber);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int productCount = resultSet.getInt("product_count");
                String date = resultSet.getString("date");

                goods.add(
                        new OrderProduct(
                                new Product(name,description,price),
                                new Order(orderNumber,productCount,date)
                        )
                );
            }
            return goods;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getOrderNumberByCondition(final int price, final int productCount) {

        List<String>orderNumbers = new ArrayList<>();

        try (Connection connection = DbManager.createConnection()){

            String query = "select orders.order_number from orders\n" +
                    "join order_products op on orders.id = op.order_id join products p on p.id = op.product_id\n" +
                    "group by orders.order_number having sum(product_count * price) < ? and count(product_id) = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, price);
            statement.setInt(2, productCount);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String orderNumber = resultSet.getString("order_number");
                orderNumbers.add(orderNumber);
            }
            return orderNumbers;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getOrderNumberByProductName(final String productName) {

        List<String>orderNumbers = new ArrayList<>();

        try (Connection connection = DbManager.createConnection()){

            String query = "select orders.order_number\n" +
                    "from orders\n" +
                    "         join order_products op on orders.id = op.order_id\n" +
                    "         join products p on p.id = op.product_id\n" +
                    "where name = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,productName);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String orderNumber = resultSet.getString("order_number");
                orderNumbers.add(orderNumber);
            }
            return orderNumbers;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getOrderNumberByProductNameAndDate(final String productName) {

        List<String>orderNumbers = new ArrayList<>();

        try (Connection connection = DbManager.createConnection()){

            String query = "select orders.order_number\n" +
                    "from orders\n" +
                    "         join order_products op on orders.id = op.order_id\n" +
                    "         join products p on p.id = op.product_id\n" +
                    "where name != ? and (select date::timestamp::date) = now()::timestamp::date\n" +
                    "group by orders.order_number";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, productName);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String orderNumber = resultSet.getString("order_number");
                orderNumbers.add(orderNumber);
            }
            return orderNumbers;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<OrderProduct> createAndGetTodaySalesOrder(final String orderNumber) {

        try (Connection connection = DbManager.createConnection()){

            String insetOrder = "insert into orders(order_number, date) VALUES (?,now())";

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

            PreparedStatement insertOrderStatement = connection.prepareStatement(insetOrder);
            insertOrderStatement.setString(1,orderNumber);
            insertOrderStatement.execute();

            PreparedStatement insertProductStatement = connection.prepareStatement(insertProducts);
            insertProductStatement.executeUpdate();

            return getInfoByOrderNumber(orderNumber);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getOrderNumbersAndDelete(final String name, final int count) {

        List<String> orderNumbers = new ArrayList<>();

        try (Connection connection = DbManager.createConnection()){

            String select = "select order_number from orders\n" +
                    "join order_products op on orders.id = op.order_id join products p on p.id = op.product_id\n" +
                    "where name = ? and product_count = ? and order_id = orders.id;";

            String delete = "delete from orders using products, order_products " +
                    "where name = ? and product_count = ? and order_id = orders.id";

            PreparedStatement selectStatement = connection.prepareStatement(select);
            selectStatement.setString(1,name);
            selectStatement.setInt(2,count);
            ResultSet resultSetSelect = selectStatement.executeQuery();

            while (resultSetSelect.next()) {
                String orderNumber = resultSetSelect.getString("order_number");
                orderNumbers.add(orderNumber);
            }

            PreparedStatement deleteStatement = connection.prepareStatement(delete);
            deleteStatement.setString(1,name);
            deleteStatement.setInt(2,count);
            deleteStatement.execute();

            return orderNumbers;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
