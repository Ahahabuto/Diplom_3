package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PasswordRecoveryPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By authLink = By.xpath("//a[text()='Войти']");
    private final By emailInput = By.xpath("//label[text()='Email']/../input");
    private final By recoverButton = By.xpath("//button[text()='Восстановить']");
    private final By passwordRecoveryText = By.xpath("//h2[contains(text(), 'Восстановление пароля')]");

    public PasswordRecoveryPage(WebDriver driver) {
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

    public void setEmail(String email) {
        waitAndSendKeys(emailInput, email);
    }

    public void clickRecoverButton() {
        waitAndClick(recoverButton);
    }

    public void clickAuthLink() {
        waitAndClick(authLink);
    }

    public boolean isPasswordRecoveryTextDisplayed() {
        return isElementVisible(passwordRecoveryText);
    }

    public boolean isAuthLinkDisplayed() {
        return isElementVisible(authLink);
    }

}