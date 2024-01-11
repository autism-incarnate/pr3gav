package ru.pr3.framework.managers;

import ru.pr3.framework.util.Props;
import java.util.concurrent.TimeUnit;

public class InitManager {

    private static final DriverManager driverManager = DriverManager.getDriverInstance();


    private static final PropManager propManager = PropManager.getPropInstance();

    public static void init() {
        driverManager.getDriver().manage().window().maximize();
        driverManager.getDriver().manage().timeouts().implicitlyWait(Integer.parseInt(propManager.getProp(Props.IMPLICITLY_WAIT)), TimeUnit.SECONDS);
        driverManager.getDriver().manage().timeouts().pageLoadTimeout(Integer.parseInt(propManager.getProp(Props.LOAD_TIMEOUT)), TimeUnit.SECONDS);
    }

    public static void quit() {
        driverManager.quitDriver();
    }
}
