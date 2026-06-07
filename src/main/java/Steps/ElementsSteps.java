package Steps;

import Flows.ElementsFlows;
import Flows.MainPageFlows;
import Resources.Wrapper;
import org.openqa.selenium.WebDriver;

public class ElementsSteps extends Wrapper {

    public ElementsSteps(WebDriver driver) {
        super(driver);
    }

    public static void validateButtonsOptions(){
        MainPageFlows.selectOption("Elements","Buttons");
        ElementsFlows.selectButtons();
        ElementsFlows.validateButtonsActions();
        ElementsFlows.clearActions();
    }

    public static void validateCheckBoxOptions(){
        MainPageFlows.selectOption("Elements","CheckBox");
        ElementsFlows.updateCheckBoxOptions();
        ElementsFlows.validateOptionAttributes();
    }

    public static void validateRadioOptions(String name, int option){
        MainPageFlows.selectOption("Elements","RadioButton");
        ElementsFlows.validateRadioOptions(option);
        ElementsFlows.validateMessages(name, option);
    }

    public static void validateTextBoxOptions() {

        MainPageFlows.selectOption("Elements","TextBox");
        ElementsFlows.validateErrors();
        ElementsFlows.EnterDataNValidate("Ares", "cesar@mail.com" , "This is for testing");
    }


}

