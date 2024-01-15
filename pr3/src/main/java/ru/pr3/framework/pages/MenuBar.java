package ru.pr3.framework.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MenuBar extends BasePage{

    @FindBy(xpath = "//a[@class='navbar-brand']")
    private WebElement homeButton;

    @FindBy(xpath = "//a[@id='navbarDropdown']")
    private WebElement sandBoxButton;

    @FindBy(xpath = "//a[@class='dropdown-item'][@href='/food']['Товары']")
    private WebElement goodsButton;

    @FindBy(xpath = "//a[@class='dropdown-item'][@id='reset']")
    private WebElement resetButton;

    public PageHome goHome() {
        waitForClickable(homeButton).click();

        return pageManager.getHomeInstance();
    }

    public PageGoods goToGoods() {
        waitForClickable(sandBoxButton).click();
        waitForClickable(goodsButton).click();

        return pageManager.getGoodsInstance();
    }

    public void clickReset() {
        waitForClickable(sandBoxButton).click();
        waitForClickable(resetButton).click();
    }

    public MenuBar checkPage() {
        assertPageTitle(homeButton, "QualIT");
        return this;
    }
}
