package com.yuo.otus_project.tests;

import com.yuo.otus_project.WebDriverFactory;
import com.yuo.otus_project.page_objects.CourseCardPage;
import com.yuo.otus_project.page_objects.MainPageWithAuth;
import com.yuo.otus_project.page_objects.MainPageWithoutAuth;
import com.yuo.otus_project.page_objects.TestingCourcesPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.safari.SafariOptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CheckCourseCardTest {
    static final Logger LOGGER = LogManager.getLogger(CheckCourseCardTest.class);

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
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }


    @Test
    void withoutAuthTest() {
        WebDriverFactory.WebDriverName webDriverName = WebDriverFactory.WebDriverName.parseString(System.getProperty("browser"));
        driver = WebDriverFactory.create(webDriverName, options(webDriverName));
        driver.manage().window().maximize();
        driver.get("https://otus.ru");
        MainPageWithoutAuth mainPageWithoutAuth = new MainPageWithoutAuth(driver);
        TestingCourcesPage testingCourcesPage = mainPageWithoutAuth.openTestingCource();
        checkCards(testingCourcesPage);
    }

    private void checkCards(TestingCourcesPage testingCourcesPage) {
        int cardCount = testingCourcesPage.getCourceCardsCount();
        LOGGER.info("Количество карточек по тестированию: " + cardCount);
        List<Integer> failedCards = new ArrayList<>();
        for (int i = 0; i < cardCount; i++) {
            try {
                CourseCardPage courseCardPage = testingCourcesPage.openCard(i);
                if (courseCardPage.type != CourseCardPage.Type.PROMO) {
                    courseCardPage.checkMainField();
                }
            } catch (Throwable t) {
                t.printStackTrace();
                failedCards.add(i);
            }
            driver.navigate().back();
        }
        Assertions.assertTrue(failedCards.isEmpty(), "В карточках " + failedCards + " есть ошибки");
    }

    @Test
    void withAuthTest() {
        WebDriverFactory.WebDriverName webDriverName = WebDriverFactory.WebDriverName.parseString(System.getProperty("browser"));
        driver = WebDriverFactory.create(webDriverName, options(webDriverName));
        driver.manage().window().maximize();
        driver.get("https://otus.ru");
        MainPageWithAuth mainPageWithAuth = new MainPageWithoutAuth(driver).auth(System.getProperty("login"), System.getProperty("password"));
        TestingCourcesPage testingCourcesPage = mainPageWithAuth.openTestingCource();
        checkCards(testingCourcesPage);
    }
}
