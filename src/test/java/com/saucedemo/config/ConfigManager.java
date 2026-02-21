package com.saucedemo.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigManager {

    private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);
    private static final Properties properties = new Properties();
    private static ConfigManager instance;

    private ConfigManager() {
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (is != null) {
                properties.load(is);
                logger.info("Configuration loaded from config.properties");
            }

        } catch (IOException e) {
            logger.warn("Could not load config.properties: {}", e.getMessage());
        }
    }

    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }

        return instance;
    }

    public String get(String key) {
        // System properties override file properties
        String sysProp = System.getProperty(key);
        if (sysProp != null && !sysProp.isEmpty()) {
            return sysProp;
        }

        String envProp = System.getenv(key.replace('.', '_').toUpperCase());
        if (envProp != null && !envProp.isEmpty()) {
            return envProp;
        }

        return properties.getProperty(key);
    }

    public String get(String key, String defaultValue) {
        String value = get(key);

        return (value != null && !value.isEmpty()) ? value : defaultValue;
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        String value = get(key);

        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }

    public int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(get(key, String.valueOf(defaultValue)));

        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public String getBaseUrl() {
        return get("base.url", "https://www.saucedemo.com");
    }

    public String getBrowser() {
        return get("browser", "chrome");
    }

    public boolean isHeadless() {
        return getBoolean("headless", false);
    }

    public int getImplicitWait() {
        return getInt("implicit.wait", 10);
    }

    public int getExplicitWait() {
        return getInt("explicit.wait", 15);
    }

    public int getPageLoadTimeout() {
        return getInt("page.load.timeout", 30);
    }

    public boolean isScreenshotOnFailure() {
        return getBoolean("screenshot.on.failure", true);
    }
}
