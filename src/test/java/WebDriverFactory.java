import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverFactory {

    private static Logger logger = LogManager.getLogger(WebDriverFactory.class);

    // Возврат драйвера для конкретного браузера по его названию
    public static WebDriver getDriver(String browserName, String loadStrategyName) {
        switch (browserName) {
            // Браузеры
            // Создание драйвера для браузера Google Chrome
            case "chrome":
                WebDriverManager.chromedriver().setup();
                logger.info("Драйвер для браузера Google Chrome");

                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-fullscreen");
                chromeOptions.addArguments("--incognito");

                chromeOptions.setPageLoadStrategy(PageLoadStrategy.valueOf(loadStrategyName.toUpperCase()));

                return new ChromeDriver(chromeOptions);
            // Создание драйвера для браузера Mozilla Firefox
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                logger.info("Драйвер для браузера Mozilla Firefox");

                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--kiosk");
                firefoxOptions.addArguments("--private");

                firefoxOptions.setPageLoadStrategy(PageLoadStrategy.valueOf(loadStrategyName.toUpperCase()));


                return new FirefoxDriver(firefoxOptions);
            // Ответ по умолчанию, если введено некорректное название браузера
            default:
                throw new RuntimeException("Введено некорректное название браузера");
        }
    }
}
