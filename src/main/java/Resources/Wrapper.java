package Resources;

import Resources.Locators.ByLocator;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;

import org.testng.Assert;

public class Wrapper {

    private static final int TIMEOUT = 5;
    private static final int POLLING = 100;

    protected static WebDriver driver; // Changed to protected, assuming it's accessed by subclasses
    private static WebDriverWait wait;

    public Wrapper(WebDriver driver) {
        Wrapper.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT), Duration.ofMillis(POLLING));
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIMEOUT), this);
    }

    ///// Utils
    public static void waitTime(int time){
        try {
            Thread.sleep(time*1000);
        } catch (InterruptedException e) {
            Reporter.log(Status.FAIL, "Thread sleep interrupted: " + e.getMessage());
            Thread.currentThread().interrupt(); // Restore the interrupted status
            Assert.fail("Thread sleep interrupted: " + e.getMessage()); // Added assertion message
        }
    }

    public static void logger(Status status, String description) {
        Reporter.log(status, description);
    }

    public static void logger(Exception e) {
        Reporter.log(e);
        Reporter.getSnapshoot(driver);
    }

    
    public static WebElement getElement(ByLocator locator) {
        try {
            waitForElementToAppearClean(locator);
            WebElement element = driver.findElement(locator.getBy());
            return element;
        } catch (Exception e) {
            logger(Status.FAIL, "Could not obtain WebElement '" + locator.getDescription() + "'.");
            logger(e);
            Assert.fail("Failed to obtain WebElement '" + locator.getDescription() + "': " + e.getMessage());
            return null; // O lanzar la excepción, dependiendo de tu manejo de errores.
        }
    }

    ////// Waits
    public static void waitForElementToAppear(ByLocator locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator.getBy()));
            logger(Status.PASS, "Element '" + locator.getDescription() + "' has been located.");
        } catch (Exception e) {
            logger(Status.INFO, "Element '" + locator.getDescription() + "' was not found after waiting.");
            logger(e);
            Assert.fail("Failed to find element '" + locator.getDescription() + "': " + e.getMessage()); // Added assertion message
        }
    }

    public static void waitForElementToAppearClean(ByLocator locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator.getBy()));
        } catch (Exception e) {
            logger(Status.INFO, "Element '" + locator.getDescription() + "' was not found after waiting.");
            logger(e);
            Assert.fail("Failed to find element '" + locator.getDescription() + "' (Clean): " + e.getMessage()); // Added assertion message
        }
    }

    public static void waitForElementToDisappear(ByLocator locator) {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator.getBy()));
            logger(Status.PASS, "'" + locator.getDescription() + "' element is not visible.");
        } catch (Exception e) {
            logger(Status.INFO, "Element '" + locator.getDescription() + "' is still visible.");
            logger(e);
            Assert.fail("Failed for element '" + locator.getDescription() + "' to disappear: " + e.getMessage()); // Added assertion message
        }
    }

    ///// Types
    public static void typeText(ByLocator locator, String text){
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator.getBy()));
            driver.findElement(locator.getBy()).sendKeys(text);
            logger(Status.PASS, "Text '" + text + "' has been set on '" + locator.getDescription() + "' element.");
        } catch (Exception e) {
            logger(Status.INFO, "Text '" + text + "' could not be set on element '" + locator.getDescription() + "'.");
            logger(e);
            Assert.fail("Failed to type text on element '" + locator.getDescription() + "': " + e.getMessage()); // Added assertion message
        }
    }

    ///// Actions
    public static void selectElement(ByLocator locator){
        try{
            waitForElementToAppearClean(locator); // Ensure element is visible before clicking
            driver.findElement(locator.getBy()).click();
            waitTime(1);
            logger(Status.PASS, "'" + locator.getDescription() + "' element clicked.");
        } catch (Exception e) {
            logger(Status.INFO, "'" + locator.getDescription() + "' element could not be selected/clicked.");
            logger(e);
            Assert.fail("Failed to click element '" + locator.getDescription() + "': " + e.getMessage()); // Added assertion message
        }
    }

    public static void selectdbClickElement(ByLocator locator){
        try{
            Actions actions = new Actions(driver);
            waitForElementToAppearClean(locator);

            actions.doubleClick(driver.findElement(locator.getBy())).perform();
            waitTime(1);
            logger(Status.PASS, "'" + locator.getDescription() + "' element double-clicked.");
        } catch (Exception e) {
            logger(Status.INFO, "'" + locator.getDescription() + "' element could not be double-clicked.");
            logger(e);
            Assert.fail("Failed to double-click element '" + locator.getDescription() + "': " + e.getMessage()); // Added assertion message
        }
    }

    public static void selectRightClickElement(ByLocator locator){
        try{
            Actions actions = new Actions(driver);
            waitForElementToAppearClean(locator);

            actions.contextClick(driver.findElement(locator.getBy())).perform();
            waitTime(1);
            logger(Status.PASS, "'" + locator.getDescription() + "' element right-clicked.");
        } catch (Exception e) {
            logger(Status.INFO, "'" + locator.getDescription() + "' element could not be right-clicked.");
            logger(e);
            Assert.fail("Failed to right-click element '" + locator.getDescription() + "': " + e.getMessage()); // Added assertion message
        }
    }

    //// Search, Select
    public static void selectMenuOption(ByLocator locator, String option){
        int success = 0;
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator.getBy()));
            List<WebElement> elements = driver.findElements(locator.getBy());

            for(int index = 0; index < elements.size(); index++ ){
                if (elements.get(index).getText().contains(option)) {
                    elements.get(index).click();
                    logger(Status.PASS, "'" + option + "' option has been selected.");
                    success++;
                    break; // Exit loop after finding and clicking
                }
            }
            if (success == 0){
                throw new Exception("Option '" + option + "' has not been located or selected."); // Changed to new Exception
            }
        } catch (Exception e) {
            logger(Status.INFO, "Element '" + locator.getDescription() + "' could not select option '" + option + "'.");
            logger(e);
            Assert.fail("Failed to select menu option '" + option + "' for element '" + locator.getDescription() + "': " + e.getMessage()); // Added assertion message
        }
    }

    public static void selectOptionList(ByLocator locator, int option){
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator.getBy()));
            List<WebElement> elements = driver.findElements(locator.getBy());

            if (option <= 0 || option > elements.size()){ // Check for valid index (1-based)
                throw new Exception("Option index '" + option + "' is out of bounds. List size: " + elements.size()); // Changed to new Exception
            }

            elements.get(option-1).click();
            logger(Status.PASS, "'" + option + "' option has been selected.");
        } catch (Exception e) {
            logger(Status.INFO, "Element '" + locator.getDescription() + "' could not select option by index '" + option + "'.");
            logger(e);
            Assert.fail("Failed to select menu option by index '" + option + "' for element '" + locator.getDescription() + "': " + e.getMessage()); // Added assertion message
        }
    }

    ///// Validate
    public static void validateTextElement(ByLocator locator, String text){
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator.getBy()));
            String elemText = driver.findElement(locator.getBy()).getText();
            if (elemText.contains(text)){
                logger(Status.PASS, "Validated: '" + locator.getDescription() + "' contains the text '" + text + "'.");
            } else {
                throw new Exception("Validation failed: Element '" + locator.getDescription() + "' does not contain the text '" + text + "'. Text found: '" + elemText + "'."); // Changed to new Exception
            }
        } catch (Exception e) {
            logger(Status.INFO, "Validation failed for element '" + locator.getDescription() + "'.");
            logger(e);
            Assert.fail("Text validation failed for element '" + locator.getDescription() + "': " + e.getMessage()); // Added assertion message
        }
    }

    public static void validateSubOptionsUsingHelper(ByLocator helper, ByLocator locator, ByLocator subLocator, String text, String subText){
        int success = 0;
        try{
            waitForElementToAppearClean(helper);
            waitForElementToAppearClean(locator);
            List<WebElement> elements = driver.findElements(helper.getBy());
            List<WebElement> locators = driver.findElements(locator.getBy());

            for(int index = 0; index < elements.size(); index++ ){
                if (elements.get(index).getText().contains(text)){
                    List<WebElement> subElement = locators.get(index).findElements(subLocator.getBy());
                    for(int subIndex = 0; subIndex < subElement.size(); subIndex++){
                        if (subElement.get(subIndex).getText().contains(subText.trim())){
                            logger(Status.PASS, "Validated: '" + subText + "' option has been found in '" + text + "'.");
                            success++;
                            break; // Break inner loop after finding
                        }
                    }
                }
            }
            if (success == 0){
                throw new Exception("Validation failed: The '" + subText + "' option has not been located in '" + text + "'."); // Changed to new Exception
            }
        } catch (Exception e) {
            logger(Status.INFO, "Validation failed for sub-option '" + subText + "' in '" + text + "'.");
            logger(e);
            Assert.fail("Sub-option validation failed: " + e.getMessage()); // Added assertion message
        }
    }
    

    public static void validateAttributeContains(ByLocator locator, String attribute, String content){
        try{
            waitForElementToAppearClean(locator);
            WebElement element = driver.findElement(locator.getBy());

            String attributeValue = element.getAttribute(attribute);
            if (attributeValue != null && attributeValue.contains(content)){ // Added null check for attributeValue
                logger(Status.PASS, "Validated: '" + locator.getDescription() + "' element contains attribute '" + attribute + "' with value '" + attributeValue + "'.");
            } else {
                throw new Exception("Validation failed: Element '" + locator.getDescription() + "' does not contain attribute '" + attribute + "' with expected content '" + content + "'. Found value: '" + attributeValue + "'."); // Changed to new Exception
            }
        } catch (Exception e) {
            logger(Status.INFO, "Attribute validation failed for '" + locator.getDescription() + "'.");
            logger(e);
            Assert.fail("Attribute validation failed for element '" + locator.getDescription() + "': " + e.getMessage()); // Added assertion message
        }
    }

    public static void validateAttributeExists(ByLocator locator, String attribute, Boolean expected){
        try{
            waitForElementToAppearClean(locator);
            WebElement element = driver.findElement(locator.getBy());

            String attributeValue = element.getAttribute(attribute);

            if ((expected && attributeValue != null) || (!expected && attributeValue == null)) {
                logger(Status.PASS, "Validated: Attribute '" + attribute + "' matches expected condition (" + expected + ") on '" + locator.getDescription() + "' element.");
            } else {
                String foundStatus = (attributeValue != null) ? "found with value '" + attributeValue + "'" : "not found";
                throw new Exception("Validation failed: Attribute '" + attribute + "' does not match expected condition (" + expected + ") on '" + locator.getDescription() + "' element. Attribute was " + foundStatus + "."); // Changed to new Exception
            }
        } catch (Exception e) {
            logger(Status.INFO, "Attribute existence validation failed for '" + locator.getDescription() + "'.");
            logger(e);
            Assert.fail("Attribute existence validation failed for element '" + locator.getDescription() + "': " + e.getMessage()); // Added assertion message
        }
    }

    public static void validateListOptions(ByLocator locator, int options){
        List<WebElement> elements = new ArrayList<>();
        try{
           waitForElementToAppearClean(locator);
           elements = driver.findElements(locator.getBy());

           String optionsList = "";
           if (options <= elements.size() && options > 0) { // Changed condition for options > 0
               for (WebElement element : elements) {
                   optionsList = optionsList + element.getText() + ", ";
               }
               // Remove trailing comma and space if list is not empty
               if (!optionsList.isEmpty()) {
                   optionsList = optionsList.substring(0, optionsList.length() - 2);
               }
           } else {
               throw new Exception("Validation failed: The list options for '" + locator.getDescription() + "' do not match expected count. Expected: " + options + ", Listed: " + elements.size() + "."); // Changed to new Exception
           }
           logger(Status.PASS, "Validated: The list for '" + locator.getDescription() + "' has '" + elements.size() + "' elements: " + optionsList + ".");

       } catch (Exception e) {
           logger(Status.INFO, "List options validation failed for '" + locator.getDescription() + "'.");
           logger(e);
           Assert.fail("List options validation failed for element '" + locator.getDescription() + "': " + e.getMessage()); // Added assertion message
       }
    }


      public static void validateHttpStatusCode(String urlString, int expectedStatusCode) {
        if (urlString == null || urlString.isEmpty()) {
            logger(Status.FAIL, "URL provided for HTTP status code validation is null or empty.");
            Assert.fail("URL for HTTP status code validation is invalid.");
            return;
        }
        
        URL url = null;
        try {
            URI uri = new URI(urlString);
            url = uri.toURL();
        } catch (URISyntaxException e) {
            logger(Status.FAIL, "Invalid URI syntax for URL '" + urlString + "': " + e.getMessage());
            logger(e);
            Assert.fail("HTTP status code validation failed due to invalid URI syntax: " + e.getMessage());
            return;
        } catch (MalformedURLException e) {
            logger(Status.FAIL, "Malformed URL for '" + urlString + "': " + e.getMessage());
            logger(e);
            Assert.fail("HTTP status code validation failed due to malformed URL: " + e.getMessage());
            return;
        }
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int actualStatusCode = connection.getResponseCode();
            connection.disconnect();

            if (actualStatusCode == expectedStatusCode) {
                logger(Status.PASS, "Validated HTTP status code for URL '" + urlString + "': Expected " + expectedStatusCode + ", Actual " + actualStatusCode + ".");
            } else {
                throw new Exception("HTTP status code validation failed for URL '" + urlString + "': Expected " + expectedStatusCode + ", Actual " + actualStatusCode + ".");
            }
        } catch (IOException e) {
            logger(Status.FAIL, "Failed to connect to URL '" + urlString + "' to validate HTTP status code: " + e.getMessage());
            logger(e);
            Assert.fail("HTTP status code validation failed due to connection issue: " + e.getMessage());
        } catch (Exception e) {
            logger(Status.INFO, "HTTP status code validation failed for URL '" + urlString + "'.");
            logger(e);
            Assert.fail("HTTP status code validation failed for URL '" + urlString + "': " + e.getMessage());
        }
    }

