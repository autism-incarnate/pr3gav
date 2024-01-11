package ru.pr3.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import ru.pr3.framework.util.TableComparator;
import ru.pr3.framework.util.foodType;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;



public class PageGoods extends BasePage {

    private List<List<String>> initTable;
    private List<List<String>> currentTable;
    private TableComparator tc = new TableComparator();

    @FindBy(xpath = "//a[@class='dropdown-item'][@id='reset']")
    private WebElement resetButton;

    @FindBy(xpath = "//button[@data-toggle='modal']")
    private WebElement addButton;

    @FindBy(xpath = "//div[@class='container-fluid']/*[1]")
    private WebElement goodsTitle;

    @FindBy(xpath = "//div[@id='editModal']")
    private WebElement modalWrapper;

    @FindBy(xpath = "//h5[@class='modal-title']")
    private WebElement modalTitle;

    @FindBy(xpath = "//button[@class='close']")
    private WebElement closeModalButton;

    @FindBy(xpath = "//input[@id='name']")
    private WebElement nameInput;

    @FindBy(xpath = "//select[@id='type']")
    private WebElement modalDropDown;

    @FindBy(xpath = "//input[@type='checkbox']")
    private WebElement modalCheckBox;

    @FindBy(xpath = "//button[@id='save']")
    private WebElement modalSave;

    @FindBy(xpath = "//div[@class='container-fluid']/table/tbody")
    private WebElement tableRoot;

    private List<List<String>> writeTable() {
        List<List<String>> sRows = new ArrayList<List<String>>();
        List<WebElement> eRows = driverManager.getDriver().
                findElements(By.xpath("//div[@class='container-fluid']/table/tbody/tr"));

        for(WebElement i : eRows){
            List<WebElement> eCols = i.findElements(By.xpath("./td"));
            List<String> nString = new ArrayList<String>();
            for(WebElement n : eCols)
                nString.add(n.getText());
            sRows.add(nString);
        }

        return sRows;
    }

    private WebElement waitForTableLoad() {
        Wait<WebDriver> stubbornWait = new FluentWait<WebDriver>(driverManager.getDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        tableRoot = stubbornWait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver e) {
                return driverManager.getDriver().findElement(By.xpath(
                        "//div[@class='container-fluid']/table/tbody"));
            }
        });

        return null;
    }


    public PageGoods initTables() {
        tableRoot = waitForTableLoad();
        initTable = writeTable();
        currentTable = writeTable();

        return this;
    }

    public PageGoods resetData() {

        waitForClickable(sandBoxButton).click();
        waitForClickable(resetButton).click();

        tableRoot = waitForTableLoad();

        currentTable = writeTable();

        Assert.assertTrue(tc.tablesEqual(initTable, currentTable), "Tables have not been properly reset!");

        return this;
    }

    public PageGoods assertPageTitle() {
        Assert.assertEquals(goodsTitle.getText(), "Список товаров",
                "Invalid page title / Incorrect page opened");

        return this;
    }

    public PageGoods openModal() {
        waitForClickable(addButton).click();
        if(!modalIsVisible())
            Assert.fail("Modal is not visible!");

        return this;
    }

    private boolean modalIsVisible() {
        waitForVisible(modalTitle).isDisplayed();
        Assert.assertEquals(modalTitle.getText(), "Добавление товара", "Modal title does not match expected!");
        if(modalWrapper.getAttribute("class").equals("modal fade show")) {
            return true;
        }

        return false;
    }

    public PageGoods closeModal() {
        waitForClickable(closeModalButton).click();
        if(modalIsVisible())
            Assert.fail("Modal is still visible!");

        return this;
    }

    public PageHome goHome() {
        waitForClickable(homeButton).click();

        return pageManager.getHomeInstance();
    }

    public PageGoods addItem(String name, foodType objType, boolean exotic) {
        openModal();

        waitForClickable(nameInput).click();
        nameInput.sendKeys(name);

        waitForVisible(modalDropDown);
        Select dropdown = new Select(modalDropDown);
        switch(objType) {
            case FRUIT -> dropdown.selectByValue(String.valueOf(foodType.FRUIT));
            case VEGETABLE -> dropdown.selectByValue(String.valueOf(foodType.VEGETABLE));
        }

        if(exotic && !modalCheckBox.isSelected())
            waitForClickable(modalCheckBox).click();
        else if(!exotic && modalCheckBox.isSelected())
            waitForClickable(modalCheckBox).click();

        waitForClickable(modalSave).click();

        tableRoot = waitForTableLoad();
        currentTable = writeTable();

        List<String> lastRow = currentTable.get(currentTable.size() - 1);

        Assert.assertEquals(name, lastRow.get(0), "Food names do not match!");
        Assert.assertEquals(objType.getFruitType(), lastRow.get(1), "Food types do not match!");
        Assert.assertEquals(String.valueOf(exotic), lastRow.get(2), "Exotic types do no match!");

        Assert.assertTrue(tc.previousUnchanged(initTable, currentTable), "Previous entries have been modified!");

        return this;
    }



}
