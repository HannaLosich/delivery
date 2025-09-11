package com.solvd.delivery.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.solvd.delivery.models.Order;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class OrderJSONWriter {

    private final ObjectMapper mapper;

    public OrderJSONWriter() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // Support for LocalDateTime
    }

    // Serialize a single Order to JSON file
    public void writeOrder(File file, Order order) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, order);
    }

    // Serialize a list of Orders to JSON file
    public void writeOrders(File file, List<Order> orders) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, orders);
    }

    // Serialize an Order to JSON string
    public String writeOrderToString(Order order) throws IOException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(order);
    }

    // Serialize a list of Orders to JSON string
    public String writeOrdersToString(List<Order> orders) throws IOException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(orders);
    }
}
