package Flows;

import Pages.ApiPage;

public class ApiFlows {
    
 public static void test200(){
        ApiPage.test200();
        ApiPage.testApi200();
    }

    public static void test204(){
        ApiPage.test204();
        ApiPage.testApi204();
    }

    public static void test400(){
        ApiPage.testApi400();
    }

    public static void test500(){
        ApiPage.testApi500();
    }

}
