import org.testng.annotations.Test;

import Pages.TestNG;
import Steps.WidgetsSteps;

public class widgetsTest extends TestNG{

    public static final String suite = "Widgets";

    public static final String f012 = suite + "_Accordion";
    public static final String f013 = suite + "_DatePicker";
    public static final String f014 = suite + "_DropDown";
    public static final String f015 = suite + "_Menu";
    public static final String f016 = suite + "_Tabs";
    
    @Test(testName = f012)
    public void validateAccordions(){
        new WidgetsSteps(driver);
        WidgetsSteps.validateAccordion();
    }

    @Test(testName = f013)
    public void validateDatePicker(){
        new WidgetsSteps(driver);
        WidgetsSteps.validateDatePicker();
    }
}
