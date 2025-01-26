package config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static Properties properties = new Properties();

    static {
        try (InputStream input = ConfigManager.class.getResourceAsStream("/config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties file not found in the classpath");
            }
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration properties", e);
        }
    }

    /**
     * Get a property value by key.
     *
     * @param key the property key
     * @return the property value
     */
    public static String get(String key) {
        return properties.getProperty(key);
    }

    /**
     * Get a property value as an integer.
     *
     * @param key the property key
     * @return the integer value
     */
    public static int getInt(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    /**
     * Get a property value as a boolean.
     *
     * @param key the property key
     * @return the boolean value
     */
    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(properties.getProperty(key));
    }
}
