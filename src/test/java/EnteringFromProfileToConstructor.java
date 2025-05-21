import PageObjects.LoginPage;
import PageObjects.MainPage;
import PageObjects.ProfilePage;
import api.DataGenerator;
import api.UserApi;
import api.UserData;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

public class EnteringFromProfileToConstructor {
    private WebDriver driver;
    private UserData user;
    private String accessToken;
    private final String URL = "https://stellarburgers.nomoreparties.site";

    @Before
    public void setUp() {
        user = DataGenerator.generateUser();
        Response response = UserApi.register(user);
        accessToken = response
                .then()
                .extract().path("accessToken");

        if (accessToken == null) {
            response = UserApi.login(user);
            accessToken = response
                    .then()
                    .extract().path("accessToken");
        }

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
    public void shouldEnterViaConstructorButton() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileButton();


        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickConstructorButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.isOrderButtonDiplayed();

        assertTrue("Переход удался", loginPage.isOrderButtonDiplayed());
    }

    @Test
    public void shouldEnterViaLogo() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileButton();

        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickHeaderLogo();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.isOrderButtonDiplayed();

        assertTrue("Переход удался", loginPage.isOrderButtonDiplayed());
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