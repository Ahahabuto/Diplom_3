import PageObjects.MainPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

public class ConstructorSectionsTest {
    private WebDriver driver;
    private final String URL = "https://stellarburgers.nomoreparties.site";

    @Before
    public void setUp() {
        driver = BrowserFactory.createWebDriver();
        driver.get(URL);
    }

    @Test
    public void shouldGoToBunsSection() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickFillingsSection();
        mainPage.clickBunsSection();

        assertTrue("Раздел булки активен", mainPage.isBunsSectionActive());
    }

    @Test
    public void shouldGoToSaucesSection() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickSaucesSection();

        assertTrue("Раздел соусы активен", mainPage.isSaucesSectionActive());
    }

    @Test
    public void shouldGoToFillingsSection() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickFillingsSection();

        assertTrue("Раздел начинки активен", mainPage.isFillingsSectionActive());
    }
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}