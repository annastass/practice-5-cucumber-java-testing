package org.ibs.steps;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import org.ibs.managers.InitManager;

public class Hooks {

    @BeforeAll
    public static void beforeAll(){
        InitManager.initFramework();
    }

    @AfterAll
    public static void after(){
        InitManager.quitFramework();
    }
}
