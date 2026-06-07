import Pages.TestNG;
import Steps.ApiSteps;
import org.testng.annotations.Test;

public class interactionsTest extends TestNG {

    public static final String suite = "API_UI_Tests";

    public static final String f008 = suite + "_ValidateApi200";
    public static final String f009 = suite + "_ValidateApi204";
    public static final String f010 = suite + "_ValidateApi400";
    public static final String f011 = suite + "_ValidateApi500";

    @Test(testName = f008,  groups = {"Regression", "Full"})
    public void validate200Status() {
        new ApiSteps(driver);
        ApiSteps.validate200http();
 }

    @Test(testName = f009,  groups = {"Full"})
    public void validate204Status() {
        new ApiSteps(driver);
        ApiSteps.validate204http();
    }
    
    @Test(testName = f010,  groups = {"Full"})
    public void validate400Status() {
        new ApiSteps(driver);
        ApiSteps.validate400http();
    }
    
    @Test(testName = f011,  groups = {"Full"})
    public void validate500Status() {
        new ApiSteps(driver);
        ApiSteps.validate500http();
    }
}