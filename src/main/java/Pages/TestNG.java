package Pages;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.io.IOException;
import java.lang.reflect.Method;

import Resources.Browser;
import Resources.ConfigReader;
import Resources.Reporter;

public class TestNG {

    public static WebDriver driver;

    private static String suiteEnvironment;
    private static String suiteBrowserType;
    private static boolean suiteHeadless;

    @BeforeSuite(alwaysRun = true)
    @Parameters({"environment", "browserType", "headless"})
    public void setUpSuite(
            @Optional("prod") String environment,
            @Optional("chrome") String browserType,
            @Optional("false") String headlessStr
    ) throws IOException {
        System.out.println("--- @BeforeSuite: Initializing Test Suite (one-time setup) ---");
        System.out.println("Environment: " + environment);

        ConfigReader.loadProperties(environment); // Carga propiedades para toda la suite

        suiteEnvironment = environment;
        suiteBrowserType = browserType;
        suiteHeadless = Boolean.parseBoolean(headlessStr);

        Reporter.initReport(environment);
    }

    @BeforeMethod (alwaysRun = true)
    public void setUpMethod(Method method, ITestContext context) {
        System.out.println("--- @BeforeMethod: Starting Browser for Test: " + method.getName() + " ---");

        Browser.start(suiteBrowserType, suiteHeadless);
        driver = Browser.getDriver();

        String baseUrl = ConfigReader.getProperty("baseUrl");
        if (baseUrl == null || baseUrl.isEmpty()) {
            throw new RuntimeException("Base URL not found in " + suiteEnvironment + ".properties file. Please check.");
        }
        driver.get(baseUrl);
        System.out.println("INFO: Navigating to Base URL: " + baseUrl);

        String testNGTestName = context.getName();
        String testMethodName = method.getName();
        if (method.isAnnotationPresent(Test.class) && !method.getAnnotation(Test.class).testName().isEmpty()) {
            testMethodName = method.getAnnotation(Test.class).testName();
        }
        Reporter.initTestMethod(testNGTestName, testMethodName);


        context.setAttribute("currentDriver", driver); // Esto sigue siendo útil
        context.setAttribute("baseUrl", ConfigReader.getProperty("baseUrl"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownMethod() {
        System.out.println("--- @AfterMethod: Closing Browser for current Test ---");
        if(null != driver) {
            Browser.quit(); // Manejar la salida del driver ThreadLocal si lo usa
            driver = null; // Limpiar la referencia local
        }
    }


    @AfterSuite (alwaysRun = true)
    public void tearDownSuite() {
        System.out.println("--- @AfterSuite: Finalizing Report ---");
        if(null != driver) {
            Browser.quit();
        }
        Reporter.closeReport("MySeleniumAutomationSuite");
    }

    @DataProvider(name="testdata")
    public Object[][] TestDataFeed(){
        Object[][] credentialsUser = new Object[2][2];

        credentialsUser[0][0]="standard_user";
        credentialsUser[0][1]="secret_sauce";
        credentialsUser[1][0]="problem_user";
        credentialsUser[1][1]="secret_sauce";

        return credentialsUser;
    }
}