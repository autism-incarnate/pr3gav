package tests;

import basetest.TestBase;
import org.testng.annotations.Test;
import ru.pr3.framework.util.foodType;

import java.util.ArrayList;
import java.util.List;

@Test
public class PR4_Case1 extends TestBase {
    public void runTest() {
        String name = "Qiwi";
        foodType type = foodType.FRUIT;
        boolean exotic = true;

        pageManager.getHomeInstance()
                .checkPage()
                .insertAndCompare(name, type, exotic)
                .clickReset();
    }
}
