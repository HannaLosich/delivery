package com.solvd.delivery.utils;

import com.solvd.delivery.enums.OrderStatus;
import com.solvd.delivery.exceptions.UnknownOrderStatusException;
import com.solvd.delivery.models.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(OrderSAXParser.class);

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
                        String idAttr = attributes.getValue("id");
                        if (idAttr != null) {
                            try {
                                order.setId(Long.parseLong(idAttr));
                            } catch (NumberFormatException ex) {
                                logger.warn("Invalid order id attribute '{}', defaulting to 0", idAttr);
                                order.setId(0L);
                            }
                        }
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
                        String value = data.toString().trim();
                        try {
                            switch (qName) {
                                case "id" -> order.setId(Long.parseLong(value));
                                case "orderDate" -> order.setOrderDate(LocalDateTime.parse(value));
                                case "status" -> {
                                    try {
                                        order.setStatus(OrderStatus.fromLabel(value));
                                    } catch (UnknownOrderStatusException e) {
                                        logger.warn("Unknown OrderStatus '{}', defaulting to PENDING", value);
                                        order.setStatus(OrderStatus.PENDING);
                                    }
                                }
                                case "totalAmount" -> order.setTotalAmount(Double.parseDouble(value));
                                case "userId" -> order.setUserId(Long.parseLong(value));
                                case "addressId" -> order.setAddressId(Long.parseLong(value));
                                case "order" -> orders.add(order);
                            }
                        } catch (Exception ex) {
                            logger.error("Error parsing element '{}' with value '{}': {}", qName, value, ex.getMessage(), ex);
                        }
                    }
                }
            };

            InputStream is = OrderSAXParser.class.getClassLoader().getResourceAsStream(xmlFile);
            if (is != null) {
                parser.parse(is, handler);
                logger.info("Successfully parsed orders from '{}'", xmlFile);
            } else {
                logger.warn("XML file '{}' not found in classpath", xmlFile);
            }

        } catch (Exception e) {
            logger.error("Error parsing XML file '{}': {}", xmlFile, e.getMessage(), e);
        }

        return orders;
    }
}
