package com.yuo.otus_project.page_objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class TestingCourcesPage {
    final Logger LOGGER = LogManager.getLogger(MainPageWithoutAuth.class);
    final WebDriverWait wait;

    By byCourceCards = new By.ByXPath("//div[@class=\"lessons__new-item-container\"]");

    private final WebDriver driver;
    public TestingCourcesPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver,5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(byCourceCards));
    }

    public int getCourceCardsCount() {
        return driver.findElements(byCourceCards).size();
    }

    public List<WebElement> getCourceCards() {
        return driver.findElements(byCourceCards);
    }

    public CourseCardPage openCard(int position) {
        getCourceCards().get(position).click();
        LOGGER.info("Открыта карточка №"+position);
        return new CourseCardPage(driver);
    }
}
