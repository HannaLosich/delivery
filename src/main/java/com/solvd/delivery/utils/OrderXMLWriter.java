package com.solvd.delivery.utils;

import com.solvd.delivery.models.Order;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class OrderXMLWriter {

    public static void writeOrders(List<Order> orders, String outputFile) {
        StringBuilder sb = new StringBuilder();

        // XML declaration
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<orders>\n");

        // Loop through orders
        for (Order o : orders) {
            sb.append("  <order>\n");
            sb.append("    <id>").append(o.getId()).append("</id>\n");
            sb.append("    <orderDate>").append(o.getOrderDate()).append("</orderDate>\n");
            sb.append("    <status>").append(o.getStatus().name()).append("</status>\n");
            sb.append("    <totalAmount>").append(o.getTotalAmount()).append("</totalAmount>\n");
            sb.append("    <userId>").append(o.getUserId()).append("</userId>\n");
            sb.append("    <addressId>").append(o.getAddressId()).append("</addressId>\n");
            sb.append("  </order>\n");
        }

        sb.append("</orders>");

        try {
            FileUtils.writeStringToFile(new File(outputFile), sb.toString(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
