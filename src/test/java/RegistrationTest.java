import PageObjects.LoginPage;
import PageObjects.MainPage;
import PageObjects.RegistrationPage;
import api.DataGenerator;
import api.UserApi;
import api.UserData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

public class RegistrationTest {
    private WebDriver driver;
    private String accessToken;
    private final String URL = "https://stellarburgers.nomoreparties.site";
    private UserData user;
    private UserData shortPasswordUser;


    @Before
    public void setUp() {
        driver = BrowserFactory.createWebDriver();
        driver.get(URL);
        user = DataGenerator.generateUser();
        shortPasswordUser = new UserData(
                DataGenerator.generateEmail(),
                DataGenerator.generateWeakPassword(),
                DataGenerator.generateName()
        );
    }

    @Test
    public void successfulRegistrationTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickAuthLink();

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.setName(user.getName());
        registrationPage.setEmail(user.getEmail());
        registrationPage.setPassword(user.getPassword());
        registrationPage.clickRegisterButton();

        assertTrue("Появилась кнопка входа", registrationPage.isLoginLinkDisplayed());

        accessToken = UserApi.register(user)
                .then()
                .log().all()
                .extract().path("accessToken");
    }

    @Test
    public void registrationWithShortPasswordTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickAuthLink();;

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.setName(shortPasswordUser.getName());
        registrationPage.setEmail(shortPasswordUser.getEmail());
        registrationPage.setPassword(shortPasswordUser.getPassword());
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