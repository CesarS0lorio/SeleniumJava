package Resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Reader {

    private static final Map<String, Properties> propertiesCache = new HashMap<>();

    public static String getProperty(String pageName, String key) {
        propertiesCache.computeIfAbsent(pageName, pName -> loadPropertiesFromFile(pName));
        return propertiesCache.get(pageName).getProperty(key);
    }

    private static Properties loadPropertiesFromFile(String pageName) {
        Properties properties = new Properties();
        String filePath = "src/resources/properties/" + pageName + ".properties";
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("El archivo no pudo ser cargado: " + filePath);
        }
        return properties;
    }
}