package com.burgas.storeservlets.entity;

import java.util.Objects;

public class Order {

    private int id;
    private String orderNumber;
    private int count;
    private String date;

    public Order(int id, String orderNumber, String date) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.date = date;
    }

    public Order(String orderNumber, int count, String date) {
        this.orderNumber = orderNumber;
        this.count = count;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Order order = (Order) object;
        return id == order.id && Objects.equals(orderNumber, order.orderNumber) && Objects.equals(date, order.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderNumber, date);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNumber='" + orderNumber + '\'' +
                ", count=" + count +
                ", date='" + date + '\'' +
                '}';
    }
}
