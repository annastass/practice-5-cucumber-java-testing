package org.ibs.managers;

import org.ibs.utils.PropConst;

import java.util.concurrent.TimeUnit;

public class InitManager {
    private static final TestPropManager props = TestPropManager.getTestPropManager();
    private static final DriverManager driverManager = DriverManager.getDriverManager();

    public static void initFramework() {
        driverManager.getDriver().manage().window().maximize();
        driverManager.getDriver().manage().timeouts().implicitlyWait(Integer.parseInt(props.getProperty(PropConst.IMPLICITLY_WAIT)), TimeUnit.SECONDS);
        driverManager.getDriver().manage().timeouts().pageLoadTimeout(Integer.parseInt(props.getProperty(PropConst.PAGE_LOAD_TIMEOUT)), TimeUnit.SECONDS);
    }

    public static void quitFramework(){
        driverManager.quitDriver();
    }
}
