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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CheckCourseCardTest {
    static final Logger LOGGER = LogManager.getLogger(CheckCourseCardTest.class);

    WebDriver driver;
    WebDriverWait wait;

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

    private void checkCards(TestingCourcesPage testingCourcesPage) {
        int cardCount = testingCourcesPage.getCourceCardsCount();
        LOGGER.info("Количество карточек по тестированию: " + cardCount);
        List<Integer> failedCards = new ArrayList<>();
        for (int i = 0; i < cardCount; i++) {
            try {
                CourseCardPage courseCardPage = testingCourcesPage.openCard(i);
                if (courseCardPage.type != CourseCardPage.Type.PROMO) {
                    checkMainField(courseCardPage);
                }
                driver.navigate().back();
            } catch (Throwable t) {
                t.printStackTrace();
                failedCards.add(i);
            }
            wait.until(ExpectedConditions.urlContains("testing"));
            testingCourcesPage.waitLoad();
        }
        Assertions.assertTrue(failedCards.isEmpty(), "В карточках " + failedCards + " есть ошибки");
    }

    public void checkMainField(CourseCardPage courseCardPage) {
        Assertions.assertFalse(courseCardPage.getTitle().isEmpty(), "Title is empty");
        Assertions.assertFalse(courseCardPage.getDescription().isEmpty(), "Description is empty");
        Assertions.assertFalse(courseCardPage.getLearnTime().isEmpty(), "Learn time is empty");
        Assertions.assertFalse(courseCardPage.getLearnFormat().isEmpty(), "Learn format is empty");
    }

    @Test
    void withoutAuthTest() {
        WebDriverFactory.WebDriverName webDriverName = WebDriverFactory.WebDriverName.parseString(System.getProperty("browser"));
        driver = WebDriverFactory.create(webDriverName, options(webDriverName));
        wait = new WebDriverWait(driver,10);
        driver.manage().window().maximize();
        driver.get("https://otus.ru");
        MainPageWithoutAuth mainPageWithoutAuth = new MainPageWithoutAuth(driver);
        TestingCourcesPage testingCourcesPage = mainPageWithoutAuth.openTestingCource();
        checkCards(testingCourcesPage);
    }



    @Test
    void withAuthTest() {
        WebDriverFactory.WebDriverName webDriverName = WebDriverFactory.WebDriverName.parseString(System.getProperty("browser"));
        driver = WebDriverFactory.create(webDriverName, options(webDriverName));
        wait = new WebDriverWait(driver,10);
        driver.manage().window().maximize();
        driver.get("https://otus.ru");
        MainPageWithAuth mainPageWithAuth = new MainPageWithoutAuth(driver).auth(System.getProperty("login"), System.getProperty("password"));
        TestingCourcesPage testingCourcesPage = mainPageWithAuth.openTestingCource();
        checkCards(testingCourcesPage);
    }
}
