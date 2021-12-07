package com.yuo.otus_project.tools;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MoveToElement {
    static public String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
            + "var elementTop = arguments[0].getBoundingClientRect().top;"
            + "window.scrollBy(0, elementTop-(viewPortHeight/2));";

    static public void scrollElementIntoMiddle(WebDriver driver, WebElement webElement) {
        ((JavascriptExecutor) driver).executeScript(scrollElementIntoMiddle, webElement);
    }
}
