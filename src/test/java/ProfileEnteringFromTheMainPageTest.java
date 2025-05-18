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

public class ProfileEnteringFromTheMainPageTest {
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
    public void shouldEnterToProfilePage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileButton();

        ProfilePage profilePage = new ProfilePage(driver);
        assertTrue("Удалось перейти в лк", profilePage.isLogoutButtonDisplayed());
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