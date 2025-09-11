package com.solvd.delivery.main;

import com.solvd.delivery.enums.OrderStatus;
import com.solvd.delivery.exceptions.UnknownOrderStatusException;
import com.solvd.delivery.enums.TicketPriority;
import com.solvd.delivery.enums.TicketStatus;
import com.solvd.delivery.models.*;
import com.solvd.delivery.dao.mysqlImpl.*;
import com.solvd.delivery.services.DeliveryService;
import com.solvd.delivery.services.interfaces.IDeliveryService;
import com.solvd.delivery.utils.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.solvd.delivery.services.interfaces.IPromotionService;
import com.solvd.delivery.services.PromotionService;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class App {
    private static final Logger logger = LogManager.getLogger(App.class);

    public App() {
        logger.info("🚀 Starting Delivery Service App...");
        testDAOs();
    }

    private void testDAOs() {
        testUserDAO();
        testAddressDAO();
        testProductDAO();
        testOrderDAO();
        testOrderItemDAO();
        testPaymentDAO();
        testPaymentTransactionDAO();
        testPromotionDAO();
        testOrderPromotionDAO();
        testShipmentDAO();
        testTrackingDAO();
        testInventoryDAO();
        testReviewDAO();
        testCourierDAO();
        testPromotionService();
        testDeliveryService();
        testOrderXMLDeserialization();
        testOrderXMLSerialization();
        testJAXBSerialization();
        testJAXBDeserialization();
        testJacksonSerialization();
        testJacksonDeserialization();

    }

    private void testUserDAO() {
        logger.info("===== Testing UserDAO =====");
        UserDAO dao = new UserDAO();
        dao.getAll().forEach(u -> logger.info(
                "User: ID={}, FirstName={}, LastName={}, Email={}, Phone={}",
                u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getPhoneNumber()
        ));
    }

    private void testAddressDAO() {
        logger.info("===== Testing AddressDAO =====");
        AddressDAO dao = new AddressDAO();
        dao.getAll().forEach(a -> logger.info(
                "Address: ID={}, Street={}, City={}, UserID={}",
                a.getId(), a.getStreet(), a.getCity(), a.getUserId()
        ));
    }

    private void testProductDAO() {
        logger.info("===== Testing ProductDAO =====");
        ProductDAO dao = new ProductDAO();
        dao.getAll().forEach(p -> logger.info(
                "Product: ID={}, Name={}, Price={}",
                p.getId(), p.getName(), p.getPrice()
        ));
    }

    private void testOrderDAO() {
        logger.info("===== Testing OrderDAO =====");
        OrderDAO dao = new OrderDAO();
        dao.getAll().forEach(o -> logger.info(
                "Order: ID={}, Date={}, StatusID={}, StatusLabel={}, TotalAmount={}, UserID={}, AddressID={}",
                o.getId(),
                o.getOrderDate(),
                o.getStatus().getId(),
                o.getStatus().getLabel(),
                o.getTotalAmount(),
                o.getUserId(),
                o.getAddressId()
        ));
    }

    private void testOrderItemDAO() {
        logger.info("===== Testing OrderItemDAO =====");
        OrderItemDAO dao = new OrderItemDAO();
        dao.getAll().forEach(oi -> logger.info(
                "OrderItem: ID={}, Quantity={}, UnitPrice={}, OrderID={}, ProductID={}",
                oi.getId(), oi.getQuantity(), oi.getUnitPrice(), oi.getOrderId(), oi.getProductId()
        ));
    }

    private void testPaymentDAO() {
        logger.info("===== Testing PaymentDAO =====");
        PaymentDAO dao = new PaymentDAO();
        dao.getAll().forEach(p -> logger.info(
                "Payment: ID={}, Amount={}, MethodID={}, MethodLabel={}, Date={}, OrderID={}",
                p.getId(),
                p.getAmount(),
                p.getPaymentMethod().getId(),
                p.getPaymentMethod().getLabel(),
                p.getPaymentDate(),
                p.getOrderId()
        ));
    }

    private void testPaymentTransactionDAO() {
        logger.info("===== Testing PaymentTransactionDAO =====");
        PaymentTransactionDAO dao = new PaymentTransactionDAO();
        dao.getAll().forEach(t -> logger.info(
                "Transaction: ID={}, Ref={}, Date={}, StatusID={}, StatusLabel={}, PaymentID={}",
                t.getId(),
                t.getTransactionReference(),
                t.getTransactionDate(),
                t.getStatus().getId(),
                t.getStatus().getLabel(),
                t.getPaymentId()
        ));
    }

    private void testPromotionDAO() {
        logger.info("===== Testing PromotionDAO =====");
        PromotionDAO dao = new PromotionDAO();
        dao.getAll().forEach(p -> logger.info(
                "Promotion: ID={}, Code={}, TypeID={}, TypeLabel={}, Value={}, Start={}, End={}",
                p.getId(),
                p.getCode(),
                p.getDiscountType().getId(),
                p.getDiscountType().getLabel(),
                p.getDiscountValue(),
                p.getStartDate(),
                p.getEndDate()
        ));
    }

    private void testOrderPromotionDAO() {
        logger.info("===== Testing OrderPromotionDAO =====");
        OrderPromotionDAO dao = new OrderPromotionDAO();

        // Create
        OrderPromotion newOP = new OrderPromotion(0, 1, 2);
        dao.insert(newOP);
        logger.info("Inserted OrderPromotion with ID={}", newOP.getId());

        // Read
        OrderPromotion fetched = dao.getById(newOP.getId());
        if (fetched != null) {
            logger.info("Fetched OrderPromotion: OrderID={}, PromotionID={}",
                    fetched.getOrderId(), fetched.getPromotionId());
        }

        // Update
        fetched.setPromotionId(1);
        dao.update(fetched);
        OrderPromotion updated = dao.getById(fetched.getId());
        logger.info("Updated OrderPromotion: OrderID={}, PromotionID={}",
                updated.getOrderId(), updated.getPromotionId());

        // Delete
        dao.removeById(newOP.getId());
        logger.info("Deleted OrderPromotion with ID={}", newOP.getId());

        // List all
        dao.getAll().forEach(op -> logger.info(
                "OrderPromotion: ID={}, OrderID={}, PromotionID={}",
                op.getId(), op.getOrderId(), op.getPromotionId()
        ));
    }

    private void testShipmentDAO() {
        logger.info("===== Testing ShipmentDAO =====");
        ShipmentDAO dao = new ShipmentDAO();
        dao.getAll().forEach(s -> logger.info(
                "Shipment: ID={}, ShipmentDate={}, DeliveryDate={}, StatusID={}, StatusLabel={}, OrderID={}",
                s.getId(),
                s.getShipmentDate(),
                s.getDeliveryDate(),
                s.getStatus().getId(),
                s.getStatus().getLabel(),
                s.getOrderId()
        ));
    }

    private void testSupportTicketDAO() {
        logger.info("===== Testing SupportTicketDAO =====");
        SupportTicketDAO dao = new SupportTicketDAO();

        // Create
        SupportTicket newTicket = new SupportTicket(0, "Login issue",
                TicketStatus.OPEN, TicketPriority.HIGH,
                LocalDateTime.now(), LocalDateTime.now(), 1);
        dao.insert(newTicket);
        logger.info("Inserted SupportTicket with ID={}", newTicket.getId());

        // Read
        SupportTicket fetched = dao.getById(newTicket.getId());
        if (fetched != null) {
            logger.info("Fetched Ticket: Subject={}, StatusID={}, StatusLabel={}, PriorityID={}, PriorityLabel={}, UserID={}",
                    fetched.getSubject(),
                    fetched.getStatus().getId(),
                    fetched.getStatus().getLabel(),
                    fetched.getPriority().getId(),
                    fetched.getPriority().getLabel(),
                    fetched.getUserId()
            );
        }

        // Update
        fetched.setStatus(TicketStatus.IN_PROGRESS);
        dao.update(fetched);
        SupportTicket updated = dao.getById(fetched.getId());
        logger.info("Updated Ticket: ID={}, StatusID={}, StatusLabel={}",
                updated.getId(),
                updated.getStatus().getId(),
                updated.getStatus().getLabel()
        );

        // Delete
        dao.removeById(newTicket.getId());
        logger.info("Deleted SupportTicket with ID={}", newTicket.getId());

        // List all
        dao.getAll().forEach(t -> logger.info(
                "Ticket: ID={}, Subject={}, Status={}, Priority={}, UserID={}",
                t.getId(), t.getSubject(), t.getStatus().getLabel(), t.getPriority().getLabel(), t.getUserId()
        ));
    }


    private void testTicketMessageDAO() {
        logger.info("===== Testing TicketMessageDAO =====");
        TicketMessageDAO dao = new TicketMessageDAO();

        TicketMessage message = new TicketMessage(0, "This is a test message", LocalDateTime.now(), 1);
        dao.insert(message);
        logger.info("Inserted TicketMessage with ID={}", message.getId());

        TicketMessage fetched = dao.getById(message.getId());
        if (fetched != null) {
            logger.info("Fetched TicketMessage: ID={}, Text={}, UserID={}",
                    fetched.getId(), fetched.getMessageText(), fetched.getUserId());
        }

        message.setMessageText("Updated message text");
        dao.update(message);
        logger.info("Updated TicketMessage: ID={}, Text={}", message.getId(), message.getMessageText());

        dao.removeById(message.getId());
        logger.info("Deleted TicketMessage with ID={}", message.getId());

        dao.getAll().forEach(m -> logger.info(
                "TicketMessage: ID={}, Text={}, UserID={}",
                m.getId(), m.getMessageText(), m.getUserId()
        ));
    }

    private void testWarehouseDAO() {
        logger.info("===== Testing WarehouseDAO =====");
        WarehouseDAO dao = new WarehouseDAO();

        Warehouse warehouse = new Warehouse(0, "Main Warehouse", "New York");
        dao.insert(warehouse);
        logger.info("Inserted Warehouse with ID={}", warehouse.getId());

        Warehouse fetched = dao.getById(warehouse.getId());
        if (fetched != null) {
            logger.info("Fetched Warehouse: ID={}, Name={}, Location={}", fetched.getId(), fetched.getName(), fetched.getLocation());
        }

        warehouse.setLocation("San Francisco");
        dao.update(warehouse);
        logger.info("Updated Warehouse: ID={}, Name={}, Location={}", warehouse.getId(), warehouse.getName(), warehouse.getLocation());

        dao.removeById(warehouse.getId());
        logger.info("Deleted Warehouse with ID={}", warehouse.getId());

        dao.getAll().forEach(w -> logger.info(
                "Warehouse: ID={}, Name={}, Location={}", w.getId(), w.getName(), w.getLocation()
        ));
    }



    private void testTrackingDAO() {
        logger.info("===== Testing TrackingDAO =====");
        TrackingDAO dao = new TrackingDAO();
        dao.getAll().forEach(t -> logger.info(
                "Tracking: ID={}, Number={}, StatusID={}, StatusLabel={}, LastUpdate={}, ShipmentID={}",
                t.getId(),
                t.getTrackingNumber(),
                t.getStatus().getId(),
                t.getStatus().getLabel(),
                t.getLastUpdate(),
                t.getShipmentId()
        ));
    }

    private void testInventoryDAO() {
        logger.info("===== Testing InventoryDAO =====");
        InventoryDAO dao = new InventoryDAO();

        // Create
        Inventory newInventory = new Inventory(0, 100, LocalDateTime.now(), 1, 1);
        dao.insert(newInventory);
        logger.info("Inserted Inventory with ID={}", newInventory.getId());

        // Read
        Inventory fetched = dao.getById(newInventory.getId());
        if (fetched != null) {
            logger.info("Fetched Inventory: Stock={}, WarehouseID={}, ProductID={}",
                    fetched.getStockQuantity(),
                    fetched.getWarehouseId(),
                    fetched.getProductId()
            );
        }

        // Update
        fetched.setStockQuantity(150);
        dao.update(fetched);
        Inventory updated = dao.getById(fetched.getId());
        logger.info("Updated Inventory: ID={}, Stock={}", updated.getId(), updated.getStockQuantity());

        // Delete
        dao.removeById(newInventory.getId());
        logger.info("Deleted Inventory with ID={}", newInventory.getId());

        // List all
        dao.getAll().forEach(i -> logger.info(
                "Inventory: ID={}, Stock={}, WarehouseID={}, ProductID={}",
                i.getId(), i.getStockQuantity(), i.getWarehouseId(), i.getProductId()
        ));
    }
    private void testReviewDAO() {
        logger.info("===== Testing ReviewDAO =====");
        ReviewDAO dao = new ReviewDAO();

        // Create
        Review newReview = new Review(0, 5, "Excellent delivery!", LocalDateTime.now(), 1, 1);
        dao.insert(newReview);
        logger.info("Inserted Review with ID={}", newReview.getId());

        // Read
        Review fetched = dao.getById(newReview.getId());
        if (fetched != null) {
            logger.info("Fetched Review: Rating={}, Comment={}, UserID={}, ShipmentID={}",
                    fetched.getRating(),
                    fetched.getComment(),
                    fetched.getUserId(),
                    fetched.getShipmentId()
            );
        }

        // Update
        fetched.setComment("Very fast delivery!");
        dao.update(fetched);
        Review updated = dao.getById(fetched.getId());
        logger.info("Updated Review: ID={}, Comment={}", updated.getId(), updated.getComment());

        // Delete
        dao.removeById(newReview.getId());
        logger.info("Deleted Review with ID={}", newReview.getId());

        // List all
        dao.getAll().forEach(r -> logger.info(
                "Review: ID={}, Rating={}, Comment={}, UserID={}, ShipmentID={}",
                r.getId(), r.getRating(), r.getComment(), r.getUserId(), r.getShipmentId()
        ));
    }

    private void testCourierDAO() {
        System.out.println("===== Testing CourierDAO =====");

        CourierDAO courierDAO = new CourierDAO();

        // Insert
        Courier newCourier = new Courier();
        newCourier.setName("SpeedyDelivery");
        newCourier.setContactNumber("+3333333333");
        newCourier.setEmail("contact@speedydelivery.com");
        newCourier.setShipmentId(1);
        newCourier.setUserId(1);

        courierDAO.insert(newCourier);
        System.out.println("Inserted Courier with ID=" + newCourier.getId());

        // Get by ID
        Courier fetched = courierDAO.getById(newCourier.getId());
        System.out.println("Fetched Courier: Name=" + fetched.getName() + ", Email=" + fetched.getEmail());

        // Update
        fetched.setName("SpeedyDelivery Updated");
        courierDAO.update(fetched);
        System.out.println("Updated Courier: Name=" + courierDAO.getById(fetched.getId()).getName());

        // Get all
        List<Courier> allCouriers = courierDAO.getAll();
        for (Courier c : allCouriers) {
            System.out.println("Courier: ID=" + c.getId() + ", Name=" + c.getName());
        }

        // Delete
        courierDAO.removeById(newCourier.getId());
        System.out.println("Deleted Courier with ID=" + newCourier.getId());
    }

    private void testPromotionService() {
        logger.info("===== Testing PromotionService =====");

        UserDAO userDAO = new UserDAO();
        PromotionDAO promotionDAO = new PromotionDAO();

        // Example: pick first user and promotion from DB
        User user = userDAO.getAll().stream().findFirst().orElse(null);
        Promotion promotion = promotionDAO.getAll().stream().findFirst().orElse(null);

        if (user == null || promotion == null) {
            logger.warn("No users or promotions found in DB. Skipping PromotionService test.");
            return;
        }

        // Example: user has completed 2 deliveries
        int deliveryCount = 2;
        String promoCode = promotion.getCode(); // Use the promotion code from DB

        IPromotionService promoService = new PromotionService(promoCode, deliveryCount, user, promotion);
        double discount = promoService.calculateDiscount();

        logger.info("User: {} {}", user.getFirstName(), user.getLastName());
        logger.info("Delivery Count: {}", deliveryCount);
        logger.info("Promo Code: {}", promoCode);
        logger.info("Calculated Discount: {}%", discount * 100);
    }

    private void testDeliveryService() {
        logger.info("===== Testing DeliveryService =====");

        ShipmentDAO shipmentDAO = new ShipmentDAO();
        Shipment shipment = shipmentDAO.getAll().stream().findFirst().orElse(null);

        if (shipment == null) {
            logger.warn("No shipments found in DB. Skipping DeliveryService test.");
            return;
        }

        IDeliveryService deliveryService = new DeliveryService();
        double hours = deliveryService.calculateEstimatedDeliveryTime(shipment);
        boolean delayed = deliveryService.isDelayed(shipment);

        logger.info("Shipment ID: {}", shipment.getId());
        logger.info("Estimated Delivery Time: {} hours", hours);
        logger.info("Is Delayed: {}", delayed);
    }

    // ===== New Methods for Order XML =====

    private void testOrderXMLDeserialization() {
        logger.info("===== Testing Order XML Deserialization (SAX) =====");

        try {
            List<Order> orders = OrderSAXParser.parseOrders("orders.xml"); // uses SAX parser with logger

            logger.info("Deserialized Orders from XML:");
            orders.forEach(o -> logger.info(
                    "Order ID={}, Date={}, Status={}, Total={}, UserID={}, AddressID={}",
                    o.getId(), o.getOrderDate(), o.getStatus().getLabel(),
                    o.getTotalAmount(), o.getUserId(), o.getAddressId()
            ));

        } catch (Exception e) {
            logger.error("Error deserializing orders.xml", e);
        }
    }



    private void testOrderXMLSerialization() {
        logger.info("===== Testing Order XML Serialization =====");

        // Fetch orders from DB
        OrderDAO orderDAO = new OrderDAO();
        List<Order> orders = orderDAO.getAll();

        String outputFile = "src/main/resources/orders_output.xml";

        // Just call OrderXMLWriter, which already logs success/error
        OrderXMLWriter.writeOrders(orders, outputFile);
    }

    // ===== JAXB =====

    private void testJAXBSerialization() {
        logger.info("===== Testing JAXB Serialization =====");

        // Example: create some sample orders
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(101, LocalDateTime.now(), OrderStatus.PENDING, 120.50, 1, 10));
        orders.add(new Order(102, LocalDateTime.now().minusDays(1), OrderStatus.SHIPPED, 89.99, 2, 11));

        OrdersWrapper wrapper = new OrdersWrapper(orders);

        String outputFile = "src/main/resources/orders_jaxb.xml";
        OrderXMLWriterJAXB.writeOrdersToFile(wrapper, outputFile);

        logger.info("Orders written to {}", outputFile);
    }

    private void testJAXBDeserialization() {
        logger.info("===== Testing JAXB Deserialization =====");

        String inputFile = "src/main/resources/orders_jaxb.xml";
        OrdersWrapper wrapper = OrderXMLReaderJAXB.readOrdersFromFile(inputFile);

        if (wrapper != null && wrapper.getOrders() != null) {
            wrapper.getOrders().forEach(order ->
                    logger.info("Deserialized Order: {}", order));
        } else {
            logger.warn("No orders found in {}", inputFile);
        }
    }


    private void testJacksonSerialization() {
        logger.info("===== Testing Jackson JSON Serialization =====");

        try {
            // Example: create some sample orders
            List<Order> orders = new ArrayList<>();
            orders.add(new Order(201, LocalDateTime.now(), OrderStatus.PENDING, 150.75, 1, 10));
            orders.add(new Order(202, LocalDateTime.now().minusDays(2), OrderStatus.SHIPPED, 99.99, 2, 11));

            String outputFile = "src/main/resources/orders.json";
            OrderJSONWriter writer = new OrderJSONWriter();
            writer.writeOrders(new File(outputFile), orders);

            logger.info("Orders successfully written to JSON: {}", outputFile);

        } catch (Exception e) {
            logger.error("Error during Jackson JSON serialization", e);
        }
    }

    private void testJacksonDeserialization() {
        logger.info("===== Testing Jackson JSON Deserialization =====");

        try {
            String inputFile = "src/main/resources/orders.json";
            OrderJSONReader reader = new OrderJSONReader();
            List<Order> orders = reader.readOrders(new File(inputFile));

            if (orders != null && !orders.isEmpty()) {
                orders.forEach(order -> logger.info(
                        "Deserialized Order: ID={}, Date={}, Status={}, Total={}, UserID={}, AddressID={}",
                        order.getId(),
                        order.getOrderDate(),
                        order.getStatus().getLabel(),
                        order.getTotalAmount(),
                        order.getUserId(),
                        order.getAddressId()
                ));
            } else {
                logger.warn("No orders found in JSON file: {}", inputFile);
            }

        } catch (Exception e) {
            logger.error("Error during Jackson JSON deserialization", e);
        }
    }



    public static void main(String[] args) {
        new App();
    }
}