package com.burgas.storeservlets.entity;

import java.util.Objects;

/**
 * Сущность, которая описывает таблицу заказов в базе данных
 * orderNumber - номер заказа
 * date - дата формирования заказа
 */
public class Order extends Entity{

    private String orderNumber;
    private String date;

    public Order(int id, String orderNumber, String date) {
        super(id);
        this.orderNumber = orderNumber;
        this.date = date;
    }

    public Order(String orderNumber, String date) {
        this.orderNumber = orderNumber;
        this.date = date;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderNumber, order.orderNumber) && Objects.equals(date, order.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber, date);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNumber='" + orderNumber + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
