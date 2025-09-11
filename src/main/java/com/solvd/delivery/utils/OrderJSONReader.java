package com.solvd.delivery.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.solvd.delivery.models.Order;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class OrderJSONReader {

    private final ObjectMapper mapper;

    public OrderJSONReader() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // Support for LocalDateTime
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // Ignore unknown fields
    }

    // Deserialize a single Order from JSON file
    public Order readOrder(File file) throws IOException {
        return mapper.readValue(file, Order.class);
    }

    // Deserialize a list of Orders from JSON file using TypeReference
    public List<Order> readOrders(File file) throws IOException {
        return mapper.readValue(file, new TypeReference<List<Order>>() {});
    }

    // Deserialize a single Order from JSON string
    public Order readOrderFromString(String json) throws IOException {
        return mapper.readValue(json, Order.class);
    }

    // Deserialize a list of Orders from JSON string
    public List<Order> readOrdersFromString(String json) throws IOException {
        return mapper.readValue(json, new TypeReference<List<Order>>() {});
    }
}
