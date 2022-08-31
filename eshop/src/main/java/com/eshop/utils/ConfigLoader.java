package com.eshop.utils;

import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author https://github.com/steshenkoma
 */
public class ConfigLoader {

    private static org.apache.logging.log4j.Logger logger = LogManager.getLogger(ConfigLoader.class);

    public static Properties properties;

    public ConfigLoader(String confPath) {
        this.properties = loadProperties(confPath);
    }

    private Properties loadProperties(String confPath) {

        File file = new File(confPath);
        properties = new Properties();

        try (FileInputStream fis = new FileInputStream(file)) {
            properties.load(fis);
            logger.info("Config file was successfully loaded");
        } catch (IOException ex) {
            logger.fatal("Config file loading failed: " + ex);
        }

        return properties;
    }

    public Properties getProperties() {
        return properties;
    }

}
