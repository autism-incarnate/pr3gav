package ru.pr3.framework.pages;

import org.testng.Assert;

public class PageHome extends BasePage{

    public PageGoods goToGoods() {
        waitForClickable(sandBoxButton).click();
        waitForClickable(goodsButton).click();

        return pageManager.getGoodsInstance();
    }

    public PageHome assertPageTitle() {
        Assert.assertEquals(homeButton.getText(), "QualIT", "Invalid page title / Incorrect page opened");

        return this;
    }
}
