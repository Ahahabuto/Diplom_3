package PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By logoutButton = By.xpath("//button[text()='Выход']");
    private final By constructorButton = By.xpath("//p[text()='Конструктор']");
    private final By headerLogo = By.xpath("//div[contains(@class, 'logo')]");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    private boolean isElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
    }

    public void waitAndClick(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    @Step("Нажатие кнопки Выход")
    public void clickLogoutButton () {
        waitAndClick(logoutButton);
    }

    @Step("Нажатие кнопки Конструктор")
    public void clickConstructorButton() {
        waitAndClick(constructorButton);
    }

    @Step("Нажатие на логотип")
    public void clickHeaderLogo () {
        waitAndClick(headerLogo);
    }

    @Step("Проверка видимости кнопки Выход")
    public boolean isLogoutButtonDisplayed() {
        return isElementVisible(logoutButton);
    }
}