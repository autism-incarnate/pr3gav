package ru.pr3.framework.managers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverManager {
    private static WebDriverManager driver_ref = null;

    public static WebDriver driver;
    private WebDriverManager() {
    }

    public static WebDriverManager getDriverInstance() {
        if(driver_ref == null)
            driver_ref = new WebDriverManager();
        return driver_ref;
    }

    public WebDriver getDriver() {
        if(driver==null){
            initDriver();
        }
        return driver;
    }

    private void initDriver() {
        driver = new ChromeDriver();
    }

    public void quitDriver() {
        if(driver!=null) {
            driver.quit();
            driver = null;
        }
    }

}
