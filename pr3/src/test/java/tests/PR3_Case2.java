package tests;

import basetest.TestBase;
import org.testng.annotations.Test;
import ru.pr3.framework.util.foodType;

@Test
public class PR3_Case2 extends TestBase {
    public void runTest() {
        pageManager.getHomeInstance()
                .checkPage()
                .goToGoods()
                .checkPage()
                .initTables()
                .addItem("^_^", foodType.FRUIT, true)
                .resetData();
    }
}
