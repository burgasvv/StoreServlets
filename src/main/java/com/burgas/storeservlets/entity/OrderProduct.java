package com.burgas.storeservlets.entity;

import java.util.Objects;

public class OrderProduct {

    private Product product;
    private Order order;

    public OrderProduct(Product product, Order order) {
        this.product = product;
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getStore() {
        return order;
    }

    public void setStore(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        OrderProduct that = (OrderProduct) object;
        return Objects.equals(product, that.product) && Objects.equals(order, that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, order);
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "product=" + product +
                ", order=" + order +
                '}';
    }
}
