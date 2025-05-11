import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Browsers {

    private static final String YANDEX_DRIVER_PATH = "D:\\Yandex_WebDriver\\yandexdriver-25.4.0.1973-win64\\yandexdriver.exe";
    private static final String YANDEX_BROWSER_PATH = "C:\\Users\\Tony\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe";

    public static WebDriver createWebDriver(String browserName) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");


        switch (browserName.toLowerCase()) {
            case "yandex":
                System.setProperty("webdriver.chrome.driver", YANDEX_DRIVER_PATH);
                options.setBinary(YANDEX_BROWSER_PATH);
                return new ChromeDriver(options);
            case "chrome":
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver(options);
            default:
                throw new IllegalArgumentException("Неподдерживаемый браузер: " + browserName);
        }
    }
}