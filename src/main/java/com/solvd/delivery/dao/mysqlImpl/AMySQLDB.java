package com.solvd.delivery.dao.mysqlImpl;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AMySQLDB {

    private static final Logger logger = LogManager.getLogger(AMySQLDB.class);

    private static final String URL =
            "jdbc:mysql://localhost:3306/delivery_service?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "newpassword123";

    private static final BasicDataSource dataSource = new BasicDataSource();

    static {
        dataSource.setUrl(URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASSWORD);

        dataSource.setInitialSize(5);
        dataSource.setMaxTotal(20);
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxWaitMillis(10000);

        try (Connection conn = dataSource.getConnection()) {
            logger.info("DBCP2 connection pool initialized successfully.");
        } catch (SQLException e) {
            logger.error("Failed to initialize DBCP2 connection pool", e);
        }
    }

    protected Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void shutdownPool() {
        try {
            dataSource.close();
            logger.info("DBCP2 connection pool closed successfully.");
        } catch (SQLException e) {
            logger.error("Failed to close DBCP2 pool", e);
        }
    }
}
