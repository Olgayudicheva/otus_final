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
    public By byTestingCourseButton = new By.ByXPath("//div[contains(@class, 'header2-menu_main')]//a[contains(@class, 'header2-menu__dropdown-link') and @title=\"Тестирование\"]");

    private final WebDriver driver;
    public MainPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver,10);
    }

    public void openCourceList() {
        driver.findElement(byCourceButton).click();
    }

    public TestingCourcesPage openTestingCource() {
        openCourceList();
        driver.findElement(byTestingCourseButton).click();
        return new TestingCourcesPage(driver);
    }
}
