package Resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;
    // La ruta se ha ajustado para apuntar a 'src/resources/'
    private static final String PROPERTIES_BASE_PATH = System.getProperty("user.dir") + "/src/resources/";

    public static void loadProperties(String environment) {
        properties = new Properties();
        String filePath = PROPERTIES_BASE_PATH + environment + ".properties";
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
            System.out.println("INFO: Loaded properties for environment: " + environment + " from: " + filePath);
        } catch (IOException e) {
            System.err.println("ERROR: Failed to load properties from " + filePath + ". Ensure the file exists and is accessible. Error: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to load environment properties for " + environment, e);
        }
    }

    public static String getProperty(String key) {
        if (properties == null) {
            throw new IllegalStateException("Properties not loaded. Call ConfigReader.loadProperties(environment) first in @BeforeSuite.");
        }
        String value = properties.getProperty(key);
        if (value == null) {
            System.err.println("WARNING: Property '" + key + "' not found in loaded configuration.");
        }
        return value;
    }
}