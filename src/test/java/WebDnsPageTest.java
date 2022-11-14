import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.Arrays;
import java.util.List;

public class WebDnsPageTest {

    // Объявление драйвера и логгера
    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(WebDnsPageTest.class);

    // Считывание входных параметров
    String browserEnv = System.getProperty("browser", "chrome");
    String loadStrategyEnv = System.getProperty("loadstrategy", "normal");


    @BeforeEach
    public void setUp() {

        logger.info("browserEnv = " + browserEnv);
        logger.info("loadStrategyEnv = " + loadStrategyEnv);

        driver = WebDriverFactory.getDriver(browserEnv.toLowerCase(), loadStrategyEnv);
        logger.info("Драйвер стартовал!");
    }

    @Test
    public void presenceReferencesAndNumberCase1(){

        driver.get("https://www.dns-shop.ru/");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.info("Открыта страница DNS - " + "https://www.dns-shop.ru/");
        logger.info("title - " + driver.getTitle().toString());
        logger.info("current URL - " + driver.getCurrentUrl().toString());
        logger.info(String.format("Ширина окна: %d", driver.manage().window().getSize().getWidth()));
        logger.info(String.format("Высота окна: %d", driver.manage().window().getSize().getHeight()));

        selectSuggestedLocation(driver);

        try {
            String xpathHouseholdApplianceLink = ".//a[text()=\"Бытовая техника\"]";
            WebElement elementHouseholdApplianceLink = driver.findElement(By.xpath(xpathHouseholdApplianceLink));
            elementHouseholdApplianceLink.click();
        } catch (NoSuchElementException e) {
            logger.error("Не найдена сслыка 'Бытовая техника'");
        }

        try {
            String xpathHouseholdApplianceText = ".//h1[@class='subcategory__page-title' and text()= \"Бытовая техника\"]";
            WebElement elementHouseholdApplianceText = driver.findElement(By.xpath(xpathHouseholdApplianceText));
            Assertions.assertTrue(elementHouseholdApplianceText.isDisplayed(), "Текст 'Бытовая техника' не отображается");
        } catch (NoSuchElementException e) {
            logger.error("Не найден текст 'Бытовая техника'");
        }

        try {
            String xpathKitchenAppliancesLink = ".//a[label[text() = \"Техника для кухни\"]]";
            WebElement elementKitchenAppliancesLink = driver.findElement(By.xpath(xpathKitchenAppliancesLink));
            elementKitchenAppliancesLink.click();
        } catch (NoSuchElementException e) {
            logger.error("Не найдена сслыка 'Техника для кухни'");
        }

        try {
            String xpathKitchenAppliancesText = ".//h1[@class='subcategory__page-title' and text()= \"Техника для кухни\"]";
            WebElement elementKitchenAppliancesText = driver.findElement(By.xpath(xpathKitchenAppliancesText));
            Assertions.assertTrue(elementKitchenAppliancesText.isDisplayed(), "Текст 'Техника для кухни' не отображается");
        } catch (NoSuchElementException e) {
            logger.error("Не найден текст 'Техника для кухни'");
        }

        try {
            String xpathAssembleYourKitchenLink = ".//a[text()=\"Собрать свою кухню\"]";
            WebElement elementAssembleYourKitchenLink = driver.findElement(By.xpath(xpathAssembleYourKitchenLink));
            Assertions.assertTrue(elementAssembleYourKitchenLink.isDisplayed(), "Текст 'Собрать свою кухню' не отображается");
            elementAssembleYourKitchenLink.click();
        } catch (NoSuchElementException e) {
            logger.error("Не найден текст 'Собрать свою кухню'");
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String xpathCategory = ".//div[@class='content-categories__item-text']";
        List<WebElement> elementsCategories = driver.findElements(By.xpath(xpathCategory));
        for(WebElement webElement:elementsCategories){
            logger.info("category - " + webElement.getText());
        }
        Assertions.assertTrue(elementsCategories.size()>5, "Категорий НЕ больше 5!");
    }

    @Test
    public void menuAndNumberOfItemsCase2(){

        driver.get("https://www.dns-shop.ru/");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.info("Открыта страница DNS - " + "https://www.dns-shop.ru/");

        selectSuggestedLocation(driver);

        Actions actions = new Actions(driver);

        try {
            String xpathHouseholdApplianceLink = ".//a[text()=\"Бытовая техника\"]";
            WebElement elementHouseholdApplianceLink = driver.findElement(By.xpath(xpathHouseholdApplianceLink));
            actions.moveToElement(elementHouseholdApplianceLink).perform();
        } catch (NoSuchElementException e) {
            logger.error("Не найдена сслыка 'Бытовая техника'");
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            String xpathKitchenAppliancesLink = ".//a[text()=\"Техника для кухни\"]";
            WebElement elementKitchenAppliancesLink = driver.findElement(By.xpath(xpathKitchenAppliancesLink));
            Assertions.assertTrue(elementKitchenAppliancesLink.isDisplayed(), "Ссылка 'Техника для кухни' не отображается");
        } catch (NoSuchElementException e) {
            logger.error("Не найдена ссылка 'Техника для кухни'");
        }

        try {
            String xpathHomeAppliancesLink = ".//a[text()=\"Техника для дома\"]";
            WebElement elementHomeAppliancesLink = driver.findElement(By.xpath(xpathHomeAppliancesLink));
            Assertions.assertTrue(elementHomeAppliancesLink.isDisplayed(), "Ссылка 'Техника для дома' не отображается");
        } catch (NoSuchElementException e) {
            logger.error("Не найдена ссылка 'Техника для дома'");
        }

        try {
            String xpathHealthAndBeautyLink = ".//a[text()=\"Красота и здоровье\"]";
            WebElement elementHealthAndBeautyLink = driver.findElement(By.xpath(xpathHealthAndBeautyLink));
            Assertions.assertTrue(elementHealthAndBeautyLink.isDisplayed(), "Ссылка 'Красота и здоровье' не отображается");
        } catch (NoSuchElementException e) {
            logger.error("Не найдена ссылка 'Красота и здоровье'");
        }

        try {
            String xpathFoodPreparationLink = ".//a[text()=\"Приготовление пищи\"]";
            WebElement elementFoodPreparationLink = driver.findElement(By.xpath(xpathFoodPreparationLink));
            actions.moveToElement(elementFoodPreparationLink).perform();
        } catch (NoSuchElementException e) {
            logger.error("Не найдена ссылка 'Приготовление пищи'");
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String xpathCookedFoodComponentLink = ".//a[text()=\"Приготовление пищи\"]/descendant::a[@class='ui-link menu-desktop__popup-link']";
        List<WebElement> elementsLinks = driver.findElements(By.xpath(xpathCookedFoodComponentLink));
        Assertions.assertTrue(elementsLinks.size() > 5, "В подменю 'Приготовление пищи' ссылок не больше 5");

        try {
            String xpathPlatesLink = ".//a[text()=\"Приготовление пищи\"]/descendant::a[text()=\"Плиты\"]";
            WebElement elementPlatesLink = driver.findElement(By.xpath(xpathPlatesLink));
            actions.moveToElement(elementPlatesLink).perform();
            elementPlatesLink.click();
        } catch (NoSuchElementException e) {
            logger.error("Не найдена ссылка 'Плиты'");
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            String xpathElectricStovesLink = ".//a[label[text() = \"Плиты электрические\"]]";
            WebElement elementElectricStovesLink = driver.findElement(By.xpath(xpathElectricStovesLink));
            elementElectricStovesLink.click();
        } catch (NoSuchElementException e) {
            logger.error("Не найдена ссылка 'Плиты электрические'");
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            String xpathProductsCountSpan = ".//span[@class='products-count']";
            WebElement elementProductsCountSpan = driver.findElement(By.xpath(xpathProductsCountSpan));
            Assertions.assertTrue(selectNumberFromString(elementProductsCountSpan.getText()) > 100, "Электрических плит НЕ больше 100");
        } catch (NoSuchElementException e) {
            logger.error("Не найдено значение количества товаров");
        }
    }

    @AfterEach
    public void setDown() {
        if(driver != null) {
            driver.quit();
            logger.info("Драйвер остановлен!");
        }
    }
    public void selectSuggestedLocation (WebDriver driver){
        String xpathCityButton = ".//button[contains(@class,' base-ui-button-v2 v-confirm-city__btn')]";
        try{
            WebElement elementCityButton = driver.findElement(By.xpath(xpathCityButton));
            elementCityButton.click();
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (NoSuchElementException e){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }
    public int selectNumberFromString (String stringWithNumbers) {
        String stringWithBrackets = Arrays.toString(stringWithNumbers.split("\\D+"));
        stringWithBrackets = stringWithBrackets.replace("[","");
        stringWithBrackets = stringWithBrackets.replace("]","");
        int number = Integer.parseInt(stringWithBrackets);
        return number;
    }
}
