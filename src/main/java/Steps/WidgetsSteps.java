package Steps;

import org.openqa.selenium.WebDriver;

import Flows.MainPageFlows;
import Flows.WidgetsFlows;
import Resources.Wrapper;

public class WidgetsSteps extends Wrapper{

    public WidgetsSteps(WebDriver driver) {
        super(driver);
    }

    public static void validateAccordion(){
        MainPageFlows.selectOption("Widgets", "Accordion");
        WidgetsFlows.qaFunctionalOption("QA Software Testing", 0);
        WidgetsFlows.qaFunctionalOption("Functional Testing",1);
        WidgetsFlows.qaFunctionalOption("Non-Functional Testing",2);
    }

    public static void validateDatePicker(){
        MainPageFlows.selectOption("Widgets", "DatePicker");
        WidgetsFlows.validateNoOpt();
        

    }

}
