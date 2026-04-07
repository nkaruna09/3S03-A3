import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.interactions.Actions;

public class UserJourney {

    private WebDriver driver;
    private Actions actions;
    private static final String URL = "https://www.saucedemo.com/";

    @BeforeEach
    void setUp() {
        //initialize for testing 
        System.setProperty("webdriver.gecko.driver", "C:\\Selenium\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        actions = new Actions(driver);
        driver.get(URL); //the website 
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit(); //close firefox
        }
    }

    @Test
    void testLogin() {
        //test login feature
        login();
        assertTrue(driver.getCurrentUrl().contains("inventory.html"));
        System.out.println("Login Success!");
    }

    @Test
    void testProductNavigation() {
        login();
        
        //test clicking on an item
        WebElement backpack = driver.findElement(By.id("item_4_title_link"));
        actions.moveToElement(backpack).click().perform();
        
        assertTrue(driver.getCurrentUrl().contains("inventory-item.html?id=4"));
        System.out.println("Sauce Labs Backpack page has been reached."); 
    }

    @Test
    void testCartManagement() {
        login();
        
        //test viewing item pages 
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        System.out.println("Sauce Labs Backpack added to cart.");
        
        //test adding to and removing from cart from homepage
        WebElement bikeLightAdd = driver.findElement(By.id("add-to-cart-sauce-labs-bike-light"));
        actions.moveToElement(bikeLightAdd).click().perform();
        System.out.println("Bike Light added to cart from home page.");
        
        WebElement bikeLightRemoveButton = driver.findElement(By.id("remove-sauce-labs-bike-light"));
        actions.moveToElement(bikeLightRemoveButton).click().perform();
        
        // Verify badge shows only 1 item after removal
        String cartCount = driver.findElement(By.className("shopping_cart_badge")).getText();
        assertTrue(cartCount.equals("1"));
        System.out.println("Bike Light removed from cart successfully.");
    }

    @Test
    void testCheckoutFlow() {
        login();
        
        // Prepare cart
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        
        //view and use shopping cart page to checkout
        driver.findElement(By.className("shopping_cart_link")).click();
        driver.findElement(By.id("checkout")).click();
        driver.findElement(By.id("first-name")).sendKeys("S");
        driver.findElement(By.id("last-name")).sendKeys("J");
        driver.findElement(By.id("postal-code")).sendKeys("L5B");
        driver.findElement(By.id("continue")).click();
        driver.findElement(By.id("finish")).click();
        
        assertTrue(driver.findElement(By.className("complete-header")).isDisplayed());
        System.out.println("Checkout completed successfully.");
    }

    @Test
    void testSortingLogic() {
        login();

        //try all the different ways to sort items
        String[] dropdown = {"za", "lohi", "hilo", "az"};
        for (String option : dropdown) {
            WebElement sortDropdown = driver.findElement(By.className("product_sort_container")); 
            Select select = new Select(sortDropdown);
            select.selectByValue(option);
            System.out.println("Sorted by " + option);
        }
    }

    @Test
    void testSidebarAndLogout() {
        login();
        
        //open side bar and use all tools
        driver.findElement(By.id("react-burger-menu-btn")).click();
        
        //test sidebar items
        WebElement aboutLink = driver.findElement(By.id("about_sidebar_link")); 
        assertTrue(aboutLink.getAttribute("href").contains("saucelabs.com"));
        System.out.println("About link has been verified.");
        
        WebElement logoutButton = driver.findElement(By.id("logout_sidebar_link"));
        actions.moveToElement(logoutButton).click().perform();
        
        assertTrue(driver.getCurrentUrl().equals("https://www.saucedemo.com/"));
        System.out.println("Logout Success!");
    }

    //private helper method
    private void login() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
    }
}
