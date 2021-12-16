package com.yuo.otus_project.page_objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CourseCardPage {
    final Logger LOGGER = LogManager.getLogger(MainPageWithoutAuth.class);
    final WebDriverWait wait;

    public enum Type {
        BASIC,
        PREPARATORY,
        PROMO
    }

    By byTitle = new By.ByXPath("//*[@class=\"course-header2__title\"]");
    By bySubTitle = new By.ByXPath("//*[@class=\"course-header2__subtitle\"]");
    By byShortDescription = new By.ByXPath("//*[@class=\"course-header2__admin-text\"]");
    By byLearnTime = new By.ByXPath("//p[contains(text(),\"Длительность обучения:\")]/../../../div[contains(@class, \"text\")]//p[contains(@class, \"course-header2-bottom__item-text\")]");
    By byLearnFormat = new By.ByXPath("//p[contains(text(),\"Длительность обучения:\")]/../../../div[contains(@class, \"text\")]//div[contains(@class, \"course-header2-bottom__item-text-small\")]");


    By byPreparatoryTitle = new By.ByXPath("//*[@class=\"preparatory-intro__title\"]");
    By byPreparatoryIntoList = new By.ByXPath("//div[@class=\"preparatory-intro__list\"]//li");
    By byPreparatoryLearnTime = new By.ByXPath("//div[contains(text(),\"Длительность:\")]/../div[2]");
    By byPreparatoryLearnFormat = new By.ByXPath("//div[contains(text(),\"Формат:\")]/../div[2]");

    By byBasicHeader = new By.ByXPath("//*[contains(@class, \"container-header2\")]");

    public final Type type;


    private final WebDriver driver;

    public CourseCardPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
        if (driver.findElements(byBasicHeader).isEmpty()) {
            type = Type.PROMO;
        } else if (!driver.findElements(byPreparatoryTitle).isEmpty()) {
            type = Type.PREPARATORY;
        } else {
            type = Type.BASIC;
        }
        LOGGER.info("---");
        LOGGER.info("Содержимое карточки");
        LOGGER.info("Тип: " + type.toString());
        LOGGER.info("Заголовок: " + getTitle());
        LOGGER.info("Описание: " + getDescription());
        LOGGER.info("Время: " + getLearnTime());
        LOGGER.info("Формат: " + getLearnFormat());
        LOGGER.info("---");
    }

    public String getTitle() {
        switch (type) {
            case PREPARATORY:
                wait.until(ExpectedConditions.visibilityOfElementLocated(byPreparatoryTitle));
                return driver.findElement(byPreparatoryTitle).getText();
            case PROMO:
                return "";
            default:
                wait.until(ExpectedConditions.visibilityOfElementLocated(byTitle));
                return driver.findElement(byTitle).getText();
        }
    }

    public String getDescription() {
        String out = "";
        switch (type) {
            case PREPARATORY:
                wait.until(ExpectedConditions.visibilityOfElementLocated(byPreparatoryIntoList));
                List<WebElement> list = driver.findElements(byPreparatoryIntoList);
                for (int i = 0; i < list.size(); i++) {
                    out += list.get(i).getText();
                }
                return out;
            case PROMO:
                return "";
            default:

                List<WebElement> shortDescription = driver.findElements(byShortDescription);
                if (!shortDescription.isEmpty())
                    out += shortDescription.get(0).getText();
                List<WebElement> subTitles = driver.findElements(bySubTitle);
                if (!subTitles.isEmpty())
                    out += subTitles.get(0).getText();

                return out;
        }
    }

    public String getLearnTime() {
        switch (type) {
            case PREPARATORY:
                wait.until(ExpectedConditions.visibilityOfElementLocated(byPreparatoryLearnTime));
                return driver.findElement(byPreparatoryLearnTime).getText();
            case PROMO:
                return "";
            default:
                String out = "";
                wait.until(ExpectedConditions.visibilityOfElementLocated(byLearnTime));
                out += driver.findElement(byLearnTime).getText();
                return out;
        }
    }

    public String getLearnFormat() {
        switch (type) {
            case PREPARATORY:
                wait.until(ExpectedConditions.visibilityOfElementLocated(byPreparatoryLearnFormat));
                return driver.findElement(byPreparatoryLearnFormat).getText();
            case PROMO:
                return "";
            default:
                String out = "";
                wait.until(ExpectedConditions.visibilityOfElementLocated(byLearnFormat));
                List<WebElement> formats = driver.findElements(byLearnFormat);
                for (int i = 0; i < formats.size(); i++) {
                    out += formats.get(i).getText();
                }
                return out;
        }
    }
}
