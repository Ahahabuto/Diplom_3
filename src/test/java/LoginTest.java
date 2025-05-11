import PageObjects.LoginPage;
import PageObjects.MainPage;
import PageObjects.PasswordRecoveryPage;
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
public class LoginTest {
    private WebDriver driver;
    private final String browserName;
    private final String email = "mugiwarano@luffy.com";
    private final String name = "Luffy";
    private final String password = "Gomugomu";
    private String accessToken;
    private final String URL = "https://stellarburgers.nomoreparties.site/";

    public LoginTest(String browserName) {
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
    @Step
    public void setUp() {
        UserData user = new UserData(email, password, name);
        accessToken = UserApi.register(user)
                .then()
                .extract().path("accessToken");

        driver = Browsers.createWebDriver(browserName);
        driver.get(URL);
    }

    @Test
    @Step ("Вход по кнопке войти в акк на главной странице")
    public void loginViaMainPageLoginButtonTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickLoginButton();

        assertTrue("Отображается кнопка 'Оформить заказ'", loginPage.isOrderButtonDiplayed());
    }

    @Test
    @Step("Вход через кнопку в форме регистрации")
    public void loginViaRegistrationFormButtonTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickAuthLink();;

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.clickAuthLink();

        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickLoginButton();

        assertTrue("Отображается кнопка 'Оформить заказ'", loginPage.isOrderButtonDiplayed());
    }

    @Test
    @Step("Вход через личный кабинет")
    public void loginViaProfileButtonTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickLoginButton();

        assertTrue("Отображается кнопка 'Оформить заказ'", loginPage.isOrderButtonDiplayed());
    }

    @Test
    @Step("Вход через кнопку в форме восстановления пароля")
    public void loginViaPasswordRecoveryFormTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickPasswordRecoveryLink();

        PasswordRecoveryPage passwordRecoveryPage = new PasswordRecoveryPage(driver);
        passwordRecoveryPage.clickAuthLink();

        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickLoginButton();

        assertTrue("Отображается кнопка 'Оформить заказ'", loginPage.isOrderButtonDiplayed());
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