package com.burgas.storeservlets.service;

import com.burgas.storeservlets.entity.Order;
import com.burgas.storeservlets.entity.OrderProduct;
import com.burgas.storeservlets.entity.Product;
import com.burgas.storeservlets.manager.DbManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceTest {

    @Test
    public void getInfoSuccess() {

        OrderService orderService = new OrderService();
        String orderNumber = "A25";
        List<OrderProduct> info = orderService.getInfo(orderNumber);

        Assertions.assertEquals(2, info.size());
        Assertions.assertIterableEquals(
                new ArrayList<>(
                        List.of(
                                new OrderProduct(
                                        new Product("Apples","For eat",150.5),
                                        new Order(orderNumber, info.get(0).getOrder().getDate()),
                                        5
                                        ),
                                new OrderProduct(
                                        new Product("Potatoes","For eat",75.34),
                                        new Order(orderNumber, info.get(1).getOrder().getDate()),
                                        12
                                        )
                                )
                        ),
                info
        );
    }

    @Test
    public void getInfoAssertionException() {

        OrderService orderService = new OrderService();
        String orderNumber = "A25";
        List<OrderProduct> info = orderService.getInfo(orderNumber);

        Assertions.assertNotEquals(3, info.size());
        Assertions.assertThrows(
                AssertionFailedError.class,
                () -> Assertions.assertEquals("Milk", info.get(0).getProduct().getName()));
    }

    @Test
    public void getInfoSqlException() {

        String query = "select name,description,price,product_count,date\n" +
                "from orders\n" +
                "         join order_products op on orders.id = op.order_id\n" +
                "         join products p on p.id = op.product_id\n" +
                "where order_number = ?";

        try (Connection connection = DbManager.createConnection();
             PreparedStatement statement = connection.prepareStatement(query)){

            Assertions.assertThrows(SQLException.class, statement::execute);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getBySuccess() {

        OrderService orderService = new OrderService();
        int price = 3000;
        int productCount = 3;
        List<String> serviceBy = orderService.getBy(price, productCount);

        Assertions.assertEquals(2, serviceBy.size());
        Assertions.assertIterableEquals(
                new ArrayList<>(List.of("B40", "B44")),
                serviceBy
        );
    }

    @Test
    public void getByAssertionException() {

        OrderService orderService = new OrderService();
        int price = 3000;
        int productCount = 3;
        List<String> serviceBy = orderService.getBy(price, productCount);

        Assertions.assertNotEquals(3, serviceBy.size());
        Assertions.assertThrows(
                AssertionFailedError.class,
                () -> Assertions.assertEquals("A25", serviceBy.get(0))
        );
    }

    @Test
    public void getBySqlException() {

        String query = "select orders.order_number from orders\n" +
                "join order_products op on orders.id = op.order_id join products p on p.id = op.product_id\n" +
                "group by orders.order_number having sum(product_count * price) < ? and count(product_id) = ?";

        try (Connection connection = DbManager.createConnection();
             PreparedStatement statement = connection.prepareStatement(query)){

            Assertions.assertThrows(SQLException.class, statement::execute);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getByProductNameSuccess() {

        OrderService orderService = new OrderService();
        String productName = "Apples";
        List<String> byProductName = orderService.getByProductName(productName);

        Assertions.assertEquals(4, byProductName.size());
        Assertions.assertIterableEquals(
                new ArrayList<>(List.of("C85", "A50", "A25", "C60")),
                byProductName
        );
    }

    @Test
    public void getByProductNameAssertionException() {

        OrderService orderService = new OrderService();
        String productName = "Apples";
        List<String> byProductName = orderService.getByProductName(productName);

        Assertions.assertNotEquals(3, byProductName.size());
        Assertions.assertThrows(
                AssertionFailedError.class,
                () -> Assertions.assertEquals("A25", byProductName.get(0))
        );
    }

    @Test
    public void getByProductNameSqlException() {

        String query = "select orders.order_number\n" +
                "from orders\n" +
                "         join order_products op on orders.id = op.order_id\n" +
                "         join products p on p.id = op.product_id\n" +
                "where name = ?";

        try (Connection connection = DbManager.createConnection();
             PreparedStatement statement = connection.prepareStatement(query)){

            Assertions.assertThrows(SQLException.class, statement::execute);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getByProductNameAndDateSuccess() {

        OrderService orderService = new OrderService();
        String productName = "Lemonade";
        List<String> byProductNameAndDate = orderService.getByProductNameAndDate(productName);

        Assertions.assertEquals(4, byProductNameAndDate.size());
        Assertions.assertIterableEquals(
                new ArrayList<>(List.of("A50", "B40", "C60", "C85")),
                byProductNameAndDate
        );
    }

    @Test
    public void getByProductNameAndDateAssertionException() {

        OrderService orderService = new OrderService();
        String productName = "Lemonade";
        List<String> byProductNameAndDate = orderService.getByProductNameAndDate(productName);

        Assertions.assertNotEquals(10, byProductNameAndDate.size());
        Assertions.assertThrows(
                AssertionFailedError.class,
                () -> Assertions.assertEquals("A25", byProductNameAndDate.get(0))
        );
    }

    @Test
    public void getByProductNameAndDateSqlException() {

        String query = "select orders.order_number from orders\n" +
                "join order_products op on orders.id = op.order_id\n" +
                "join products p on p.id = op.product_id\n" +
                "where name = ? and (select date::date) = now()::timestamp::date\n" +
                "group by orders.order_number";

        try (Connection connection = DbManager.createConnection();
             PreparedStatement statement = connection.prepareStatement(query)){

            Assertions.assertThrows(SQLException.class, statement::execute);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createAndGetSqlException() {

        String insertOrder = "insert into orders(order_number, date) VALUES (?,now())";

        String insertProducts = "do $$\n" +
                "    declare\n" +
                "        count integer := 1;\n" +
                "        idOrder integer = (select orders.id from orders where id = ?);\n" +
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
             PreparedStatement insertProductsStatement = connection.prepareStatement(insertProducts)){


            Assertions.assertThrows(SQLException.class, insertOrderStatement::execute);
            Assertions.assertThrows(SQLException.class, insertProductsStatement::execute);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getAndDeleteSqlException() {

        String select = "select order_number from orders\n" +
                "join order_products op on orders.id = op.order_id join products p on p.id = op.product_id\n" +
                "where name = ? and product_count = ? and order_id = orders.id;";

        String delete = "delete from orders using products, order_products " +
                "where name = ? and product_count = ? and order_id = orders.id";

        try (Connection connection = DbManager.createConnection();
             PreparedStatement selectStatement = connection.prepareStatement(select);
             PreparedStatement deleteStatement = connection.prepareStatement(delete)){

            Assertions.assertThrows(SQLException.class, selectStatement::execute);
            Assertions.assertThrows(SQLException.class, deleteStatement::execute);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
