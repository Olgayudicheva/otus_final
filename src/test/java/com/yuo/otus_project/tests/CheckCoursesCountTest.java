package com.yuo.otus_project.tests;

import com.yuo.otus_project.WebDriverFactory;
import com.yuo.otus_project.page_objects.MainPageWithoutAuth;
import com.yuo.otus_project.page_objects.MainPageWithAuth;
import com.yuo.otus_project.page_objects.TestingCourcesPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.safari.SafariOptions;

public class CheckCoursesCountTest {
    static final Logger LOGGER = LogManager.getLogger(CheckCoursesCountTest.class);

    WebDriver driver;

    AbstractDriverOptions options(WebDriverFactory.WebDriverName webDriverName) {
        switch (webDriverName) {
            case CHROME:
                return new ChromeOptions();
            case SAFARI:
                return new SafariOptions();
            case FIREFOX:
                return new FirefoxOptions();
        }
        return new ChromeOptions();
    }

    @AfterEach
    void after() {
        driver.quit();
    }


    @Test
    void withoutAuthTest() {
        WebDriverFactory.WebDriverName webDriverName = WebDriverFactory.WebDriverName.parseString(System.getProperty("browser"));
        driver = WebDriverFactory.create(webDriverName,options(webDriverName));
        driver.manage().window().maximize();
        driver.get("https://otus.ru");
        MainPageWithoutAuth mainPageWithoutAuth = new MainPageWithoutAuth(driver);
        TestingCourcesPage testingCourcesPage = mainPageWithoutAuth.openTestingCource();
        int cardCount = testingCourcesPage.getCourceCardsCount();
        LOGGER.info("Количество карточек по тестированию: "+cardCount);
        Assertions.assertEquals(11,cardCount );
    }

    @Test
    void withAuthTest() {
        WebDriverFactory.WebDriverName webDriverName = WebDriverFactory.WebDriverName.parseString(System.getProperty("browser"));
        driver = WebDriverFactory.create(webDriverName,options(webDriverName));
        driver.manage().window().maximize();
        driver.get("https://otus.ru");
        MainPageWithAuth mainPageWithAuth = new MainPageWithoutAuth(driver).auth(System.getProperty("login"), System.getProperty("password"));
        TestingCourcesPage testingCourcesPage = mainPageWithAuth.openTestingCource();
        int cardCount = testingCourcesPage.getCourceCardsCount();
        LOGGER.info("Количество карточек по тестированию: "+cardCount);
        Assertions.assertEquals(11,cardCount );
    }
}
