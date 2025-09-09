package com.solvd.delivery.utils;

import com.solvd.delivery.models.OrdersWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class OrderXMLReaderJAXB {

    private static final Logger logger = LogManager.getLogger(OrderXMLReaderJAXB.class);

    public static OrdersWrapper readOrdersFromFile(String filePath) {
        try {
            // Create JAXB context for OrdersWrapper
            JAXBContext context = JAXBContext.newInstance(OrdersWrapper.class);

            // Create unmarshaller
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // Unmarshal XML file into OrdersWrapper object
            return (OrdersWrapper) unmarshaller.unmarshal(new File(filePath));

        } catch (JAXBException e) {
            logger.error("Failed to unmarshal XML file: {}", filePath, e);
            return null;
        }
    }
}