public static void validateHttpBodyContent(String urlString, String expectedBodyContent) {
        if (urlString == null || urlString.isEmpty()) {
            logger(Status.FAIL, "URL provided for HTTP body content validation is null or empty.");
            Assert.fail("URL for HTTP body content validation is invalid.");
            return;
        }
        if (expectedBodyContent == null || expectedBodyContent.isEmpty()) {
            logger(Status.INFO, "No expected body content provided for URL '" + urlString + "'. Skipping body content validation.");
            return; // No content to validate, so just return
        }

        URL url = null;
        try {
            URI uri = new URI(urlString);
            url = uri.toURL();
        } catch (URISyntaxException e) {
            logger(Status.FAIL, "Invalid URI syntax for URL '" + urlString + "': " + e.getMessage());
            logger(e);
            Assert.fail("HTTP body content validation failed due to invalid URI syntax: " + e.getMessage());
            return;
        } catch (MalformedURLException e) {
            logger(Status.FAIL, "Malformed URL for '" + urlString + "': " + e.getMessage());
            logger(e);
            Assert.fail("HTTP body content validation failed due to malformed URL: " + e.getMessage());
            return;
        }

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            StringBuilder responseBody = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(
                    (connection.getResponseCode() >= 200 && connection.getResponseCode() < 400) ? 
                            connection.getInputStream() : connection.getErrorStream()
            ))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    responseBody.append(inputLine);                    
                }
            }
            
            String actualBodyContent = responseBody.toString();

            if (actualBodyContent.contains(expectedBodyContent)) {
                logger(Status.PASS, "Validated HTTP response body for URL '" + urlString + "' contains expected text: '" + expectedBodyContent + "'.");
            } else {
                throw new Exception("HTTP response body validation failed for URL '" + urlString + "'. Expected to contain '" + expectedBodyContent + "', but found: '" + actualBodyContent + "'.");
            }

        } catch (IOException e) {
            logger(Status.FAIL, "Failed to connect to URL '" + urlString + "' to validate HTTP body content: " + e.getMessage());
            logger(e);
            Assert.fail("HTTP body content validation failed due to connection issue: " + e.getMessage());
        } catch (Exception e) {
            logger(Status.INFO, "HTTP body content validation failed for URL '" + urlString + "'.");
            logger(e);
            Assert.fail("HTTP body content validation failed for URL '" + urlString + "': " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

    }

}