package PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By nameInput = By.xpath("//label[text()='Имя']/../input");
    private final By emailInput = By.xpath("//label[text()='Email']/../input");
    private final By passwordInput = By.xpath("//label[text()='Пароль']/../input");

    private final By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    private final By passwordError = By.xpath("//p[contains(text(), 'Некорректный пароль')]");
    private final By authLink = By.xpath("//a[text()='Войти']");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    private void waitAndSendKeys(By locator, String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(text);
    }

    private void waitAndClick(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    private boolean isElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
    }

    @Step("Ввод имени")
    public void setName(String name) {
        waitAndSendKeys(nameInput, name);
    }

    @Step("Ввод email")
    public void setEmail(String email) {
        waitAndSendKeys(emailInput, email);
    }

    @Step("Ввод пароля")
    public void setPassword(String password) {
        waitAndSendKeys(passwordInput, password);
    }

    @Step("Нажатие кнопки зарегистрироваться")
    public void clickRegisterButton() {
        waitAndClick(registerButton);
    }

    @Step("Нажатие ссылки Войти")
    public void clickAuthLink() {
        waitAndClick(authLink);
    }

    @Step("Проверка видимости сообщения об ошибки в написании пароля")
    public boolean isPasswordErrorDisplayed() {
        return isElementVisible(passwordError);
    }

    @Step("Проверка видимости ссылки Войти")
    public boolean isLoginLinkDisplayed() {
        return isElementVisible(authLink);
    }
}