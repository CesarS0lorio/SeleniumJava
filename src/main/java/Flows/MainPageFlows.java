package Flows;

import Pages.MainPage;

public class MainPageFlows {

    public static void ValidateSite() {
        MainPage.validateHeader();
        MainPage.validateMenu();
        MainPage.validateFooter();
        MainPage.validateContent();
    }

    public static void ValidateSubMenuOptions() {
        MainPage.validateMenuOptions("Elements", "textElemMenu");
        MainPage.validateMenuOptions("Interactions","textIntMenu");
        MainPage.validateMenuOptions("Widgets", "textWidMenu");
        MainPage.validateMenuOptions("Windows","textWinMenu");
    }

    public static void selectOption(String option, String subOption) {
        MainPage.selectMenu(option);
        MainPage.selectSubMenu(subOption);
    }
}