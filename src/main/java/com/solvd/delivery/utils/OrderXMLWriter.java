package com.solvd.delivery.utils;

import com.solvd.delivery.models.Order;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class OrderXMLWriter {

    public static void writeOrders(List<Order> orders, String outputFile) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.newDocument();
            Element root = doc.createElement("orders");
            doc.appendChild(root);

            for (Order o : orders) {
                Element orderElem = doc.createElement("order");

                Element id = doc.createElement("id");
                id.appendChild(doc.createTextNode(String.valueOf(o.getId())));
                orderElem.appendChild(id);

                Element date = doc.createElement("orderDate");
                date.appendChild(doc.createTextNode(o.getOrderDate().toString()));
                orderElem.appendChild(date);

                Element status = doc.createElement("status");
                status.appendChild(doc.createTextNode(o.getStatus().name()));
                orderElem.appendChild(status);

                Element amount = doc.createElement("totalAmount");
                amount.appendChild(doc.createTextNode(String.valueOf(o.getTotalAmount())));
                orderElem.appendChild(amount);

                Element userId = doc.createElement("userId");
                userId.appendChild(doc.createTextNode(String.valueOf(o.getUserId())));
                orderElem.appendChild(userId);

                Element addressId = doc.createElement("addressId");
                addressId.appendChild(doc.createTextNode(String.valueOf(o.getAddressId())));
                orderElem.appendChild(addressId);

                root.appendChild(orderElem);
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(new File(outputFile)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
