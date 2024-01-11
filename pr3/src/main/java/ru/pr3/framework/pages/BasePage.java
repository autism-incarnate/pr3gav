package ru.pr3.framework.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.pr3.framework.managers.DriverManager;
import ru.pr3.framework.managers.PageManager;
import ru.pr3.framework.managers.PropManager;

import java.time.Duration;

public class BasePage {

    @FindBy(xpath = "//a[@class='navbar-brand']")
    protected WebElement homeButton;

    @FindBy(xpath = "//a[@id='navbarDropdown']")
    protected WebElement sandBoxButton;

    @FindBy(xpath = "//a[@class='dropdown-item'][@href='/food']['Товары']")
    protected WebElement goodsButton;

    private final PropManager propManager = PropManager.getPropInstance();
    protected final DriverManager driverManager = DriverManager.getDriverInstance();

    protected PageManager pageManager = PageManager.getPageManagerInstance();

    protected WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), Duration.ofSeconds(5));

    BasePage() {
        PageFactory.initElements(driverManager.getDriver(), this);
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



}
