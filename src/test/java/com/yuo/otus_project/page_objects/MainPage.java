package com.yuo.otus_project.page_objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    final Logger LOGGER = LogManager.getLogger(MainPageWithAuth.class);
    WebDriverWait wait;

    public By byCourceButton = new By.ByXPath("//div[contains(@class, 'header2-menu_main')]//p[text()=\"Курсы\"]");
    public By byTestingCourseButton = new By.ByXPath("//div[contains(@class, 'header2-menu_main')]//a[contains(@class, 'header2-menu__dropdown-link') and contains(@title,\"Тестирование\")]");
    public By byTestingInUnActive = new By.ByXPath("//div[@class=\"nav__items course-categories__nav\"]//a[contains(@title,\"Тестирование\")]");
    public By byTestingInActive = new By.ByXPath("//div[@class=\"nav__items course-categories__nav\"]//a[contains(@title,\"Тестирование\") and contains(@class, \"item_active\")]");
    public By byEventButton = new By.ByXPath("//div[contains(@class, 'header2-menu_main')]//p[text()=\"События\"]");
    public By byCalendarEventButton = new By.ByXPath("//div[contains(@class, 'header2-menu_main')]//a[contains(@class, 'header2-menu__dropdown-link') and @title=\"Календарь мероприятий\"]");

    private final WebDriver driver;
    public MainPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver,30);
    }

    public void openCourcePopup() {
        driver.findElement(byCourceButton).click();
    }

    public void openEventPopup() {
        driver.findElement(byEventButton).click();
    }

    public TestingCourcesPage openTestingCource() {
        openCourcePopup();
        wait.until(ExpectedConditions.visibilityOfElementLocated(byTestingCourseButton)).click();
        return new TestingCourcesPage(driver);
    }

    public EventCalendarPage openEventCalendar() {
        openEventPopup();
        wait.until(ExpectedConditions.visibilityOfElementLocated(byCalendarEventButton)).click();
        return new EventCalendarPage(driver);
    }
}
