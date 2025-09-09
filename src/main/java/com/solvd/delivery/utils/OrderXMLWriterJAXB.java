package com.solvd.delivery.utils;

import com.solvd.delivery.models.OrdersWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

public class OrderXMLWriterJAXB {

    private static final Logger logger = LogManager.getLogger(OrderXMLWriterJAXB.class);

    public static void writeOrdersToFile(OrdersWrapper wrapper, String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(OrdersWrapper.class);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(wrapper, new File(filePath));

            logger.info("Successfully wrote orders to XML file: {}", filePath);

        } catch (JAXBException e) {
            logger.error("Failed to marshal OrdersWrapper to file: {}", filePath, e);
        }
    }
}
