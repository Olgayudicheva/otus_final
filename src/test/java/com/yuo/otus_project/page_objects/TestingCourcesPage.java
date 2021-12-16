package com.yuo.otus_project.page_objects;

import com.yuo.otus_project.tools.MoveToElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class TestingCourcesPage {
    final Logger LOGGER = LogManager.getLogger(TestingCourcesPage.class);
    final WebDriverWait wait;

    By byTitle = new By.ByXPath("//div[@class=\"title-new__text\"]/h1[contains(text(), \"Тестирование\")]");
    By byCourceCards = new By.ByXPath("//a[contains(@class,\"lessons__new-item\")]");

    private final WebDriver driver;
    public TestingCourcesPage(WebDriver driver) {
        this.driver = driver;
        LOGGER.info(driver.getCurrentUrl());
        wait = new WebDriverWait(driver,30);
        waitLoad();
        LOGGER.info(driver.findElement(byTitle).getText());
        Assertions.assertTrue(driver.findElement(byTitle).getText().contains("Тестирование"));
    }

    public void waitLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(byTitle));
        wait.until(ExpectedConditions.visibilityOfElementLocated(byCourceCards));
        LOGGER.info(driver.getCurrentUrl());
    }

    public int getCourceCardsCount() {
        return driver.findElements(byCourceCards).size();
    }

    public List<WebElement> getCourceCards() {
        return driver.findElements(byCourceCards);
    }

    public CourseCardPage openCard(int position) {
        WebElement card = getCourceCards().get(position);
        Actions actions = new Actions(driver);
        MoveToElement.scrollElementIntoMiddle(driver, card);
        card.click();
        //wait.until(ExpectedConditions.visibilityOf(card)).click();
        LOGGER.info("Открыта карточка №"+position);
        return new CourseCardPage(driver);
    }
}
