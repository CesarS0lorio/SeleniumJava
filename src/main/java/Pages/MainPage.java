package Pages;

import Resources.Locators.ByLocator;
import Resources.Locators.LocatorReader;
import Resources.Reader;
import Resources.Wrapper;

public class MainPage {

    public static String pageName = "MainPage";

    static ByLocator txtHeader = LocatorReader.getLocator(pageName, "txtHeader");
    static ByLocator txtMenu = LocatorReader.getLocator(pageName, "txtMenu");
    static ByLocator txtMenuOpts = LocatorReader.getLocator(pageName, "txtMenuOpts");
    static ByLocator txtMenuSubs = LocatorReader.getLocator(pageName, "txtMenuSubs");
    static ByLocator txtMenuSubsHeaders = LocatorReader.getLocator(pageName, "txtMenuSubsHeaders");
    static ByLocator txtFooter = LocatorReader.getLocator(pageName, "txtFooter");
    static ByLocator txtMainContent = LocatorReader.getLocator(pageName, "txtMainContent");

    public static void validateHeader(){
        Wrapper.waitForElementToAppear(txtHeader);
    }

    public static void validateMenu(){
        String [] menuOptions = Reader.getProperty(pageName, "textMenu").split(",");

        for(String option : menuOptions){
            Wrapper.validateTextElement(txtMenu, option.trim());
        }
    }

    public static void validateFooter(){
        Wrapper.validateTextElement(txtFooter, Reader.getProperty(pageName,"textFooter"));
    }

    public static void validateContent(){
        Wrapper.validateTextElement(txtMainContent, Reader.getProperty(pageName, "textMainContent"));
    }

    public static void validateMenuOptions(String options, String txtSubOptions){
        Wrapper.selectMenuOption(txtMenuOpts, options);

        String [] menuOptions = Reader.getProperty(pageName, txtSubOptions).split(",");
        for(String subOptions : menuOptions){
            Wrapper.validateSubOptionsUsingHelper(txtMenuOpts,txtMenuSubsHeaders,txtMenuSubs,options, subOptions);
        }
    }

    public static void selectMenu(String option) {
        Wrapper.selectMenuOption(txtMenuOpts, option);
    }

    public static void selectSubMenu(String option) {
        Wrapper.selectMenuOption(txtMenuSubs, option);
    }

}

