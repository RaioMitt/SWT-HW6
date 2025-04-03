import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class TestHelper {

    static WebDriver driver;
    final int waitForResposeTime = 4;
	
	// here write a link to your admin website (e.g. http://my-app.herokuapp.com/admin)
    String baseUrlAdmin = "http://127.0.0.1:3000/admin";
	
	// here write a link to your website (e.g. http://my-app.herokuapp.com/)
    String baseUrl = "http://127.0.0.1:3000/";

    @Before
    public void setUp(){

        // if you use Chrome:
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\raiomitt\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();

        // if you use Firefox:
        //System.setProperty("webdriver.gecko.driver", "C:\\Users\\...\\geckodriver.exe");
        //driver = new FirefoxDriver();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(baseUrl);

    }

    void goToPage(String page){
        WebElement elem = driver.findElement(By.linkText(page));
        elem.click();
        waitForElementById(page);
    }

    void waitForElementById(String id){
        new WebDriverWait(driver, waitForResposeTime).until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
    }

    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }
    void register(String newUser, String newPassword, String confirmPassword){
        driver.get(baseUrlAdmin);
        driver.findElement(By.linkText("Register")).click();
        driver.findElement(By.id("user_name")).sendKeys(newUser);
        driver.findElement(By.id("user_password")).sendKeys(newPassword);
        driver.findElement(By.id("user_password_confirmation")).sendKeys(confirmPassword);

        By registerButtonXpath = By.xpath("//input[@value='Create User']");
        // click on the button
        driver.findElement(registerButtonXpath).click();
    }

    void login(String username, String password){

        driver.get(baseUrlAdmin);

        driver.findElement(By.linkText("Login")).click();

        driver.findElement(By.id("name")).sendKeys(username);

        driver.findElement(By.id("password")).sendKeys(password);

        // ...

        By loginButtonXpath = By.xpath("//input[@value='Login']");
        // click on the button
        driver.findElement(loginButtonXpath).click();


    }

    void logout(){
        WebElement logout = driver.findElement(By.linkText("Logout"));
        logout.click();

        waitForElementById("Admin");
    }
    public void deleteUser(){
        driver.get(baseUrlAdmin);
        driver.findElement(By.linkText("Admin")).click();
        waitForElementById("Admin");
        driver.findElement(By.linkText("Delete")).click();
    }
    public void addProduct(String productName, String productDescription, String productPrice, String productType){
        //Adds title
        waitForElementById("product_title");
        WebElement titleInput = driver.findElement(By.id("product_title"));
        titleInput.sendKeys(productName);

        //Adds description
        WebElement descriptionInput = driver.findElement(By.id("product_description"));
        descriptionInput.sendKeys(productDescription);

        //Adds price
        WebElement priceInput = driver.findElement(By.id("product_price"));
        priceInput.sendKeys(productPrice);

        //Selects type
        Select prodTypeDropdown = new Select(driver.findElement(By.id("product_prod_type")));
        prodTypeDropdown.selectByVisibleText(productType);

        //Confirms product
        WebElement submitButton = driver.findElement(By.xpath("//input[@value='Create Product']"));
        submitButton.click();
    }

    @After
    public void tearDown(){
        driver.close();
    }

}