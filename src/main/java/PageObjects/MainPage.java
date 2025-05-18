package PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    private final By profileButton = By.xpath("//p[text()='Личный Кабинет']");

    private final By bunsSection = By.xpath("//div[contains(@class, 'tab') and .//span[text()='Булки']]");
    private final By saucesSection = By.xpath("//div[contains(@class, 'tab') and .//span[text()='Соусы']]");
    private final By fillingsSection = By.xpath("//div[contains(@class, 'tab') and .//span[text()='Начинки']]");

    private final By activeBunsSection = By.xpath("//div[contains(@class, 'current')]//span[text()='Булки']");
    private final By activeSaucesSection = By.xpath("//div[contains(@class, 'current')]//span[text()='Соусы']");
    private final By activeFillingsSection = By.xpath("//div[contains(@class, 'current')]//span[text()='Начинки']");


    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    private void waitAndClick(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    private boolean isElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
    }

    @Step("Нажатие кнопки Войти")
    public void clickLoginButton() {
        waitAndClick(loginButton);
    }

    @Step("Нажатие кнопки Личный кабинет")
    public void clickProfileButton() {
        waitAndClick(profileButton);
    }

    @Step("Нажатие раздела Булки")
    public void clickBunsSection() {
        waitAndClick(bunsSection);
    }

    @Step("Нажатие раздела Соусы")
    public void clickSaucesSection() {
        waitAndClick(saucesSection);
    }

    @Step("Нажатие раздела Начинки")
    public void clickFillingsSection() {
        waitAndClick(fillingsSection);
    }

    @Step("Активен ли раздел Булки")
    public boolean isBunsSectionActive() {
        return isElementVisible(activeBunsSection);
    }

    @Step("Активен ли раздел Соусы")
    public boolean isSaucesSectionActive() {
        return isElementVisible(activeSaucesSection);
    }

    @Step("Активен ли раздел Начинки")
    public boolean isFillingsSectionActive() {
        return isElementVisible(activeFillingsSection);
    }

}