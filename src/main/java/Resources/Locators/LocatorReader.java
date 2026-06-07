package Resources.Locators;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken; // Importar TypeToken
import org.openqa.selenium.By;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type; // Importar Type
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocatorReader {

    private static final Map<String, Map<String, Locator>> loadedPageLocators = new ConcurrentHashMap<>();

    private static final String LOCATORS_BASE_PATH = System.getProperty("user.dir") + "/src/resources/elemLocators/";

    private LocatorReader() {
        // Constructor privado para evitar instanciación
    }

    private static void loadLocatorsForPage(String pageName) {
        if (!loadedPageLocators.containsKey(pageName)) {
            String filePath = LOCATORS_BASE_PATH + pageName + ".json";
            try (FileReader reader = new FileReader(filePath)) {
                Gson gson = new Gson();
                // **** MODIFICACIÓN CLAVE AQUÍ ****
                // Usar TypeToken para deserializar correctamente el mapa anidado
                Type type = new TypeToken<Map<String, Map<String, Locator>>>(){}.getType(); //
                Map<String, Map<String, Locator>> tempMap = gson.fromJson(reader, type); // Usar 'reader' directamente en lugar de 'jsonReader' si no hay necesidades específicas de un JsonReader. Se puede dejar jsonReader si ya funciona así.

                if (tempMap == null || !tempMap.containsKey(pageName)) {
                    throw new IOException("JSON file " + pageName + ".json is empty or does not contain a top-level key matching '" + pageName + "'.");
                }
                
                Map<String, Locator> locatorsForCurrentPage = tempMap.get(pageName);
                if (locatorsForCurrentPage == null) {
                    throw new IOException("No locators found under the key '" + pageName + "' in " + pageName + ".json.");
                }

                loadedPageLocators.put(pageName, locatorsForCurrentPage);
                System.out.println("INFO: Locators for page '" + pageName + "' loaded successfully from: " + filePath);

            } catch (IOException e) {
                System.err.println("ERROR: Failed to load locators for page '" + pageName + "' from '" + filePath + "': " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Failed to load locators from " + filePath, e);
            } catch (Exception e) {
                System.err.println("ERROR: An unexpected error occurred while loading locators for page '" + pageName + "' from '" + filePath + "': " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Unexpected error loading locators from " + filePath, e);
            }
        }
    }

    public static ByLocator getLocator(String pageName, String elementName) {
        loadLocatorsForPage(pageName);

        Map<String, Locator> locatorsForPage = loadedPageLocators.get(pageName);
        if (locatorsForPage == null) {
            throw new IllegalArgumentException("Internal error: Locators for page '" + pageName + "' were not found after loading attempt.");
        }

        Locator locator = locatorsForPage.get(elementName);
        if (locator == null) {
            throw new IllegalArgumentException("Element '" + elementName + "' not found on page '" + pageName + "'. Check your JSON file and element name.");
        }
        
        if (locator.getType() == null || locator.getType().isEmpty()) {
            throw new IllegalArgumentException("Locator type is null or empty for element '" + elementName + "' on page '" + pageName + "'.");
        }
        if (locator.getValue() == null || locator.getValue().isEmpty()) {
            throw new IllegalArgumentException("Locator value is null or empty for element '" + elementName + "' on page '" + pageName + "'.");
        }

        return createByObject(locator.getType(), locator.getValue(), locator.getDescription());
    }

    private static ByLocator createByObject(String type, String value, String description) {
        By byObject;
        switch (type.toLowerCase()) {
            case "id":
                byObject = By.id(value);
                break;
            case "name":
                byObject = By.name(value);
                break;
            case "xpath":
                byObject = By.xpath(value);
                break;
            case "cssselector":
            case "css":
                byObject = By.cssSelector(value);
                break;
            case "classname":
            case "class":
                byObject = By.className(value);
                break;
            case "tagname":
            case "tag":
                byObject = By.tagName(value);
                break;
            case "linktext":
                byObject = By.linkText(value);
                break;
            case "partiallinktext":
                byObject = By.partialLinkText(value);
                break;
            default:
                throw new IllegalArgumentException("Unknown or unsupported locator type: '" + type + "'. Supported types: id, name, xpath, cssselector, classname, tagname, linktext, partiallinktext.");
        }
        return new ByLocator(byObject, description);
    }
}