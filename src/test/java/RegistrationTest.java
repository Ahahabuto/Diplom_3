import PageObjects.LoginPage;
import PageObjects.MainPage;
import PageObjects.RegistrationPage;
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
public class RegistrationTest {
    private WebDriver driver;
    private final String browserName;
    private final String email = "tonytonychopper@doctor.com";
    private final String name = "Tony Tone Chopper";
    private final String password = "BokuWaDocta";
    private final String shortPassword = "short";
    private String accessToken;
    private final String URL = "https://stellarburgers.nomoreparties.site";

    public RegistrationTest(String browserName) {
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
    public void setUp() {
        driver = Browsers.createWebDriver(browserName);
        driver.get(URL);
    }

    @Test
    @Step("Успешная регистрация")
    public void successfulRegistrationTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickAuthLink();

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.setName(name);
        registrationPage.setEmail(email);
        registrationPage.setPassword(password);
        registrationPage.clickRegisterButton();

        assertTrue("Появилась кнопка входа", registrationPage.isLoginLinkDisplayed());

        UserData user = new UserData(email, password, name);
        accessToken = UserApi.register(user)
                .then()
                .log().all()
                .extract().path("accessToken");
    }

    @Test
    @Step
    public void registrationWithShortPasswordTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickAuthLink();;

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.setName(name);
        registrationPage.setEmail(email);
        registrationPage.setPassword(shortPassword);
        registrationPage.clickRegisterButton();

        assertTrue("Отображается сообщение об ошибке", registrationPage.isPasswordErrorDisplayed());
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            UserApi.delete(accessToken);
        }
        if (driver != null) {
            driver.quit();
        }
    }
}