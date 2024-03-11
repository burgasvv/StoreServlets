package com.burgas.storeservlets.service;


import com.burgas.storeservlets.entity.OrderProduct;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class OrderServiceTest {

    @Test
    public void getInfoSuccess() {

        OrderService orderService = new OrderService();
        String orderNumber = "A25";
        List<OrderProduct> info = orderService.getInfo(orderNumber);
        Assertions.assertEquals(2, info.size());
        Assertions.assertEquals("Apples", info.get(0).getProduct().getName());
        Assertions.assertEquals("Potatoes", info.get(1).getProduct().getName());
        Assertions.assertEquals(150.5, info.get(0).getProduct().getPrice());
        Assertions.assertEquals(75.34, info.get(1).getProduct().getPrice());
        Assertions.assertEquals(5, info.get(0).getProductCount());
        Assertions.assertEquals(12, info.get(1).getProductCount());
    }
}
