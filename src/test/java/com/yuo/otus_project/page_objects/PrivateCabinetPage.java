package com.yuo.otus_project.page_objects;

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
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class PrivateCabinetPage {
    final Logger LOGGER = LogManager.getLogger(PrivateCabinetPage.class);

    By byAboutMe = new By.ByXPath("//div[contains(@class,'nav ') and not(contains(@style,'display: none'))]//a[@title=\"О себе\"]");
    By byFirstName = new By.ByXPath("//input[@name=\"fname\"]");
    By byFirstNameLat = new By.ByXPath("//input[@name=\"fname_latin\"]");
    By bySecondName = new By.ByXPath("//input[@name=\"lname\"]");
    By bySecondNameLat = new By.ByXPath("//input[@name=\"lname_latin\"]");
    By byNameBlog = new By.ByXPath("//input[@name=\"blog_name\"]");
    By byDOB = new By.ByXPath("//input[@name=\"date_of_birth\"]");

    By byContactsListElement = new By.ByXPath("//div[@data-num and not(contains(@class,'hide'))]");

    By bySaveButton = new By.ByXPath("//button[@title=\"Сохранить и заполнить позже\"]");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public PrivateCabinetPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    public void clickToAboutMe() {
        LOGGER.info("---Переход на экран \"О себе\"---");
        wait.until(ExpectedConditions.visibilityOfElementLocated(byAboutMe));
        driver.findElement(byAboutMe).click();
        LOGGER.info("---Завершено---\n");
    }

    public void setFirstName(String firstName) {
        LOGGER.info("---Имя---\n"+firstName);
        driver.findElement(byFirstName).clear();
        driver.findElement(byFirstName).sendKeys(firstName);
        LOGGER.info("---Завершено---\n");
    }
    public void checkFirstName(String firstName){
        LOGGER.info("---проверка имени---");
        Assertions.assertEquals(driver.findElement(byFirstName).getAttribute("value"), firstName);
    }

    public void setFirstNameLat(String firstNameLat) {
        LOGGER.info("---Имя лат---\n"+firstNameLat);
        driver.findElement(byFirstNameLat).clear();
        driver.findElement(byFirstNameLat).sendKeys(firstNameLat);
        LOGGER.info("---Завершено---\n");
    }
    public void checkFirstNameLat(String firstNameLat){
        LOGGER.info("---проверка имени на латинице---");
        Assertions.assertEquals(driver.findElement(byFirstNameLat).getAttribute("value"), firstNameLat);
    }

    public void setSecondName(String secondName) {
        LOGGER.info("---Фамилия---\n"+secondName);
        driver.findElement(bySecondName).clear();
        driver.findElement(bySecondName).sendKeys(secondName);
        LOGGER.info("---Завершено---\n");
    }
    public void checkSecondName(String secondName){
        LOGGER.info("---проверка фамилии---");
        Assertions.assertEquals(driver.findElement(bySecondName).getAttribute("value"), secondName);
    }

    public void setSecondNameLat(String secondNameLat) {
        LOGGER.info("---Фамилия лат---\n"+secondNameLat);
        driver.findElement(bySecondNameLat).clear();
        driver.findElement(bySecondNameLat).sendKeys(secondNameLat);
        LOGGER.info("---Завершено---\n");
    }
    public void checkSecondNameLat(String secondNameLat){
        LOGGER.info("---проверка фамилии на латинице---");
        Assertions.assertEquals(driver.findElement(bySecondNameLat).getAttribute("value"), secondNameLat);
    }
    public void setNameBlog(String nameBlog) {
        LOGGER.info("---Имя блога---\n"+nameBlog);
        driver.findElement(byNameBlog).clear();
        driver.findElement(byNameBlog).sendKeys(nameBlog);
        LOGGER.info("---Завершено---\n");
    }
    public void checkNameBlog(String nameBlog){
        LOGGER.info("---проверка имени в блоге---");
        Assertions.assertEquals(driver.findElement(byNameBlog).getAttribute("value"), nameBlog);
    }
    public void setDOB(String dob) {
        LOGGER.info("---Дата рождения---\n"+dob);
        driver.findElement(byDOB).clear();
        driver.findElement(byDOB).sendKeys(dob);
        LOGGER.info("---Завершено---\n");
    }
    public void checkDOB(String dob){
        LOGGER.info("---проверка даты рождения---");
        Assertions.assertEquals(driver.findElement(byDOB).getAttribute("value"), dob);
    }
    public void save() {
        LOGGER.info("---Сохранение данных---");
        driver.findElement(bySaveButton).click();
        LOGGER.info("---Завершено---\n");
    }

    private List<WebElement> getContactsListElements() {
        return driver.findElements(byContactsListElement);
    }

    public void deleteAllContact() {
        LOGGER.info("---Удаление контактов---");
        List<WebElement> contacts = getContactsListElements();
        while (!contacts.isEmpty()) {
            WebElement last = contacts.get(contacts.size() - 1);
            WebElement deleteButton = last.findElement(new By.ByXPath("./div[3]/div[2]/button"));
            deleteButton.click();
            contacts = getContactsListElements();
        }
        LOGGER.info("---Завершено---\n");
    }

    public Contact addRandomContact() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(new By.ByXPath("//button[text()=\"Добавить\"]")));
        WebElement addContact = driver.findElement(new By.ByXPath("//button[text()=\"Добавить\"]"));

        addContact.click();
        List<WebElement> contacts = getContactsListElements();
        WebElement lastElement = contacts.get(contacts.size() - 1);
        WebElement lastContactSelected = lastElement.findElement(new By.ByClassName("js-custom-select-presentation"));
        WebElement lastContactInput = lastElement.findElement(new By.ByXPath(".//input[starts-with(@id,'id_contact') and contains(@type,'text')]"));


        new Actions(driver).moveToElement(lastContactInput).perform();

        String unText = UUID.randomUUID().toString();
        lastContactInput.sendKeys(unText);
        lastContactSelected.click();
        WebElement contatsTypeMenu = lastElement.findElement(new By.ByXPath(".//div[@class=\"lk-cv-block__select-scroll lk-cv-block__select-scroll_service js-custom-select-options\"]"));
        List<WebElement> contactTypes = contatsTypeMenu.findElements(new By.ByXPath(".//*"));
        int randomIndex = ThreadLocalRandom.current().nextInt(1, contactTypes.size() - 1);
        contactTypes.get(randomIndex).click();

        String selectedType = lastElement.findElement(new By.ByXPath("./div[1]/div[1]/div[1]/div[1]/label/div")).getText();
        Contact contact = new Contact(selectedType.trim(), unText.trim());
        LOGGER.info("---Добавление контакта---\n"+contact.toString());
        LOGGER.info("---Завершено---\n");
        return contact;
    }

    public void checkContactList(List<Contact> contacts) {
        LOGGER.info("---Проверка списка контактов---");
        List<WebElement> contactsList = getContactsListElements();
        Assertions.assertEquals(contactsList.size(), contacts.size());
        for(int i=0;i<contacts.size();i++) {
            LOGGER.info("---Проверка контакта №"+(i+1)+"---");
            WebElement contactElement = contactsList.get(i);
            WebElement lastContactInput = contactElement.findElement(new By.ByXPath(".//input[starts-with(@id,'id_contact') and contains(@type,'text')]"));
            String selectedType = contactElement.findElement(new By.ByXPath("./div[1]/div[1]/div[1]/div[1]/label/div")).getText().trim();
            String value = lastContactInput.getAttribute("value").trim();
            Contact contact = null;
            for (Contact c : contacts) {
                if (c.text.equals(value)) {
                    contact = c;
                }
            }
            LOGGER.info("-Проверка на null-");
            Assertions.assertNotNull(contact);
            LOGGER.info("-Проверка содаржимого-");
            Assertions.assertEquals(contact.text, value);
            LOGGER.info("-Проверка типа-\n"+contact.type+"=="+selectedType);
            Assertions.assertEquals(contact.type, selectedType);
        }
    }

    static public class Contact {
        String type;
        String text;

        public Contact(String type, String text) {
            this.type = type;
            this.text = text;
        }

        @Override
        public String toString() {
            return "Contact{" +
                    "type='" + type + '\'' +
                    ", text='" + text + '\'' +
                    '}';
        }
    }
}
