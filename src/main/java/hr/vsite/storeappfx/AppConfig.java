package hr.vsite.storeappfx;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = AppConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                System.err.println("Unable to find config.properties");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double getTaxRate() {
        return Double.parseDouble(properties.getProperty("taxRate"));
    }
    public static String getCurrency() {
        return properties.getProperty("currency");
    }

}
