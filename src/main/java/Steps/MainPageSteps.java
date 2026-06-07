package Steps;

import Flows.MainPageFlows;
import Resources.Wrapper;
import org.openqa.selenium.WebDriver;

public class MainPageSteps extends Wrapper {

    public MainPageSteps(WebDriver driver) {
        super(driver);
    }

    public static void ValidateSite() {
        MainPageFlows.ValidateSite();
    }

    public static void ValidateSubMenuOptions() {
        MainPageFlows.ValidateSubMenuOptions();
    }
}
