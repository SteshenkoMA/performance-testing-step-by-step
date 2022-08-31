package com.eshop.database;

import com.eshop.utils.ConfigLoader;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;

/**
 * @author https://github.com/steshenkoma
 */
public final class DatabaseConnector {

    private static final Logger logger = LogManager.getLogger(DatabaseConnector.class);

    private static DatabaseConnector instance;
    private final BasicDataSource ds;

    public DatabaseConnector() {
        String dbUrl = ConfigLoader.properties.getProperty("db");

        ds = new BasicDataSource();
        ds.setDriverClassName("org.sqlite.JDBC");
        ds.setInitialSize(1);
        ds.setMaxIdle(30);
        ds.setMinIdle(1);
        ds.setMaxTotal(30);
        ds.setMinEvictableIdleTimeMillis(10 * 60 * 1000);
        ds.setEnableAutoCommitOnReturn(true);
        ds.setConnectionProperties("foreign_keys=true");
        ds.setUrl("jdbc:sqlite:" + dbUrl);
        logger.info("Database connection was successfully established");
    }

    public static DatabaseConnector getInstance() {
        DatabaseConnector localInstance = instance;
        if (localInstance == null) {
            synchronized (DatabaseConnector.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DatabaseConnector();
                }
            }
        }
        return localInstance;
    }

    public Connection getConnection() {
        try {
            Connection c = ds.getConnection();
            return c;
        } catch (Exception e) {
            logger.fatal("Error establishing a database connection", e);
            return null;
        }
    }

}
