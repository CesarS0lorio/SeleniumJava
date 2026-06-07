package Pages;

import Resources.Locators.ByLocator;
import Resources.Locators.LocatorReader;
import Resources.Reader;
import Resources.Wrapper;

public class ElementsPage {
    public static String pageName = "ElementsPage";

    //Buttons Section
    public static ByLocator btnDClick = LocatorReader.getLocator(pageName, "btnDClick");
    public static ByLocator btnRClick = LocatorReader.getLocator(pageName, "btnRClick");
    public static ByLocator btnSClick = LocatorReader.getLocator(pageName, "btnSClick");
    public static ByLocator btnDisabled = LocatorReader.getLocator(pageName, "btnDisabled");
    public static ByLocator btnClear = LocatorReader.getLocator(pageName, "btnClear");
    public static ByLocator txtDClick = LocatorReader.getLocator(pageName, "txtDClick");
    public static ByLocator txtRClick = LocatorReader.getLocator(pageName, "txtRClick");
    public static ByLocator txtSClick = LocatorReader.getLocator(pageName, "txtSClick");
    public static ByLocator txtDisabled = LocatorReader.getLocator(pageName, "txtDisabled");

    //CheckBox Section
    public static ByLocator inpNinja = LocatorReader.getLocator(pageName, "inpNinja");
    public static ByLocator inpAuto = LocatorReader.getLocator(pageName, "inpAuto");
    public static ByLocator inpPerf = LocatorReader.getLocator(pageName, "inpPerf");
    public static ByLocator inpFunc = LocatorReader.getLocator(pageName, "inpFunc");
    public static ByLocator txtNinja = LocatorReader.getLocator(pageName, "txtNinja");
    public static ByLocator txtAuto = LocatorReader.getLocator(pageName, "txtAuto");
    public static ByLocator txtPerf = LocatorReader.getLocator(pageName, "txtPerf");
    public static ByLocator txtFunc = LocatorReader.getLocator(pageName, "txtFunc");

    //Radio Button Section
    public static ByLocator inpName = LocatorReader.getLocator(pageName, "inpName");
    public static ByLocator inpOptions = LocatorReader.getLocator(pageName, "inpOptions");
    public static ByLocator txtInpOptions = LocatorReader.getLocator(pageName, "txtInpOptions");
    public static ByLocator txtDescription = LocatorReader.getLocator(pageName, "txtDescription");
    public static ByLocator btnView = LocatorReader.getLocator(pageName, "btnView"); //Also TextBox
    public static ByLocator txtOutMessage = LocatorReader.getLocator(pageName, "txtOutMessage");

    //TextBox Section
    public static ByLocator txtNameError = LocatorReader.getLocator(pageName, "txtNameError");
    public static ByLocator txtEmailError= LocatorReader.getLocator(pageName, "txtEmailError");
    public static ByLocator txtDescriptionError = LocatorReader.getLocator(pageName, "txtDescriptionError");
    public static ByLocator inpCompleteName = LocatorReader.getLocator(pageName, "inpCompleteName");
    public static ByLocator inpEmail = LocatorReader.getLocator(pageName, "inpEmail");
    public static ByLocator inpDescription = LocatorReader.getLocator(pageName, "inpDescription");
    public static ByLocator txtOutView = LocatorReader.getLocator(pageName, "txtOutView");

    public static void clickNValidate() {
        Wrapper.selectElement(btnSClick);
    }

    public static void dbClickNValidate() {
        Wrapper.selectdbClickElement(btnDClick);
    }

    public static void rightClickNValidate() {
        Wrapper.selectRightClickElement(btnRClick);
    }

    public static void validateDisabled(){
        Wrapper.validateAttributeExists(btnDisabled, "Disabled", true);
    }

    public static void validateButtonsActions() {
        Wrapper.validateTextElement(txtSClick, Reader.getProperty(pageName,"textSClick"));
        Wrapper.validateTextElement(txtDClick, Reader.getProperty(pageName,"textDClick"));
        Wrapper.validateTextElement(txtRClick, Reader.getProperty(pageName,"textRClick"));
        Wrapper.validateTextElement(txtDisabled, Reader.getProperty(pageName,"textDisabled"));
    }

