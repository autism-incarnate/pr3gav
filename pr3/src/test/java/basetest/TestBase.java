package basetest;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import ru.pr3.framework.managers.WebDriverManager;
import ru.pr3.framework.managers.InitManager;
import ru.pr3.framework.managers.PageManager;
import ru.pr3.framework.managers.PropManager;
import static ru.pr3.framework.util.Props.*;

public class TestBase {
    private final WebDriverManager webDriverManager = WebDriverManager.getDriverInstance();

    protected PageManager pageManager = PageManager.getPageManagerInstance();

    @BeforeClass
    public static void beforeAll() { InitManager.init(); }

    @BeforeTest
    public void beforeEach() {
        webDriverManager.getDriver().get(PropManager.getPropInstance().getProp(BASE_URL));
    }

    @AfterClass
    public static void afterAll() {
        InitManager.quit();
    }

}
