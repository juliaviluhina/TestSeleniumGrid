import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

import static core.CustomConditions.sizeOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;

public class GoogleSearchTest {

    static WebDriver driver;
    static DesiredCapabilities capabilities;

    WebDriverWait wait = new WebDriverWait(driver, 10);
    String results = ".srg>.g";

    @BeforeClass
    public static void setUp() throws MalformedURLException {
//        capabilities = DesiredCapabilities.firefox();
//        capabilities.setBrowserName("firefox");
//        capabilities.setPlatform(Platform.ANY);
//        driver = new RemoteWebDriver(new URL("http://192.168.0.103:5551/wd/hub"), capabilities);

//        capabilities = DesiredCapabilities.firefox();
//        driver = new RemoteWebDriver(new URL("http://192.168.0.102:5552/wd/hub"), capabilities);

//        //capabilities = DesiredCapabilities.chrome();
//        capabilities = DesiredCapabilities.firefox();
//        driver = new RemoteWebDriver(new URL("http://192.168.0.101:5553/wd/hub"), capabilities);//

        capabilities = new DesiredCapabilities(System.getProperty("remote.driver.browser"), "", Platform.ANY);
        driver = new RemoteWebDriver(new URL(System.getProperty("remote.url") + "/wd/hub"), capabilities);
    }


    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

    @Test
    public void testSearchAndFollowLink() {
        open("http://google.com/ncr");

        driver.findElement(By.name("q")).sendKeys("Selenium automates browsers" + Keys.ENTER);
        assertResultsNumber(10);
        assertNthResultContainsText(0, "Selenium automates browsers");

        followResultLink(0);
        assertUrl("http://www.seleniumhq.org/");
    }


    public void open(String url) {
        driver.get(url);
    }

    public void assertResultsNumber(final int number) {
        wait.until(sizeOf(By.cssSelector(results), number));
    }

    public void assertNthResultContainsText(int index, String text) {
        wait.until(textToBePresentInElementLocated(By.cssSelector(results + ":nth-child(" + (++index) + ")"), text));
    }

    public void followResultLink(int index) {
        driver.findElements(By.cssSelector(results)).get(index).findElement(By.cssSelector(".r>a")).click();
    }

    public void assertUrl(String url) {
        wait.until(urlToBe(url));
    }
}

