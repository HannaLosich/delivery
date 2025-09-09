package com.solvd.delivery.utils;

import com.solvd.delivery.models.OrdersWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class OrderXMLReader {

    public static OrdersWrapper readOrdersFromFile(String filePath) {
        try {
            // Create JAXB context for OrdersWrapper
            JAXBContext context = JAXBContext.newInstance(OrdersWrapper.class);

            // Create unmarshaller
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // Unmarshal XML file into OrdersWrapper object
            return (OrdersWrapper) unmarshaller.unmarshal(new File(filePath));

        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}
