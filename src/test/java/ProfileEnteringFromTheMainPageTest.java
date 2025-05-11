import PageObjects.LoginPage;
import PageObjects.MainPage;
import PageObjects.ProfilePage;
import api.UserApi;
import api.UserData;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class ProfileEnteringFromTheMainPageTest {
    private WebDriver driver;
    private final String browserName;
    private final String email = "tonytonychopper@doctor.com";
    private final String name = "Tony Tone Chopper";
    private final String password = "BokuWaDocta";
    private String accessToken;
    private final String URL = "https://stellarburgers.nomoreparties.site";

    public ProfileEnteringFromTheMainPageTest(String browserName) {
        this.browserName = browserName;
    }

    @Parameterized.Parameters(name = "{0} browser")
    public static Object[][] browsers() {
        return new Object[][] {
                {"chrome"},
                {"yandex"}
        };
    }

    @Before
    @Step("Создание пользователя и вход")
    public void setUp() {
        UserData user = new UserData(email, password, name);
        accessToken = UserApi.register(user)
                .then()
                .extract().path("accessToken");

        driver = Browsers.createWebDriver(browserName);
        driver.get(URL);

        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickLoginButton();
    }

    @Test
    @Step("Проверка перехода в ЛК")
    public void shouldEnterToProfilePage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileButton();

        ProfilePage profilePage = new ProfilePage(driver);
        assertTrue("Удалось перейти в лк", profilePage.isLogoutButtonDisplayed());
    }

    @After
    @Step
    public void tearDown() {
        if (accessToken != null) {
            UserApi.delete(accessToken);
        }
        if (driver != null) {
            driver.quit();
        }
    }
}