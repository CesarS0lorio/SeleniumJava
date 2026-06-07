import Pages.TestNG;
import Steps.MainPageSteps;
import org.testng.annotations.Test;

public class _principalTest extends TestNG {

    public static final String suite = "MainPage";

    public static final String f001 = suite + "_BaseElements";
    public static final String f002 = suite + "_SubMenuOptions";

    @Test(dataProvider="testdata", testName = f001,  groups = {"Regression", "Full"})
    public void ValidateBaseElements(String uName, String password){
        new MainPageSteps(driver);
        MainPageSteps.ValidateSite();
    }

    @Test(testName = f002,  groups = {"Regression", "Full"})
    public void ValidateSubMenuOptions(){
        new MainPageSteps(driver);
        MainPageSteps.ValidateSubMenuOptions();
    }
}



