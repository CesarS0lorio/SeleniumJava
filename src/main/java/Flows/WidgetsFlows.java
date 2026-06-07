package Flows;

import Pages.WidgetsPage;
import Resources.Wrapper;

public class WidgetsFlows {

    public static void qaFunctionalOption(String Option, int list){
        Wrapper.waitTime(2);
        WidgetsPage.selectAccordionOption(Option);
        WidgetsPage.validateAccordionContent(list);
    }

    public static void validateNoOpt(){
        WidgetsPage.selectView();
        WidgetsPage.validateNoOptSelected();        
    }
}
