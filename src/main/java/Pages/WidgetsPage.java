package Pages;

import Resources.Reader;
import Resources.Wrapper;
import Resources.Locators.ByLocator;
import Resources.Locators.LocatorReader;

public class WidgetsPage {

    public static String pageName = "WidgetsPage";

    //Accordion Section
    public static ByLocator optTabAccordion = LocatorReader.getLocator(pageName, "optTabAccordion");
    public static ByLocator txtTabAccordion = LocatorReader.getLocator(pageName, "txtTabAccordion");
    public static ByLocator txtContAccordion = LocatorReader.getLocator(pageName, "txtContAccordion");


    //DatePicker Section
    public static ByLocator btnViewDate = LocatorReader.getLocator(pageName, "btnViewDate");
    public static ByLocator txtContentDate = LocatorReader.getLocator(pageName, "txtContentDate");
    public static ByLocator inpDateOpt = LocatorReader.getLocator(pageName, "inpDateOpt");
    public static ByLocator btnDaysOpt = LocatorReader.getLocator(pageName, "btnDaysOpt");
    public static ByLocator btnTodayOpt = LocatorReader.getLocator(pageName, "btnTodayOpt");


    public static void selectAccordionOption(String option){
        Wrapper.selectMenuOption(optTabAccordion, option);     
    }

    public static void validateAccordionContent(int optionList){
        String [] tableTexts = Reader.getProperty(pageName, "textTabAccordion").split(",");
        String [] textContent = Reader.getProperty(pageName, "textContentAccordion").split(",");
        Wrapper.validateTextElement(txtTabAccordion, tableTexts[optionList]);
        Wrapper.validateTextElement(txtContAccordion, textContent[optionList]);
    }

    public static void selectView(){
        Wrapper.selectElement(btnViewDate);      
        }
        
    public static void validateNoOptSelected(){
        Wrapper.validateTextElement(txtContentDate, Reader.getProperty(pageName, "textNoContent"));
    }

}
