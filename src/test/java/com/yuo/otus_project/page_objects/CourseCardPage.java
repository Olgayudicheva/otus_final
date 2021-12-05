package com.yuo.otus_project.page_objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.function.Consumer;

public class CourseCardPage {
    final Logger LOGGER = LogManager.getLogger(MainPageWithoutAuth.class);
    final WebDriverWait wait;

    enum Type {
        BASIC,
        PREPARATORY
    }

    By byTitle = new By.ByXPath("//*[@class=\"course-header2__title\"]");
    By byShortDescription = new By.ByXPath("//*[@class=\"course-header2__admin-text\"]");

    By byPreparatoryTitle = new By.ByXPath("//*[@class=\"preparatory-intro__title\"]");
    By byPreparatoryIntoList = new By.ByXPath("//div[@class=\"preparatory-intro__list\"]//li");

    Type type;


    private final WebDriver driver;
    public CourseCardPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver,5);
        if (!driver.findElements(byPreparatoryTitle).isEmpty()) {
            type = Type.PREPARATORY;
        } else {
            type = Type.BASIC;
        }
        LOGGER.info("Содержимое карточки\nЗаголовок:"+getTitle()+"\nОписание:"+getDescription());
    }

    public String getTitle() {
        switch (type) {
            case PREPARATORY:
                wait.until(ExpectedConditions.visibilityOfElementLocated(byPreparatoryTitle));
                return driver.findElement(byPreparatoryTitle).getText();
            default:
                wait.until(ExpectedConditions.visibilityOfElementLocated(byTitle));
                return driver.findElement(byTitle).getText();
        }
    }
    public String getDescription() {
        switch (type) {
            case PREPARATORY:
                wait.until(ExpectedConditions.visibilityOfElementLocated(byPreparatoryIntoList));
                String out = "";
                List<WebElement> list = driver.findElements(byPreparatoryIntoList);
                for (int i=0;i<list.size();i++) {
                    out += list.get(i).getText();
                }
                return out;
            default:
                wait.until(ExpectedConditions.visibilityOfElementLocated(byShortDescription));
                return driver.findElement(byShortDescription).getText();
        }
    }

}
