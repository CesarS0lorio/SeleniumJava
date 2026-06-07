package Steps;

import Flows.ApiFlows;
import Flows.MainPageFlows;
import Resources.Wrapper;
import org.openqa.selenium.WebDriver;

public class ApiSteps extends Wrapper {

    public ApiSteps(WebDriver driver) {
        super(driver);
    }

    public static void validate200http() {
        MainPageFlows.selectOption("Interactions", "API");
        ApiFlows.test200();
        }

    public static void validate204http() {
        MainPageFlows.selectOption("Interactions", "API");
        ApiFlows.test204();
    }

    public static void validate400http() {
        MainPageFlows.selectOption("Interactions", "API");
        ApiFlows.test400();
    }

    public static void validate500http() {
        MainPageFlows.selectOption("Interactions", "API");
        ApiFlows.test500();
    }


}