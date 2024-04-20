package org.ibs.managers;

import org.apache.commons.exec.OS;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


import java.net.MalformedURLException;
import java.net.URI;

import static org.ibs.utils.PropConst.*;

public class DriverManager {

    private static DriverManager INSTANCE = null;
    private WebDriver driver;
    private final TestPropManager prop = TestPropManager.getTestPropManager();

    private DriverManager() {
    }

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
        if ("remote" .equalsIgnoreCase(prop.getProperty("type.driver"))) {
            initRemoteDriver();
        } else {
            if (OS.isFamilyWindows()) {
                initDriverWindowsOsFamily();
            } else if (OS.isFamilyMac()) {
                initDriverMacOsFamily();
            } else if (OS.isFamilyUnix()) {
                initDriverUnixOsFamily();
            }
        }
    }

    private void initRemoteDriver() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setBrowserName(prop.getProperty(TYPE_BROWSER));
        desiredCapabilities.setVersion("109.0");
        desiredCapabilities.setCapability("enableVNC",true);
        //desiredCapabilities.setCapability("enableVideo", false);
        try {
            driver = new RemoteWebDriver(URI.create(prop.getProperty(SELENOID_URL)).toURL(),desiredCapabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initDriverWindowsOsFamily() {
        initDriverAnyOsFamily(PATH_GECKO_DRIVER_WINDOWS, PATH_CHROME_DRIVER_WINDOWS);
    }
    private void initDriverMacOsFamily() {
        initDriverAnyOsFamily(PATH_GECKO_DRIVER_MAC, PATH_CHROME_DRIVER_MAC);
    }
    private void initDriverUnixOsFamily() {
        initDriverAnyOsFamily(PATH_GECKO_DRIVER_UNIX, PATH_CHROME_DRIVER_UNIX);
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
    private void initDriverAnyOsFamily(String gecko, String chrome) {
        switch (prop.getProperty(TYPE_BROWSER)) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver", prop.getProperty(gecko));
                driver = new FirefoxDriver();
                break;
            case "chrome":
                System.setProperty("webdriver.chrome.driver", prop.getProperty(chrome));
                driver = new ChromeDriver();
                break;
            default:
                Assertions.assertTrue(false, "Типа браузера '" + prop.getProperty(TYPE_BROWSER) + "' не существует во фреймворке");
        }
    }
}
