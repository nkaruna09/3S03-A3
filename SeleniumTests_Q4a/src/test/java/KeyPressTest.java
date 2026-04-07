import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class KeyPressTest {

    private WebDriver driver;
    private static final String URL =
        "https://the-internet.herokuapp.com/key_presses";

    @BeforeEach
    void setUp() {
        FirefoxOptions options = new FirefoxOptions();
        driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    void testLetterKeyPress() {
        driver.get(URL);

        WebElement body = driver.findElement(By.tagName("body"));
        body.sendKeys("A");

        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.textToBePresentInElementLocated(
                By.id("result"), "A"));

        String result = driver.findElement(By.id("result")).getText();
        assertTrue(result.contains("A"));
    }

    @Test
    void testEnterKeyPress() {
        driver.get(URL);

        WebElement body = driver.findElement(By.tagName("body"));
        body.sendKeys(Keys.ENTER);

        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.textToBePresentInElementLocated(
                By.id("result"), "ENTER"));

        String result = driver.findElement(By.id("result")).getText();
        assertTrue(result.contains("ENTER"));
    }

    @Test
    void testSpacePress() {
        driver.get(URL);

        WebElement body = driver.findElement(By.tagName("body"));
        body.sendKeys(Keys.SPACE);

        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.textToBePresentInElementLocated(
                By.id("result"), "SPACE"));

        String result = driver.findElement(By.id("result")).getText();
        assertTrue(result.contains("SPACE"));
    }

    @Test
    void testMultipleKeys() {
        driver.get(URL);

        WebElement body = driver.findElement(By.tagName("body"));

        body.sendKeys("B");
        body.sendKeys("C");

        String result = driver.findElement(By.id("result")).getText();

        assertTrue(result.contains("B") || result.contains("C"));
    }
}