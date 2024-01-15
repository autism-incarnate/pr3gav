package tests;

import basetest.TestBase;
import org.testng.annotations.Test;
import ru.pr3.framework.util.foodType;

@Test
public class PR3_Case1 extends TestBase {
    public void runTest() {
        pageManager.getHomeInstance()
                .checkPage()
                .goToGoods()
                .checkPage()
                .initTables()
                .addItem("!Яблоко1", foodType.VEGETABLE, false)
                .resetData();

    }
}
