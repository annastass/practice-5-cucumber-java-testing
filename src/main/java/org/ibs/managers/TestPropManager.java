package org.ibs.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestPropManager {
    private final Properties properties = new Properties();
    private static TestPropManager INSTANCE = null;

    private TestPropManager() {
        loadApplicationProperties();
    }

    public static TestPropManager getTestPropManager() {
        if (INSTANCE == null) {
            INSTANCE = new TestPropManager();
        }
        return INSTANCE;
    }

    private void loadApplicationProperties() {
        try {
            String propFileName = System.getProperty("propFile", "application") + ".properties";
            File file = new File("src/main/resources/" + propFileName);
            properties.load(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
