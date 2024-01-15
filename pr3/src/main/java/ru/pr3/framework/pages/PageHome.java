package ru.pr3.framework.pages;

import org.testng.Assert;
import ru.pr3.framework.util.TableComparator;
import ru.pr3.framework.util.foodType;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PageHome extends BasePage{

    //Placeholder
    public PageHome checkPage() {
        pageManager.getMenuInstance().checkPage();

        return this;
    }
    public PageGoods goToGoods() {
        pageManager.getMenuInstance().goToGoods();

        return pageManager.getGoodsInstance();
    }

    public PageHome clickReset() {
        pageManager.getMenuInstance().clickReset();

        return this;
    }

    public PageHome insertAndCompare(String name, foodType type, boolean exotic) {
        List<String> r = new ArrayList<>();
        r.add(name); r.add(type.getFruitNameRus()); r.add(String.valueOf(exotic));
        ResultSet init = getAllFromDB();
        insertToDB(name, type, exotic);
        ResultSet current = getAllFromDB();
        Assert.assertTrue(TableComparator.compareTables(init, current, false));
        current = getAllFromDB();
        TableComparator.compareToLast(current, r);

        return this;
    }
}
