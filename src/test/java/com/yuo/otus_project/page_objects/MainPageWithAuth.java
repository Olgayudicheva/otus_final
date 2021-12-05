package com.yuo.otus_project.page_objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPageWithAuth extends MainPage {
    final Logger LOGGER = LogManager.getLogger(MainPageWithAuth.class);

    By myMenu = new By.ByXPath("//div[@class=\"header2-menu\"]");
    By privateCabinet = new By.ByXPath("//a[@title=\"Личный кабинет\"]");

    private final WebDriver driver;
    public MainPageWithAuth(WebDriver driver) {
        super(driver);
        this.driver = driver;
        wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(myMenu));
    }
    public PrivateCabinetPage goToPrivateCabinet (){
        LOGGER.info("---Переход в личный кабинет---");
        driver.findElement(myMenu).click();
        driver.findElement(privateCabinet).click();
        return new PrivateCabinetPage(driver);
    }
}
