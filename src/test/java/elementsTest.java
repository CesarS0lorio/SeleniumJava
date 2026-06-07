import Pages.TestNG;
import Steps.ElementsSteps;
import org.testng.annotations.Test;


public class elementsTest extends TestNG {

    public static final String suite = "Elements";

    public static final String f003 = suite + "_ButtonsOptions";
    public static final String f004 = suite + "_CheckBoxOptions";
    public static final String f005 = suite + "_RadioButtonOptions";
    public static final String f006 = suite + "_SlidersOptions";
    public static final String f007 = suite + "_TextBoxOptions";


    @Test(testName = f003,  groups = {"Regression", "Full"})
    public void buttonsOptions() {
        new ElementsSteps(driver);
        ElementsSteps.validateButtonsOptions();
    }

    @Test(testName = f004,  groups = {"Full"})
    public void checkBoxOptions() {
        new ElementsSteps(driver);
        ElementsSteps.validateCheckBoxOptions();
    }

    @Test(testName = f005,  groups = {"Full"})
    public void radioButtonsOptions() {
        new ElementsSteps(driver);
        ElementsSteps.validateRadioOptions("Ares", 3);
    }

    @Test(testName = f007,  groups = {"Full"})
    public void textBoxOptions() {
        new ElementsSteps(driver);
        ElementsSteps.validateTextBoxOptions();
    }



}
