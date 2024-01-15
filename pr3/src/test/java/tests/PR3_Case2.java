package tests;

import basetest.TestBase;
import org.testng.annotations.Test;
import ru.pr3.framework.util.foodType;

@Test
public class Case2 extends TestBase {
    public void runTest() {
        pageManager.getHomeInstance()
                .checkPage()
                .goToGoods()
                .checkPage()
                .addItem("^_^", foodType.FRUIT, true)
                .resetData()
                .checkPage()
                .goHome()
                .checkPage();
    }
}
