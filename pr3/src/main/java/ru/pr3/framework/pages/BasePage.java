package ru.pr3.framework.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ru.pr3.framework.managers.WebDriverManager;
import ru.pr3.framework.managers.PageManager;
import ru.pr3.framework.managers.PropManager;
import ru.pr3.framework.managers.DBManager;
import ru.pr3.framework.util.foodType;

import java.sql.ResultSet;
import java.time.Duration;

public class BasePage {

    private final PropManager propManager = PropManager.getPropInstance();
    protected final WebDriverManager webDriverManager = WebDriverManager.getDriverInstance();

    protected final DBManager dbManager = DBManager.getDBInstance();

    protected PageManager pageManager = PageManager.getPageManagerInstance();

    protected WebDriverWait wait = new WebDriverWait(webDriverManager.getDriver(), Duration.ofSeconds(5));

    BasePage() {
        PageFactory.initElements(webDriverManager.getDriver(), this);
    }

    protected WebElement waitForClickable(WebElement e) {
        return wait.until(ExpectedConditions.elementToBeClickable(e));
    }

    protected WebElement waitForVisible(WebElement e) {
        return wait.until(ExpectedConditions.visibilityOf(e));
    }

    protected void fillInput(String text, WebElement e) {
        waitForClickable(e).click();
        e.sendKeys(text);
    }

    protected void assertPageTitle(WebElement e, String expected) {
        Assert.assertEquals(e.getText(), expected,
                "Invalid page title / Incorrect page opened");
    }

    protected void insertToDB(String name, foodType type, boolean exotic) {
        Assert.assertTrue(dbManager.insertRow(name, type, exotic), "Row insertion fail!");
    }

    protected ResultSet getAllFromDB() {
        ResultSet rs = dbManager.getContents();
        Assert.assertNotNull(rs, "Empty table returned!");
        return rs;
    }

}
