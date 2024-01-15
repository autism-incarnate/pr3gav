package ru.pr3.framework.pages;

import com.beust.ah.A;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import ru.pr3.framework.managers.PageManager;
import ru.pr3.framework.util.TableComparator;
import ru.pr3.framework.util.foodType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;



public class PageGoods extends BasePage {

    private List<List<String>> initTableUI; //HTML Table initial state
    private List<List<String>> currentTableUI; //HTML Table after we perform some actions

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

    //Parse the HTML table so we can work with it
    private List<List<String>> uiParseTable() {
        List<List<String>> sRows = new ArrayList<List<String>>();
        List<WebElement> eRows = webDriverManager.getDriver().
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

    //Sometimes webdriver fails to locate the table, thus we need to wait a little. Proudly stolen from Stackoverflow!
    private WebElement waitForTableLoad() {
        Wait<WebDriver> stubbornWait = new FluentWait<WebDriver>(webDriverManager.getDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        tableRoot = stubbornWait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver e) {
                return webDriverManager.getDriver().findElement(By.xpath(
                        "//div[@class='container-fluid']/table/tbody"));
            }
        });

        return null;
    }

    public PageGoods checkPage() {
        assertPageTitle(goodsTitle, "Список товаров");

        return this;
    }

    public PageHome goHome() {
        pageManager.getMenuInstance().goHome();

        return pageManager.getHomeInstance();
    }

    //Set current table state as default and compare to DB
    public PageGoods initTables() {
        tableRoot = waitForTableLoad();
        initTableUI = uiParseTable();
        currentTableUI = uiParseTable();
        compareTableStates(true);

        return this;
    }

    public PageGoods resetData() {
        pageManager.getMenuInstance().clickReset();
        waitForTableLoad();
        currentTableUI = uiParseTable();
        compareTableStates(true);

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

        Assert.assertEquals(modalTitle.getText(), "Добавление товара",
                "Modal title does not match what is expected!");

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

    //Here we compare table state to it's initial state and it's current state in DB
    private void compareTableStates(boolean expectSizeMatch) {
        ResultSet r = getAllFromDB();
        Assert.assertTrue(TableComparator.compareTables(currentTableUI, r, true),
                "Current UI table does not match DB table state: tables are different or previous entries have been modified!");
        Assert.assertTrue(TableComparator.compareTables(currentTableUI, initTableUI, expectSizeMatch),
                "Current and initial UI table states do not match: tables are different or previous entries have been modified!");
    }

    //Adding a new item, then performing some checks
    public PageGoods addItem(String name, foodType objType, boolean exotic) {
        List<String> newRow = new ArrayList<>();
        newRow.add(name); newRow.add(objType.getFruitNameRus()); newRow.add(String.valueOf(exotic));
        openModal();
        fillInput(name, nameInput);
        waitForVisible(modalDropDown);

        Select dropdown = new Select(modalDropDown);

        switch(objType) {
            case FRUIT -> dropdown.selectByValue(foodType.FRUIT.getFruitNameEng());
            case VEGETABLE -> dropdown.selectByValue(foodType.VEGETABLE.getFruitNameEng());
        }

        if(exotic && !modalCheckBox.isSelected())
            waitForClickable(modalCheckBox).click();
        else if(!exotic && modalCheckBox.isSelected())
            waitForClickable(modalCheckBox).click();

        waitForClickable(modalSave).click();

        tableRoot = waitForTableLoad();
        currentTableUI = uiParseTable();

        compareTableStates(false);
        rowInDB(newRow);

        return this;
    }

    public PageGoods rowInDB(List<String> r) {
        ResultSet current = getAllFromDB();
        TableComparator.compareToLast(current, r);

        return this;
    }
}
