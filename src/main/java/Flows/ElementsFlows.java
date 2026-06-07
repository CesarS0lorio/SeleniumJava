package Flows;

import Pages.ElementsPage;

public class ElementsFlows {

    public static void selectButtons(){
        ElementsPage.dbClickNValidate();
        ElementsPage.clickNValidate();
        ElementsPage.rightClickNValidate();

    }

    public static void validateButtonsActions(){
        ElementsPage.validateButtonsActions();
        ElementsPage.validateDisabled();
    }


    public static void clearActions(){
        ElementsPage.validateClearActions();
    }

    public static void updateCheckBoxOptions(){
        ElementsPage.updateCheckBoxOptions();
    }

    public static void validateOptionAttributes(){
        ElementsPage.validateOptionAttributes();
    }

    public static void validateMessages(String name, int option){
        ElementsPage.validateOutMessages(name, option);
        ElementsPage.validateElements();
    }

    public static void validateRadioOptions(int options){
        ElementsPage.validateRadioOptions(options);
    }

    public static void validateErrors(){
        ElementsPage.validateContent();
        ElementsPage.selectNValidate();
    }

    public static void EnterDataNValidate(String name, String email, String description) {
        ElementsPage.enterInputs(name, email, description);
        ElementsPage.selectView();
        ElementsPage.validateOutput();

    }

}
