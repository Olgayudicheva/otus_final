package com.yuo.otus_project.page_objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Consumer;

public class MainPageWithoutAuth extends MainPage {
    final Logger LOGGER = LogManager.getLogger(MainPageWithoutAuth.class);
    final WebDriverWait wait;

    By bySignInButton = new By.ByXPath("//button[@data-modal-id=\"new-log-reg\"]");
    By byLoginInput = new By.ByXPath("//input[@name=\"email\" and not(contains(@class,\"hide\")) and @type=\"text\"]");
    By byPasswordInput = new By.ByXPath("//input[@name=\"password\" and not(contains(@class,\"hide\"))]");
    By byEnterButton = new By.ByXPath("//button[contains(text(), 'Войти') and not(contains(text(), 'аккаунт'))]");

    private final WebDriver driver;

    public MainPageWithoutAuth(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(bySignInButton));
        if (!"https://otus.ru/".equals(driver.getCurrentUrl())) {
            throw new IllegalStateException("This is not the login page");
        }
        if (driver.findElements(bySignInButton).size()==0) {
            throw new IllegalStateException("This is not the login page");
        }
    }

    public MainPageWithAuth auth (String email, String password){
        LOGGER.info("---Авторизация---\n"+"email: "+email+"\npassword: "+password);
        driver.findElement(bySignInButton).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(byLoginInput));

        WebElement passwordElement = driver.findElement(byPasswordInput);

        for (String s : password.split("")) {
            passwordElement.sendKeys(s);
        }
        wait.until(ExpectedConditions.attributeToBe(byPasswordInput, "value", password));

        WebElement loginElement = driver.findElement(byLoginInput);
        loginElement.sendKeys(email);
        wait.until(ExpectedConditions.attributeToBe(byLoginInput, "value", email));


        WebElement loginButtonElement = driver.findElement(byEnterButton);
        loginButtonElement.click();
        System.out.println("COOKIE:");
        driver.manage().getCookies().forEach(new Consumer<Cookie>() {
            @Override
            public void accept(Cookie cookie) {
                System.out.println(cookie);
            }
        });
        return new MainPageWithAuth(driver);
    }
}
