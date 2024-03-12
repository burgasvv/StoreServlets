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
                                new OrderProduct(new Product("Apples","For eat",150.5),
                                        new Order(orderNumber,"2024-03-11 16:46:40.333745"),
                                        5
                                        ),
                                new OrderProduct(
                                        new Product("Potatoes","For eat",75.34),
                                        new Order(orderNumber,"2024-03-11 16:46:40.333745"),
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

        Assertions.assertEquals(2, info.size());
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
}
