package PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By emailInput = By.xpath("//label[text()='Email']/../input");
    private final By passwordInput = By.xpath("//label[text()='Пароль']/../input");
    private final By loginButton = By.xpath("//button[contains(text(), 'Войти')]");
    private final By authLink = By.xpath("//a[text()='Зарегистрироваться']");
    private final By passwordRecoveryLink = By.xpath("//a[text()='Восстановить пароль']");
    private final By orderButton = By.xpath("//button[contains(text(), 'Оформить заказ')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    private void waitAndClick(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    private void waitAndSendKeys(By locator, String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(text);
    }

    private boolean isElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
    }

    @Step("Ввод email")
    public void setEmail(String email) {
        waitAndSendKeys(emailInput, email);
    }

    @Step("Ввод пароля")
    public void setPassword (String password) {
        waitAndSendKeys(passwordInput, password);
    }

    @Step("Нажатие кнопки Войти")
    public void clickLoginButton () {
        waitAndClick(loginButton);
    }

    @Step("Нажатие на ссылку Зарегистрироваться")
    public void clickAuthLink() {
        waitAndClick(authLink);
    }

    @Step("Нажатие на ссылку Восстановление пароля")
    public void clickPasswordRecoveryLink() {
        waitAndClick(passwordRecoveryLink);
    }

    @Step("Проверка видимости кнопки Оформить заказ")
    public boolean isOrderButtonDiplayed() {
        return isElementVisible(orderButton);
    }

    @Step("Проверка видимости кнопки Войти")
    public boolean isLoginButtonDisplayed() {
        return isElementVisible(loginButton);
    }
}