package com.solvd.delivery.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "orders")
public class OrdersWrapper {

    private List<Order> orders;

    // Default no-arg constructor required by JAXB
    public OrdersWrapper() {}

    public OrdersWrapper(List<Order> orders) {
        this.orders = orders;
    }

    @XmlElement(name = "order")
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
