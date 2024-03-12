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
}
