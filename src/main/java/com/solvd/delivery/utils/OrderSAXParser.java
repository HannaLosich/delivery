package com.solvd.delivery.utils;

import com.solvd.delivery.enums.OrderStatus;
import com.solvd.delivery.models.Order;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderSAXParser {

    public static List<Order> parseOrders(String xmlFile) {
        List<Order> orders = new ArrayList<>();

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                Order order = null;
                StringBuilder data = null;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if ("order".equals(qName)) {
                        order = new Order();
                    }
                    data = new StringBuilder();
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    data.append(ch, start, length);
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (order != null) {
                        switch (qName) {
                            case "id" -> order.setId(Long.parseLong(data.toString()));
                            case "orderDate" -> order.setOrderDate(LocalDateTime.parse(data.toString()));
                            case "status" -> order.setStatus(OrderStatus.valueOf(data.toString()));
                            case "totalAmount" -> order.setTotalAmount(Double.parseDouble(data.toString()));
                            case "userId" -> order.setUserId(Long.parseLong(data.toString()));
                            case "addressId" -> order.setAddressId(Long.parseLong(data.toString()));
                            case "order" -> orders.add(order);
                        }
                    }
                }
            };

            InputStream is = OrderSAXParser.class.getClassLoader().getResourceAsStream(xmlFile);
            if (is != null) parser.parse(is, handler);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }
}
