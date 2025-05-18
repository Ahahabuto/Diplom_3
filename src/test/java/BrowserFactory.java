import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BrowserFactory {

    private static final String PROPERTIES_FILE = "browserconfig.properties";
    private static final String DEFAULT_BROWSER = "chrome";

    public static WebDriver createWebDriver() {
        String browserName = getBrowserNameFromProperties();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        switch (browserName.toLowerCase()) {
            case "yandex" :
                return createYandexDriver(options);
            case "chrome" :
            default:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver(options);
        }
    }

    private static WebDriver createYandexDriver(ChromeOptions options) {
        Properties properties = loadProperties();
        String driverPath = properties.getProperty("yandex.driver.path");
        String browserPath = properties.getProperty("yandex.browser.path");

        if (driverPath != null && browserPath != null) {
            System.setProperty("webdriver.chrome.driver", driverPath);
            options.setBinary(browserPath);
            return new ChromeDriver(options);
        }
        throw new RuntimeException("Yandex browser paths not configured in properties");
    }

    private static String getBrowserNameFromProperties() {
        Properties properties = loadProperties();
        return properties.getProperty("browser", DEFAULT_BROWSER);
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = BrowserFactory.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)){
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}