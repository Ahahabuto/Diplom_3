import PageObjects.MainPage;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class ConstructorSectionsTest {
    private WebDriver driver;
    private final String browserName;
    private final String URL = "https://stellarburgers.nomoreparties.site";

    public ConstructorSectionsTest(String browserName) {
        this.browserName = browserName;
    }

    @Parameterized.Parameters(name = "{0} browser")
    public static Object[][] browsers() {
        return new Object[][]{
                {"chrome"},
                {"yandex"}
        };
    }

    @Before
    @Step
    public void setUp() {
        driver = Browsers.createWebDriver(browserName);
        driver.get(URL);
    }

    @Test
    @Step("Переход в булки")
    public void shouldGoToBunsSection() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickFillingsSection();
        mainPage.clickBunsSection();

        assertTrue("Раздел булки активен", mainPage.isBunsSectionActive());
    }

    @Test
    @Step("переход в соусы")
    public void shouldGoToSaucesSection() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickSaucesSection();

        assertTrue("Раздел соусы активен", mainPage.isSaucesSectionActive());
    }

    @Test
    @Step("Переход в начинки")
    public void shouldGoToFillingsSection() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickFillingsSection();

        assertTrue("Раздел начинки активен", mainPage.isFillingsSectionActive());
    }
    @After
    @Step
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}