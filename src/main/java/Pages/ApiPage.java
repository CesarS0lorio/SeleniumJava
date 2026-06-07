package Pages;

import org.openqa.selenium.WebElement;

import Resources.Reader;
import Resources.Wrapper;
import Resources.Locators.ByLocator;
import Resources.Locators.LocatorReader;

public class ApiPage {
    public static String pageName = "ApiPage";

    public static ByLocator btn200Ok = LocatorReader.getLocator(pageName, "btn200Ok");
    public static ByLocator btn204NC = LocatorReader.getLocator(pageName, "btn204NC");
    public static ByLocator btn400BR = LocatorReader.getLocator(pageName, "btn400BR");
    public static ByLocator btn500SE = LocatorReader.getLocator(pageName, "btn500SE");


    public static ByLocator txtRespTable = LocatorReader.getLocator(pageName, "txtRespTable"); 


    public static void test200(){
        Wrapper.selectElement(btn200Ok);
        Wrapper.validateTextElement(txtRespTable, Reader.getProperty(pageName,"text200Resp"));
    }

    public static void testApi200(){
        WebElement WebElebtn200Ok = Wrapper.getElement(btn200Ok);
        String url200 = WebElebtn200Ok.getAttribute("data-url");
        Wrapper.validateHttpStatusCode(url200, 200);
        Wrapper.validateHttpBodyContent(url200, Reader.getProperty(pageName,"text200Resp"));
    }

    public static void test204(){
        Wrapper.selectElement(btn204NC);
        Wrapper.validateTextElement(txtRespTable, Reader.getProperty(pageName,"text204Resp"));
    }

    public static void testApi204(){
        WebElement WebElebtn204NC = Wrapper.getElement(btn204NC);
        String url204 = WebElebtn204NC.getAttribute("data-url");
        Wrapper.validateHttpStatusCode(url204, 204);
    }
 
   public static void testApi400(){
        WebElement WebElebtn400BR = Wrapper.getElement(btn400BR);
        String url400 = WebElebtn400BR.getAttribute("data-url");
        Wrapper.validateHttpStatusCode(url400, 400);
    }
 
       public static void testApi500(){
        WebElement WebElebtn500SE = Wrapper.getElement(btn500SE);
        String url500 = WebElebtn500SE.getAttribute("data-url");
        Wrapper.validateHttpStatusCode(url500, 500);
    }
 

}