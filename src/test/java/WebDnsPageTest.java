import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        logger.info("Драйвер стартовал!");
    }

    @Test
    public void presenceReferencesAndNumberCase1(){

        driver.get("https://www.dns-shop.ru/");

        logger.info("Открыта страница DNS - " + "https://www.dns-shop.ru/");
        logger.info("title - " + driver.getTitle().toString());
        logger.info("current URL - " + driver.getCurrentUrl().toString());
        logger.info(String.format("Ширина окна: %d", driver.manage().window().getSize().getWidth()));
        logger.info(String.format("Высота окна: %d", driver.manage().window().getSize().getHeight()));

        selectSuggestedLocation(driver);

        /*Найти ссылку "Бытовая техника" и перейти по ней*/
        try {
            String xpathHouseholdApplianceLink = ".//a[text()=\"Бытовая техника\"]";
            WebElement elementHouseholdApplianceLink = driver.findElement(By.xpath(xpathHouseholdApplianceLink));
            elementHouseholdApplianceLink.click();
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(1000))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png",new File("temp//case1//HouseholdApplianceFullPage.png"));
        } catch (NoSuchElementException e) {
            logger.error("Не найдена сслыка 'Бытовая техника'");
        } catch (IOException e){
            e.printStackTrace();
        }


        /*Проверить, что отображается текст "Бытовая техника"*/
        try {
            String xpathHouseholdApplianceText = ".//h1[@class='subcategory__page-title' and text()= \"Бытовая техника\"]";
            WebElement elementHouseholdApplianceText = driver.findElement(By.xpath(xpathHouseholdApplianceText));
            Assertions.assertTrue(elementHouseholdApplianceText.isDisplayed(), "Текст 'Бытовая техника' не отображается");
        } catch (NoSuchElementException e) {
            logger.error("Не найден текст 'Бытовая техника'");
        }

        /*Перейти по ссылке Техника для кухни*/
        try {
            String xpathKitchenAppliancesLink = ".//a[label[text() = \"Техника для кухни\"]]";
            WebElement elementKitchenAppliancesLink = driver.findElement(By.xpath(xpathKitchenAppliancesLink));
            elementKitchenAppliancesLink.click();
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(1000))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png",new File("temp//case1//KitchenAppliancesFullPage.png"));
        } catch (NoSuchElementException e) {
            logger.error("Не найдена сслыка 'Техника для кухни'");
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*Проверить, что отображается текст Техника для кухни*/
        try {
            String xpathKitchenAppliancesText = ".//h1[@class='subcategory__page-title' and text()= \"Техника для кухни\"]";
            WebElement elementKitchenAppliancesText = driver.findElement(By.xpath(xpathKitchenAppliancesText));
            Assertions.assertTrue(elementKitchenAppliancesText.isDisplayed(), "Текст 'Техника для кухни' не отображается");
        } catch (NoSuchElementException e) {
            logger.error("Не найден текст 'Техника для кухни'");
        }

        /*Проверить, что отображается ссылка Собрать свою кухню*/
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
            By xpathAssembleYourKitchenLink = By.xpath(".//a[text()=\"Собрать свою кухню\"]");
            wait.until(ExpectedConditions.elementToBeClickable(xpathAssembleYourKitchenLink));
            WebElement elementAssembleYourKitchenLink = driver.findElement(xpathAssembleYourKitchenLink);
            Assertions.assertTrue(elementAssembleYourKitchenLink.isDisplayed(), "Текст 'Собрать свою кухню' не отображается");
            new Actions(driver)
                    .scrollToElement(elementAssembleYourKitchenLink)
                    .perform();
            elementAssembleYourKitchenLink.click();
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(1000))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png",new File("temp//case1//AssembleYourKitchenFullPage.png"));
        } catch (NoSuchElementException e) {
            logger.error("Не найден текст 'Собрать свою кухню'");
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*Найти все категории и вывести в логи*/
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        By xpathCategory = By.xpath(".//div[@class='content-categories__item-text']");
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(xpathCategory));
        List<WebElement> elementsCategories = driver.findElements(xpathCategory);
        for(WebElement webElement:elementsCategories){
            logger.info("category - " + webElement.getText());
        }
        Assertions.assertTrue(elementsCategories.size()>5, "Категорий НЕ больше 5!");
    }

    @Test
    public void menuAndNumberOfItemsCase2(){

        driver.get("https://www.dns-shop.ru/");

        logger.info("Открыта страница DNS - " + "https://www.dns-shop.ru/");

        selectSuggestedLocation(driver);

        Actions actions = new Actions(driver);

        List<String> xpathSubmenuItems= new ArrayList<String>();
        xpathSubmenuItems.add(".//a[text()=\"Бытовая техника\"]");
        xpathSubmenuItems.add(".//a[text() = \"Плиты и печи\"]");


        /*Навести курсор на ссылку Бытовая техника*/
        try {
            WebElement elementHouseholdApplianceLink = driver.findElement(By.xpath(xpathSubmenuItems.get(0)));
            goToSubmenu(driver, actions, xpathSubmenuItems, 1);
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(1000))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png",new File("temp//case2//MoveToHouseholdApplianceFullPage.png"));
            goToSubmenu(driver, actions, xpathSubmenuItems, 1);
        } catch (NoSuchElementException e) {
            logger.error("Не найдена сслыка 'Бытовая техника'");
        } catch (IOException e) {
            e.printStackTrace();
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        By xpathMenuDiv = By.xpath(".//div[@class = 'menu-desktop__submenu menu-desktop__submenu_top']");
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(xpathMenuDiv));

        /*Проверить, что отображаются ссылки: Техника для кухни*/
        try {
            String xpathKitchenAppliancesLink = ".//a[text()=\"Техника для кухни\"]";
            WebElement elementKitchenAppliancesLink = driver.findElement(By.xpath(xpathKitchenAppliancesLink));
            Assertions.assertTrue(elementKitchenAppliancesLink.isDisplayed(), "Ссылка 'Техника для кухни' не отображается");
        } catch (NoSuchElementException e) {
            logger.error("Не найдена ссылка 'Техника для кухни'");
        }

        /*Проверить, что отображаются ссылки: Техника для дома*/
        try {
            String xpathHomeAppliancesLink = ".//a[text()=\"Техника для дома\"]";
            WebElement elementHomeAppliancesLink = driver.findElement(By.xpath(xpathHomeAppliancesLink));
            Assertions.assertTrue(elementHomeAppliancesLink.isDisplayed(), "Ссылка 'Техника для дома' не отображается");
        } catch (NoSuchElementException e) {
            logger.error("Не найдена ссылка 'Техника для дома'");
        }

        /*Проверить, что отображаются ссылки: Красота и здоровье*/
        try {
            String xpathHealthAndBeautyLink = ".//a[text()=\"Красота и здоровье\"]";
            WebElement elementHealthAndBeautyLink = driver.findElement(By.xpath(xpathHealthAndBeautyLink));
            Assertions.assertTrue(elementHealthAndBeautyLink.isDisplayed(), "Ссылка 'Красота и здоровье' не отображается");
        } catch (NoSuchElementException e) {
            logger.error("Не найдена ссылка 'Красота и здоровье'");
        }

        /*Навести курсор на ссылку Плиты и печи*/
        try {
           goToSubmenu(driver, actions, xpathSubmenuItems, 2);
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(1000))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png",new File("temp//case2//MoveToStovesAndOvensFullPage.png"));
            goToSubmenu(driver, actions, xpathSubmenuItems, 2);
        } catch (NoSuchElementException e) {
            logger.error("Не найдена ссылка 'Плиты и плечи'");
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*Проверить, что количество ссылок в подменю Плиты и печи больше 5*/
        try{
            By xpathStovesAndOvensItemsLinks = By.xpath(".//a[text() = \"Плиты и печи\"]/descendant::a[@class='ui-link menu-desktop__popup-link']");
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(xpathStovesAndOvensItemsLinks));
            List<WebElement> elementsLinks = driver.findElements(xpathStovesAndOvensItemsLinks);
            Assertions.assertTrue(elementsLinks.size() > 5, "В подменю 'Плиты и печи' ссылок не больше 5");
        } catch (NoSuchElementException e) {
            logger.error("Не найдены ссылки в подменю 'Плиты и печи'");
        }

        /*Перейти по ссылке Плиты электрические*/
        try {

            By xpathElectricStovesLink = By.xpath(".//a[text() = \"Плиты электрические\"]");
            WebElement elementElectricStovesLink = driver.findElement(xpathElectricStovesLink);
            wait.until(ExpectedConditions.elementToBeClickable(xpathElectricStovesLink));
            elementElectricStovesLink.click();
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(1000))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png",new File("temp//case2//ClickElectricStovesFullPage.png"));
        } catch (NoSuchElementException e) {
            logger.error("Не найдена ссылка 'Плиты электрические'");
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*Проверить, что в тексте Плиты электрические [количество] товаров количество товаров больше 100*/
        try {
            String xpathProductsCountSpan = ".//span[@class='products-count']";
            WebElement elementProductsCountSpan = driver.findElement(By.xpath(xpathProductsCountSpan));
            Assertions.assertTrue(selectNumberFromString(elementProductsCountSpan.getText()) > 100, "Электрических плит НЕ больше 100");
        } catch (NoSuchElementException e) {
            logger.error("Не найдено значение количества товаров");
        }
    }
    @Test
    public void sortingAndNoBlockCase3() {

        driver.get("https://www.dns-shop.ru/");

        logger.info("Открыта страница DNS - " + "https://www.dns-shop.ru/");

        selectSuggestedLocation(driver);

        Actions actions = new Actions(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try{
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(1000))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png",new File("temp//case3//DNSFullPage.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String xpathPCsLaptopsPeripheralsLink = ".//a[text() = 'ПК, ноутбуки, периферия']";
            WebElement elementPCsLaptopsPeripheralsLink = driver.findElement(By.xpath(xpathPCsLaptopsPeripheralsLink));
            actions.moveToElement(elementPCsLaptopsPeripheralsLink).perform();
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(1000))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png",new File("temp//case3//PCsLaptopsPeripheralsFullPage.png"));
            new Actions(driver)
                    .scrollToElement(driver.findElement(By.xpath(xpathPCsLaptopsPeripheralsLink)))
                    .perform();
            actions.moveToElement(elementPCsLaptopsPeripheralsLink).perform();
        } catch (NoSuchElementException e){
            logger.error("Не найдена ссылка 'ПК, ноутбуки, периферия'");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            By xpathLaptopsLink = By.xpath(".//a[text() = 'Ноутбуки']");
            WebElement elementLaptopsLink = driver.findElement(xpathLaptopsLink);
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(xpathLaptopsLink));
            elementLaptopsLink.click();
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(1000))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png",new File("temp//case3//LaptopsFullPage.png"));
        } catch (NoSuchElementException e) {
            logger.error("Не найдена ссылка 'Ноутбуки'");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String xpathVisibilityHeader = "//header";
            WebElement elementVisibilityHeader = driver.findElement(By.xpath(xpathVisibilityHeader));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String script = "arguments[0].style.visibility='hidden';";
            js.executeScript(script, elementVisibilityHeader);
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(1000))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png",new File("temp//case3//DNSWithoutHeaderFullPage.png"));
            actions.scrollToElement(driver.findElement(By.xpath(xpathVisibilityHeader))).perform();
        } catch (NoSuchElementException e) {
            logger.error("Не найден элемент 'Header'");
        } catch (IOException e) {
            e.printStackTrace();
        }


        try{
            By xpathFilterAsus = By.xpath(".//input[@value ='asus']/ancestor::label[1]");
            WebElement elementFilterAsus  = driver.findElement(xpathFilterAsus);
            actions.scrollToElement(elementFilterAsus);
            wait.until(ExpectedConditions.elementToBeClickable(xpathFilterAsus));
            elementFilterAsus.click();
        } catch (NoSuchElementException e) {
            logger.error("Не найдено значение фиьтра 'Asus'");
        }

        try {
            By xpathRAMDropdownList = By.xpath(".//span[text()='Объем оперативной памяти (ГБ)']/ancestor::a[@class = 'ui-link ui-collapse__link_left ui-collapse__link ui-collapse__link_list']");
            WebElement elementRAMDropdownList = driver.findElement(xpathRAMDropdownList);
            actions.scrollToElement(elementRAMDropdownList).perform();
            wait.until(ExpectedConditions.elementToBeClickable(xpathRAMDropdownList));
            elementRAMDropdownList.click();
        } catch (NoSuchElementException e) {
            logger.error("Не найден выпадабщий список 'Объем оперативной памяти (ГБ)'");
        }

        try {
            By xpathFilterRAM32 = By.xpath(".//input[@value ='26ob']/ancestor::label[1]");
            WebElement elementFilterRAM32 = driver.findElement(xpathFilterRAM32);
            wait.until(ExpectedConditions.elementToBeClickable(xpathFilterRAM32));
            elementFilterRAM32.click();
        } catch (NoSuchElementException e) {
            logger.error("Не найдено значение фиьтра '32 ГБ'");
        }

        try {
            String xpathButtonApply = ".//button[text() = 'Применить']";
            WebElement elementButtonApply = driver.findElement(By.xpath(xpathButtonApply));
            actions.scrollToElement(elementButtonApply).perform();
            elementButtonApply.click();
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(1000))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png",new File("temp//case3//LaptopsWithFiltersFullPage.png"));
        } catch (NoSuchElementException e) {
            logger.error("Кнопка 'Применить' не найдена");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            String xpathSortDropdown = ".//span[text() = 'Сначала недорогие']/ancestor::a[@class = 'ui-link ui-link_blue']";
            WebElement elementSortDropdown = driver.findElement(By.xpath(xpathSortDropdown));
            actions.scrollToElement(elementSortDropdown);
            elementSortDropdown.click();
        } catch (NoSuchElementException e) {
            logger.error("Выпадающий список сортировки не найден");
        }

        try{
            By xpathFirstExpensiveOnesSort = By.xpath(".//input[@value = '2']/ancestor::label[1]");
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(xpathFirstExpensiveOnesSort));
            WebElement elementFirstExpensiveOnesSort = driver.findElement(xpathFirstExpensiveOnesSort);
            elementFirstExpensiveOnesSort.click();
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(1000))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png",new File("temp//case3//LaptopsWithSortFullPage.png"));
            actions.scrollToElement(driver.findElement(By.xpath("//header")));
        } catch (NoSuchElementException e) {
            logger.error("Сортировка 'Сначала дорогие' не найдена");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String productListWindow = driver.getWindowHandle();
        try{
            String xpathFirstProductLink = ".//a[@class = \'catalog-product__name ui-link ui-link_black\']";
            WebElement elementFirstProductLink = driver.findElement(By.xpath(xpathFirstProductLink));
            actions.scrollToElement(elementFirstProductLink);
            String productUrl = elementFirstProductLink.getAttribute("href");
            logger.info(productUrl);

            driver.switchTo().newWindow(WindowType.WINDOW);
            driver.manage().window().maximize();
            driver.get(productUrl);

            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(1000))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "png",new File("temp//case3//FirstProductFullPage.png"));

        } catch (NoSuchElementException e) {
            logger.error("Продукты на странице не найдены");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String productWindow = driver.getWindowHandle();
        String titleProductPage = "";
        String titleProductInListPage = "";
        try{
            String xpathTitleProductPage = ".//h1";
            WebElement elementTitleProductPage = driver.findElement(By.xpath(xpathTitleProductPage));
            titleProductPage = elementTitleProductPage.getText();
            driver.switchTo().window(productListWindow);
        } catch (NoSuchElementException e) {
            logger.error("Заголовок не найден");
        }

        try {
            String xpathProductTitleInList = ".//a[@class = 'catalog-product__name ui-link ui-link_black']/descendant::span]";
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpathProductTitleInList)));
            WebElement elementProductTitleInList = driver.findElement(By.xpath(xpathProductTitleInList));
            String titleProductInListPageWithParams =  elementProductTitleInList.getText();
            titleProductInListPage = titleProductInListPageWithParams.substring(titleProductInListPageWithParams.indexOf("["));
            Assertions.assertTrue(titleProductPage.equals(titleProductInListPage), "Название НЕ соответствует названию в списке на предыдущей странице");
        } catch (NoSuchElementException e) {
            logger.error("Название продукта не найдено");
        }

        try {
            driver.switchTo().window(productWindow);
            String xpathExpandAllButton = ".//button[text() = 'Развернуть все']";
            WebElement elementExpandAllButton = driver.findElement(By.xpath(xpathExpandAllButton));
            actions.scrollToElement(elementExpandAllButton);
            elementExpandAllButton.click();
        } catch (NoSuchElementException e) {
            logger.error("Кнопка 'Развернуть все' не найдено");
        }

        try {
            String xpathTitleCharacteristics = ".//div[@class = 'product-card-description__title']";
            WebElement elementTitleCharacteristics = driver.findElement(By.xpath(xpathTitleCharacteristics));
            Assertions.assertTrue(elementTitleCharacteristics.getText().contains("ASUS"), "Название НЕ не содержит 'ASUS'");
        } catch (NoSuchElementException e) {
            logger.error("Заголовк характеристик не найден");
        }


        try{
            String xpathCharacteristicsValue = ".//div[@class = 'product-characteristics__spec-value']";
            List<WebElement> elementCharacteristicsValue = driver.findElements(By.xpath(xpathCharacteristicsValue));
            if(elementCharacteristicsValue.size() > 34){
                Assertions.assertTrue(elementCharacteristicsValue.get(35).getText().contains("32"), "Объем оперативной памяти не соответствует");
            }
        } catch (NoSuchElementException e) {
            logger.error("Ни одной характеристики не найдено");
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

    /*Вообще этот метод выглядит очень плохо, но пока выполняет свою функциональность.
    Внутри него лучше смотрелась бы рекурсия*/
    public void goToSubmenu(WebDriver driver,Actions actions, List<String> xpathSubmenuList, int nesting){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        actions.scrollToElement(driver.findElement(By.xpath(xpathSubmenuList.get(0)))).perform();
        switch (nesting) {
            case 1 :
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpathSubmenuList.get(0))));
                WebElement element1_nesting1 = driver.findElement(By.xpath(xpathSubmenuList.get(0)));
                actions.moveToElement(element1_nesting1).perform();
                break;
            case 2:
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpathSubmenuList.get(0))));
                WebElement element1_nesting2 = driver.findElement(By.xpath(xpathSubmenuList.get(0)));
                actions.moveToElement(element1_nesting2).perform();
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathSubmenuList.get(1))));
                WebElement element2_nesting2 = driver.findElement(By.xpath(xpathSubmenuList.get(1)));
                actions.moveToElement(element2_nesting2).perform();
                break;
            case 3:
                WebElement element1_nesting3 = driver.findElement(By.xpath(xpathSubmenuList.get(0)));
                actions.moveToElement(element1_nesting3).perform();
                WebElement element2_nesting3 = driver.findElement(By.xpath(xpathSubmenuList.get(1)));
                actions.moveToElement(element2_nesting3).perform();
                WebElement element3_nesting3 = driver.findElement(By.xpath(xpathSubmenuList.get(1)));
                actions.moveToElement(element3_nesting3).perform();
                break;
        }
    }
}
