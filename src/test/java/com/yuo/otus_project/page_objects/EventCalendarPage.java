package com.yuo.otus_project.page_objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.platform.commons.util.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EventCalendarPage {
    final Logger LOGGER = LogManager.getLogger(EventCalendarPage.class);
    final WebDriverWait wait;

    By byUpcomingEventCards = new By.ByXPath("//div[@class=\"dod_new-event-content\"]");
    By byUpcomingEventCardDate = new By.ByXPath(".//span[@class=\"dod_new-event__date-text\"]");

    private final WebDriver driver;

    public EventCalendarPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(byUpcomingEventCards));
    }

    public List<WebElement> getUpcomingEvents() {
        List<WebElement> upcomingEvents = driver.findElements(byUpcomingEventCards);

        boolean loadListStop = false;

        while (!loadListStop) {
            //Actions actions = new Actions(driver);
            //actions.moveToElement(upcomingEvents.get(upcomingEvents.size() - 1)).perform();
            wait.until(ExpectedConditions.javaScriptThrowsNoExceptions("window.scrollTo(0, document.body.scrollHeight)"));
            try {
                wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(byUpcomingEventCards, upcomingEvents.size()));
            } catch (Throwable t) {} finally {
                List<WebElement> newUpcomingEvents = driver.findElements(byUpcomingEventCards);
                if (newUpcomingEvents.size() == upcomingEvents.size()) {
                    loadListStop = true;
                }
                upcomingEvents = newUpcomingEvents;
            }
        }
        return upcomingEvents;
    }

    public List<Date> getDatesUpcomingEvents() throws ParseException {
        ArrayList<Date> outArray = new ArrayList<>();

        List<WebElement> upcomingEvents = getUpcomingEvents();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy dd MMMM HH:mm");
        String currentYear = new SimpleDateFormat("yyyy").format(new Date());

        for (int cardIndex = 0; cardIndex < upcomingEvents.size(); cardIndex++) {
            WebElement card = upcomingEvents.get(cardIndex);
            List<WebElement> dateAndTime = card.findElements(byUpcomingEventCardDate);
            String dateAndTimeString = currentYear + " " + dateAndTime.get(0).getText() + " " + dateAndTime.get(1).getText();
            LOGGER.info(dateAndTimeString);
            Date date = simpleDateFormat.parse(dateAndTimeString);
            LOGGER.info(date);
            outArray.add(date);
        }
        return outArray;
    }
}
