package com.yuo.otus_project.tests;

import com.yuo.otus_project.WebDriverFactory;
import com.yuo.otus_project.page_objects.EventCalendarPage;
import com.yuo.otus_project.page_objects.MainPageWithAuth;
import com.yuo.otus_project.page_objects.MainPageWithoutAuth;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ValidatingDatesUpcomingEventsTest {
    static final Logger LOGGER = LogManager.getLogger(ValidatingDatesUpcomingEventsTest.class);

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
    void withoutAuthTest() throws ParseException {
        WebDriverFactory.WebDriverName webDriverName = WebDriverFactory.WebDriverName.parseString(System.getProperty("browser"));
        driver = WebDriverFactory.create(webDriverName, options(webDriverName));
        driver.manage().window().maximize();
        driver.get("https://otus.ru");
        MainPageWithoutAuth mainPageWithoutAuth = new MainPageWithoutAuth(driver);
        EventCalendarPage eventCalendarPage = mainPageWithoutAuth.openEventCalendar();
        List<Date> eventsDate = eventCalendarPage.getDatesUpcomingEvents();
        Date currentDate = new Date();
        List<Integer> failedDate = new ArrayList<>();
        for (int i=0;i<eventsDate.size();i++) {
            Date eventDate = eventsDate.get(i);
            if (!eventDate.equals(currentDate) && eventDate.before(currentDate)) {
                failedDate.add(i);
            }
        }
        Assertions.assertTrue(failedDate.isEmpty(), "В событиях "+failedDate+" дата уже пройдена, либо будет в след году");
    }



    @Test
    void withAuthTest() throws ParseException {
        WebDriverFactory.WebDriverName webDriverName = WebDriverFactory.WebDriverName.parseString(System.getProperty("browser"));
        driver = WebDriverFactory.create(webDriverName, options(webDriverName));
        driver.manage().window().maximize();
        driver.get("https://otus.ru");
        MainPageWithAuth mainPageWithAuth = new MainPageWithoutAuth(driver).auth(System.getProperty("login"), System.getProperty("password"));
        EventCalendarPage eventCalendarPage = mainPageWithAuth.openEventCalendar();
        List<Date> eventsDate = eventCalendarPage.getDatesUpcomingEvents();
        Date currentDate = new Date();
        List<Integer> failedDate = new ArrayList<>();
        for (int i=0;i<eventsDate.size();i++) {
            Date eventDate = eventsDate.get(i);
            if (!eventDate.equals(currentDate) && eventDate.before(currentDate)) {
                failedDate.add(i);
            }
        }
        Assertions.assertTrue(failedDate.isEmpty(), "В событиях "+failedDate+" дата уже пройдена, либо будет в след году");
    }
}
