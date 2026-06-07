package Resources.Locators;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class PageLocators {
    private Map<String, Locator> LoginPage;
    private Map<String, Locator> HomePage;
    private Map<String, Locator> MainPage;
    private Map<String, Locator> ElementsPage;
}