package com.yuo.otus_project.page_objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    final Logger LOGGER = LogManager.getLogger(MainPageWithAuth.class);
    WebDriverWait wait;

    public By byCourceButton = new By.ByXPath("//div[contains(@class, 'header2-menu_main')]//p[text()=\"Курсы\"]");
    public By byTestingCourseButton = new By.ByXPath("//div[contains(@class, 'header2-menu_main')]//a[contains(@class, 'header2-menu__dropdown-link') and @title=\"Тестирование\"]");
    public By byEventButton = new By.ByXPath("//div[contains(@class, 'header2-menu_main')]//p[text()=\"События\"]");
    public By byCalendarEventButton = new By.ByXPath("//div[contains(@class, 'header2-menu_main')]//a[contains(@class, 'header2-menu__dropdown-link') and @title=\"Календарь мероприятий\"]");

    private final WebDriver driver;
    public MainPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver,10);
    }

    public void openCourcePopup() {
        driver.findElement(byCourceButton).click();
    }

    public void openEventPopup() {
        driver.findElement(byEventButton).click();
    }

    public TestingCourcesPage openTestingCource() {
        openCourcePopup();
        driver.findElement(byTestingCourseButton).click();
        return new TestingCourcesPage(driver);
    }

    public EventCalendarPage openEventCalendar() {
        openEventPopup();
        driver.findElement(byCalendarEventButton).click();
        return new EventCalendarPage(driver);
    }
}
