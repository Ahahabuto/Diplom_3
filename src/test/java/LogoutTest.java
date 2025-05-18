import PageObjects.LoginPage;
import PageObjects.MainPage;
import PageObjects.ProfilePage;
import api.DataGenerator;
import api.UserApi;
import api.UserData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

public class LogoutTest {
    private WebDriver driver;
    private UserData user;
    private String accessToken;
    private final String URL = "https://stellarburgers.nomoreparties.site";


    @Before
    public void setUp() {
        user = DataGenerator.generateUser();
        accessToken = UserApi.register(user)
                .then()
                .extract().path("accessToken");

        driver = BrowserFactory.createWebDriver();
        driver.get(URL);

        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.setEmail(user.getEmail());
        loginPage.setPassword(user.getPassword());
        loginPage.clickLoginButton();
    }

    @Test
    public void shouldLogoutSuccessfully() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileButton();

        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickLogoutButton();

        LoginPage loginPage = new LoginPage(driver);
        assertTrue("Успешный выход из ЛК", loginPage.isLoginButtonDisplayed());
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