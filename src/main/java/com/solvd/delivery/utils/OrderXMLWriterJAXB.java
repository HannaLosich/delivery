package com.solvd.delivery.utils;

import com.solvd.delivery.models.OrdersWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

public class OrderXMLWriterJAXB {

    public static void writeOrdersToFile(OrdersWrapper wrapper, String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(OrdersWrapper.class);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(wrapper, new File(filePath));

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
