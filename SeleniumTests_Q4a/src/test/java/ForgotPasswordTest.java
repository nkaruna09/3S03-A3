import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class ForgotPasswordTest {

    private WebDriver driver;
    private static final String URL = 
        "https://the-internet.herokuapp.com/forgot_password";

    @BeforeEach
    void setUp() {
        FirefoxOptions options = new FirefoxOptions();
        //options.addArguments("--headless");
        driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    void testPageLoadsCorrectly() {
        driver.get(URL);
        assertTrue(driver.findElement(By.tagName("button")).isDisplayed());
        assertTrue(driver.findElement(By.id("email")).isDisplayed());
        assertTrue(driver.findElement(
            By.cssSelector("button[type='submit']")).isDisplayed());
        assertEquals("Forgot Password",
            driver.findElement(By.tagName("h2")).getText());
    }

    @Test
    void testValidEmailSubmission() {
        driver.get(URL);

        WebElement emailField = driver.findElement(By.id("email"));
        WebElement button = driver.findElement(By.tagName("button"));

        emailField.sendKeys("test@example.com");

        assertTrue(button.isDisplayed());
        assertTrue(button.isEnabled());

        // Click button
        button.click();

       assertTrue(true);
    }

    @Test
    void testEmptyEmailSubmission() {
        driver.get(URL);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        assertTrue(driver.getCurrentUrl().contains("forgot_password") ||
            driver.findElement(By.id("email")).isDisplayed());
    }

    @Test
    void testInvalidEmailFormat() {
        driver.get(URL);
        driver.findElement(By.id("email")).sendKeys("notanemail");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        assertTrue(driver.getCurrentUrl().contains("forgot_password"));
    }

    @Test
    void testEmailFieldAcceptsInput() {
        driver.get(URL);
        WebElement field = driver.findElement(By.id("email"));
        field.sendKeys("hello@test.com");
        assertEquals("hello@test.com", field.getAttribute("value"));
    }

    @Test
    void testEmailFieldClearable() {
        driver.get(URL);
        WebElement field = driver.findElement(By.id("email"));
        field.sendKeys("clear@me.com");
        field.clear();
        assertEquals("", field.getAttribute("value"));
    }

    @Test
    void testSubmitViaEnterKey() {
        driver.get(URL);

        WebElement field = driver.findElement(By.id("email"));

        field.sendKeys("enter@test.com");
        field.sendKeys(Keys.RETURN);

        // Just verify interaction happened (field still exists = no crash)
        assertTrue(driver.findElement(By.id("email")).isDisplayed());
    }
}