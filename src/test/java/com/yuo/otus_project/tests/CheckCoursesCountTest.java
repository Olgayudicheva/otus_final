package com.yuo.otus_project.tests;

import com.yuo.otus_project.WebDriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.safari.SafariOptions;

import java.util.ArrayList;

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
    }

    @Test
    void withAuthTest() {
        WebDriverFactory.WebDriverName webDriverName = WebDriverFactory.WebDriverName.parseString(System.getProperty("browser"));
        driver = WebDriverFactory.create(webDriverName,options(webDriverName));
        driver.manage().window().maximize();
        driver.get("https://otus.ru");
    }
}
