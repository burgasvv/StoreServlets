package com.burgas.storeservlets.service;


import com.burgas.storeservlets.entity.Order;
import com.burgas.storeservlets.entity.OrderProduct;
import com.burgas.storeservlets.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
                info,
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
                )
        );
    }
}
