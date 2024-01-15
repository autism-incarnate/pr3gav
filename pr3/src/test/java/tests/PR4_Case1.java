package tests;

import basetest.TestBase;
import org.testng.annotations.Test;
import ru.pr3.framework.util.foodType;

import java.util.ArrayList;
import java.util.List;

@Test
public class Case3 extends TestBase {
    public void runTest() {
        List<String> r = new ArrayList<>();
        String name = "Qiwi";
        foodType type = foodType.FRUIT;
        boolean exotic = true;

        r.add(name); r.add(type.getFruitNameRus()); r.add(String.valueOf(exotic));

        pageManager.getHomeInstance()
                .checkPage()
                .clickReset()
                .insertAndCompare(name, type, exotic)
                .clickReset();
    }
}
