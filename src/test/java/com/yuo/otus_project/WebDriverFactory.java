package com.yuo.otus_project;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.util.Collections;
import java.util.List;

public class WebDriverFactory {
     public enum WebDriverName {
        CHROME, FIREFOX, SAFARI;

        public static WebDriverName parseString(String name) {
            try {
                return valueOf(name.toUpperCase());
            } catch (Throwable t) {
                t.printStackTrace();
                return CHROME;
            }
        }
    }

    public static WebDriver create(WebDriverName webDriverName) {
        return create(webDriverName, Collections.emptyList());
    }

    public static WebDriver create(WebDriverName webDriverName, List<String> args) {
        switch (webDriverName) {
            case CHROME:
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments(args);
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver(chromeOptions);
            case SAFARI:
                SafariOptions safariOptions = new SafariOptions();
                WebDriverManager.safaridriver().setup();
                return new SafariDriver(safariOptions);
            case FIREFOX:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments(args);
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver(firefoxOptions);
        }
        return new ChromeDriver();
    }

    public static WebDriver create(WebDriverName webDriverName, AbstractDriverOptions options) {
        switch (webDriverName) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver((ChromeOptions) options);
            case SAFARI:
                WebDriverManager.safaridriver().setup();
                return new SafariDriver((SafariOptions) options);
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver((FirefoxOptions) options);
        }
        return new ChromeDriver();
    }
}
