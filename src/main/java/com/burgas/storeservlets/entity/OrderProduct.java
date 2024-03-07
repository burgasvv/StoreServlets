package com.burgas.storeservlets.entity;

import java.util.Objects;

public class OrderProduct extends Entity {

    private Product product;
    private Order order;
    private int productCount;

    public OrderProduct(Product product, Order order, int productCount) {
        this.product = product;
        this.order = order;
        this.productCount = productCount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
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
                "order=" + order +
                ", product=" + product +
                ", productCount=" + productCount +
                '}';
    }
}
