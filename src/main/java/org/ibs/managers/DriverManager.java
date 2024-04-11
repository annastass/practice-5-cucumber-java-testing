package org.ibs.managers;

import dev.failsafe.internal.util.Assert;

import org.apache.commons.exec.OS;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


import static org.ibs.utils.PropConst.*;
public class DriverManager {

    private static DriverManager INSTANCE = null;
    private WebDriver driver;
    private final TestPropManager prop = TestPropManager.getTestPropManager();

    private DriverManager() {}

    public static DriverManager getDriverManager() {
        if (INSTANCE == null) {
            INSTANCE = new DriverManager();
        }
        return INSTANCE;
    }


    public WebDriver getDriver() {
        if (driver == null) {
            initDriver();
        }
        return driver;
    }

    private void initDriver() {
        if (OS.isFamilyWindows()) {
            initDriverWindowsOsFamily();
        }
    }


    private void initDriverWindowsOsFamily() {
        initDriverAnyOsFamily(PATH_CHROME_DRIVER_WINDOWS);
    }


    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    private void initDriverAnyOsFamily(String chrome) {
        switch (prop.getProperty(TYPE_BROWSER)) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", prop.getProperty(chrome));
                driver = new ChromeDriver();
                break;
            default:
                Assert.isTrue(false, "Типа браузера '" + prop.getProperty(TYPE_BROWSER) + "' не существует во фреймворке");
        }
    }
}
