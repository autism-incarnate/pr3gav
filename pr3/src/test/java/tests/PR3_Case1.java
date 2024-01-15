package tests;

import basetest.TestBase;
import org.testng.annotations.Test;
import ru.pr3.framework.util.foodType;

@Test
public class Case1 extends TestBase {
    public void runTest() {
        pageManager.getHomeInstance()
                .checkPage()
                .goToGoods()
                .checkPage()
                .addItem("!Яблоко1", foodType.VEGETABLE, false)
                .resetData()
                .checkPage()
                .goHome()
                .checkPage();
    }
}
