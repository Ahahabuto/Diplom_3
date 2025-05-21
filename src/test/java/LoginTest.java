import PageObjects.LoginPage;
import PageObjects.MainPage;
import PageObjects.PasswordRecoveryPage;
import PageObjects.RegistrationPage;
import api.DataGenerator;
import api.UserApi;
import api.UserData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

public class LoginTest {
    private WebDriver driver;
    private UserData user;
    private String accessToken;
    private final String URL = "https://stellarburgers.nomoreparties.site/";

    @Before
    public void setUp() {
        driver = BrowserFactory.createWebDriver();
        driver.get(URL);
        user = DataGenerator.generateUser();
        accessToken = UserApi.register(user)
                .then()
                .extract().path("accessToken");
    }

    @Test
    public void loginViaMainPageLoginButtonTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.setEmail(user.getEmail());
        loginPage.setPassword(user.getPassword());
        loginPage.clickLoginButton();

        assertTrue("Отображается кнопка 'Оформить заказ'", loginPage.isOrderButtonDiplayed());
    }

    @Test
    public void loginViaRegistrationFormButtonTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickAuthLink();;

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.clickAuthLink();

        loginPage.setEmail(user.getEmail());
        loginPage.setPassword(user.getPassword());
        loginPage.clickLoginButton();

        assertTrue("Отображается кнопка 'Оформить заказ'", loginPage.isOrderButtonDiplayed());
    }

    @Test
    public void loginViaProfileButtonTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.setEmail(user.getEmail());
        loginPage.setPassword(user.getPassword());
        loginPage.clickLoginButton();

        assertTrue("Отображается кнопка 'Оформить заказ'", loginPage.isOrderButtonDiplayed());
    }

    @Test
    public void loginViaPasswordRecoveryFormTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickPasswordRecoveryLink();

        PasswordRecoveryPage passwordRecoveryPage = new PasswordRecoveryPage(driver);
        passwordRecoveryPage.clickAuthLink();

        loginPage.setEmail(user.getEmail());
        loginPage.setPassword(user.getPassword());
        loginPage.clickLoginButton();

        assertTrue("Отображается кнопка 'Оформить заказ'", loginPage.isOrderButtonDiplayed());
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