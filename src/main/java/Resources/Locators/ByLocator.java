package Resources.Locators;

import org.openqa.selenium.By;

public class ByLocator {
    private final By by;
    private final String description;

    public ByLocator(By by, String description) {
        this.by = by;
        this.description = description;
    }

    public By getBy() {
        return by;
    }

    public String getDescription() {
        return description;
    }
}