    public static void validateClearActions() {
        Wrapper.selectElement(btnClear);
        Wrapper.waitForElementToDisappear(txtSClick);
        Wrapper.waitForElementToDisappear(txtDClick);
        Wrapper.waitForElementToDisappear(txtRClick);
    }

    public static void updateCheckBoxOptions(){
        Wrapper.validateTextElement(txtAuto, Reader.getProperty(pageName,"textAutoCh"));
        Wrapper.validateTextElement(txtPerf, Reader.getProperty(pageName,"textPerfUn"));
        Wrapper.selectElement(inpAuto);
        Wrapper.selectElement(inpPerf);
        Wrapper.validateTextElement(txtNinja, Reader.getProperty(pageName,"textNinjaCh"));
        Wrapper.validateTextElement(txtAuto, Reader.getProperty(pageName,"textAutoUn"));
        Wrapper.validateTextElement(txtPerf, Reader.getProperty(pageName,"textPerfCh"));
        Wrapper.validateTextElement(txtFunc, Reader.getProperty(pageName,"textFuncUn"));
    }

    public static void validateOptionAttributes(){
        Wrapper.validateAttributeExists(inpNinja, "disabled", true);
        Wrapper.validateAttributeExists(inpAuto, "disabled", false);
        Wrapper.validateAttributeExists(inpPerf, "disabled", false);
        Wrapper.validateAttributeExists(inpFunc, "disabled", true);
    }

    public static void validateRadioOptions(int options){
        Wrapper.validateListOptions(txtInpOptions, options);
    }

    public static void validateOutMessages(String name, int option){
        String [] radioOptions = Reader.getProperty(pageName, "textOutMessage").split(",");

        Wrapper.validateTextElement(txtDescription, Reader.getProperty(pageName,"textDescription"));

        Wrapper.selectElement(btnView);
        Wrapper.validateTextElement(txtOutMessage, radioOptions[0]);

        Wrapper.typeText(inpName,name);
        Wrapper.selectElement(btnView);
        Wrapper.validateTextElement(txtOutMessage, radioOptions[1]);

        Wrapper.selectOptionList(inpOptions,option);
        Wrapper.selectElement(btnView);
        Wrapper.validateTextElement(txtOutMessage, radioOptions[2]);
    }

    public static void validateElements(){
        Wrapper.validateAttributeContains(inpName, "placeholder", Reader.getProperty(pageName,"textInpName"));
    }

    public static void selectNValidate(){
        String [] textOptions = Reader.getProperty(pageName, "textOutView").split(",");

        Wrapper.selectElement(btnView);
        Wrapper.validateTextElement(txtNameError, Reader.getProperty(pageName,"textNameError"));
        Wrapper.validateTextElement(txtEmailError, Reader.getProperty(pageName,"textEmailError"));
        Wrapper.validateTextElement(txtDescriptionError, Reader.getProperty(pageName,"textDescriptionError"));
        Wrapper.validateTextElement(txtOutView, textOptions[0]);
    }

    public static void enterInputs(String name, String email, String description){
        Wrapper.typeText(inpCompleteName, name);
        Wrapper.typeText(inpEmail, email);
        Wrapper.typeText(inpDescription, description);
    }

    public static void selectView(){
        Wrapper.selectElement(btnView);
    }

    public static void validateContent(){
        Wrapper.validateAttributeContains(inpCompleteName, "placeholder", Reader.getProperty(pageName,"textNamePlaceholder"));
        Wrapper.validateAttributeContains(inpEmail, "placeholder", Reader.getProperty(pageName,"textEmailPlaceholder"));
        Wrapper.validateAttributeContains(inpDescription, "placeholder", Reader.getProperty(pageName,"textDescriptionPlaceholder"));
    }

    public static void validateOutput(){
        String [] textOptions = Reader.getProperty(pageName, "textOutView").split(",");
        Wrapper.validateTextElement(txtOutView, textOptions[1]);
    }

}
