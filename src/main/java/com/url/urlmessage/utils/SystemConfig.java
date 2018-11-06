package com.url.urlmessage.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.Properties;

public class SystemConfig {
    private static Properties properties;

    public SystemConfig() {
        Resource resource = new ClassPathResource("application.properties");
        try {
            properties = PropertiesLoaderUtils.loadProperties(resource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties == null ? null : properties.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return properties == null ? null : properties.getProperty(key, defaultValue);
    }

    public static Properties getProperty() {
        return properties;
    }
}
