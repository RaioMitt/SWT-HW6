import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BasicTest extends TestHelper {


    private String password = "no";
    private String falsePassword = "Test";
    private String newUser = "test";
    private String newPassword = "test";
    private String originalTitle = "Web Application Testing Book";
    private String newTitle = "Updated Testing Book";
    private String newDescription = "New Description";
    private String newPrice = "10.00";
    private String newProdType = "Sunglasses";
    private String emptyString = "";

    @Test
    public void titleExistsTest(){
        String expectedTitle = "ST Online Store";
        String actualTitle = driver.getTitle();

        assertEquals(expectedTitle, actualTitle);
    }


    /*
    In class Exercise

    Fill in loginLogoutTest() and login mehtod in TestHelper, so that the test passes correctly.

     */
    @Test
    public void loginLogoutTest(){

        register(newUser, newPassword, newPassword);
        //login(newUser, newPassword);
        driver.findElement(By.linkText("Admin")).click();

        WebElement adminHeader = driver.findElement(By.className("admin_header"));
        assertNotNull(adminHeader);

        logout();
        login(newUser, newPassword);
        deleteUser();
    }

    /*
    In class Exercise

     Write a test case, where you make sure, that one can’t log in with a false password

     */
    @Test
    public void loginFalsePassword() {

        register(newUser, newPassword, newPassword);
        login(newUser, falsePassword);

        WebElement notice = driver.findElement(By.id("notice"));
        assertNotNull(notice);
        login(newUser, newPassword);
        deleteUser();

    }
//Created by ME
    @Test
    public void registerNewAdminUser(){
        register(newUser, newPassword, newPassword);
        WebElement notice = driver.findElement(By.id("notice"));
        assertEquals("User "+ newUser + " was successfully created.", notice.getText());
        deleteUser();

    }

    @Test
    public void registerAdminUserWithSameUserame(){
        register(newUser, newPassword, newPassword);
        logout();
        register(newUser, newPassword, newPassword);
        WebElement errorBox = driver.findElement(By.id("error_explanation"));
        WebElement errorMessage = errorBox.findElement(By.tagName("li"));

        assertEquals("Name has already been taken", errorMessage.getText());
        login(newUser, newPassword);
        deleteUser();
    }

    @Test
    public void deleteAdminUser(){
        register(newUser, newPassword, newPassword);
        deleteUser();
        WebElement notice = driver.findElement(By.id("notice"));
        assertEquals("User was successfully deleted.", notice.getText());
    }

    @Test
    public void registerAdminWithWrongConfirmPassword(){
        register(newUser, password, newPassword);
        WebElement errorBox = driver.findElement(By.id("error_explanation"));
        WebElement errorMessage = errorBox.findElement(By.tagName("li"));

        assertEquals("Password confirmation doesn't match Password", errorMessage.getText());
    }

    @Test
    public void editTitleOfProduct(){

        register(newUser, newPassword, newPassword);
        //login(newUser, newPassword);

        driver.findElement(By.linkText("Products")).click();
        waitForElementById("column2");
        driver.findElement(By.linkText(originalTitle)).click();
        waitForElementById("main");
        WebElement editButton = driver.findElement(By.xpath("//div[@class='back_button']/a[text()='Edit']"));
        editButton.click();
        waitForElementById("product_title");
        WebElement titleInput = driver.findElement(By.id("product_title"));

        // Clear the existing text
        titleInput.clear();

        // Enter new title

        titleInput.sendKeys(newTitle);

        // Locate and click the submit button
        WebElement submitButton = driver.findElement(By.xpath("//input[@value='Update Product']"));
        submitButton.click();
        WebElement notice = driver.findElement(By.id("notice"));
        assertEquals("Product was successfully updated.", notice.getText());
        WebElement productTitle = driver.findElement(By.xpath("//div[@class='products_column']/p[strong[text()='Title:']]"));

        // Verify that the title has been updated
        assertEquals("Title: " + newTitle, productTitle.getText());
        waitForElementById("column2");
        driver.findElement(By.xpath("//div[@class='back_button']/a[text()='Edit']")).click();
        waitForElementById("product_title");

        // Clear the existing text
        driver.findElement(By.id("product_title")).clear();

        // Enter new title
        newTitle = originalTitle;
        driver.findElement(By.id("product_title")).sendKeys(newTitle);

        // Locate and click the submit button
        driver.findElement(By.xpath("//input[@value='Update Product']")).click();

        //Deletes admin user
        deleteUser();

    }

    @Test
    public void editTypeOfProduct() {
        String originalProdType = "Books";


        register(newUser, newPassword, newPassword);
        //login(newUser, newPassword);

        driver.findElement(By.linkText("Products")).click();
        waitForElementById("column2");
        driver.findElement(By.linkText(originalTitle)).click();
        waitForElementById("main");
        WebElement editButton = driver.findElement(By.xpath("//div[@class='back_button']/a[text()='Edit']"));
        editButton.click();
        waitForElementById("product_prod_type");

        Select prodTypeDropdown = new Select(driver.findElement(By.id("product_prod_type")));
        prodTypeDropdown.selectByVisibleText(newProdType);


        // Enter new description

        // Locate and click the submit button
        WebElement submitButton = driver.findElement(By.xpath("//input[@value='Update Product']"));
        submitButton.click();
        WebElement notice = driver.findElement(By.id("notice"));
        assertEquals("Product was successfully updated.", notice.getText());

        WebElement productType = driver.findElement(By.xpath("//div[@class='products_column']/p[strong[text()='Type:']]"));
        assertEquals("Type: " + newProdType, productType.getText());

        waitForElementById("column2");
        //Edit back to original
        driver.findElement(By.xpath("//div[@class='back_button']/a[text()='Edit']")).click();
        waitForElementById("product_prod_type");

        // Clear the existing text
        prodTypeDropdown = new Select(driver.findElement(By.id("product_prod_type")));
        prodTypeDropdown.selectByVisibleText(originalProdType);

        // Locate and click the submit button
        driver.findElement(By.xpath("//input[@value='Update Product']")).click();

        //Deletes admin user
        deleteUser();

    }
    @Test
    public void editDescriptionOfProduct() {

        originalTitle = "Web Application Testing Book";

        register(newUser, newPassword, newPassword);
        //login(newUser, newPassword);

        driver.findElement(By.linkText("Products")).click();
        waitForElementById("column2");
        driver.findElement(By.linkText(originalTitle)).click();
        waitForElementById("main");
        WebElement editButton = driver.findElement(By.xpath("//div[@class='back_button']/a[text()='Edit']"));
        editButton.click();
        waitForElementById("product_description");

        WebElement descriptionInput = driver.findElement(By.id("product_description"));

        String oldDescriptionInput = descriptionInput.getText();

        // Clear the existing text

        descriptionInput.clear();

        // Enter new description

        descriptionInput.sendKeys(newDescription);

        // Locate and click the submit button
        WebElement submitButton = driver.findElement(By.xpath("//input[@value='Update Product']"));
        submitButton.click();
        WebElement notice = driver.findElement(By.id("notice"));
        assertEquals("Product was successfully updated.", notice.getText());
        WebElement productDescription = driver.findElement(By.xpath("//div[@class='products_column']/p[strong[text()='Description:']]"));

        // Verify that the title has been updated
        assertEquals("Description: " + newDescription, productDescription.getText());
        waitForElementById("column2");
        //Edit back to original
        driver.findElement(By.xpath("//div[@class='back_button']/a[text()='Edit']")).click();
        waitForElementById("product_description");

        // Clear the existing text
        driver.findElement(By.id("product_description")).clear();

        // Enter new title
        newTitle = originalTitle;
        driver.findElement(By.id("product_description")).sendKeys(oldDescriptionInput);

        // Locate and click the submit button
        driver.findElement(By.xpath("//input[@value='Update Product']")).click();

        //Deletes admin user
        deleteUser();

    }
    @Test
    public void editPriceOfProduct(){

        String originalPrice = "29.99";

        register(newUser, newPassword, newPassword);
        //login(newUser, newPassword);

        driver.findElement(By.linkText("Products")).click();
        waitForElementById("column2");
        driver.findElement(By.linkText(originalTitle)).click();
        waitForElementById("main");
        WebElement editButton = driver.findElement(By.xpath("//div[@class='back_button']/a[text()='Edit']"));
        editButton.click();
        waitForElementById("product_price");
        WebElement priceInput = driver.findElement(By.id("product_price"));

        // Clear the existing text
        priceInput.clear();

        // Enter new title

        priceInput.sendKeys(newPrice);

        // Locate and click the submit button
        WebElement submitButton = driver.findElement(By.xpath("//input[@value='Update Product']"));
        submitButton.click();
        WebElement notice = driver.findElement(By.id("notice"));
        assertEquals("Product was successfully updated.", notice.getText());
        WebElement productPrice = driver.findElement(By.xpath("//div[@class='products_column']/p[strong[text()='Price:']]"));

        // Verify that the title has been updated
        assertEquals("Price: €" + newPrice, productPrice.getText());
        waitForElementById("column2");
        driver.findElement(By.xpath("//div[@class='back_button']/a[text()='Edit']")).click();
        waitForElementById("product_price");

        // Clear the existing text
        driver.findElement(By.id("product_price")).clear();

        // Enter new title
        newPrice = originalPrice;
        driver.findElement(By.id("product_price")).sendKeys(newPrice);

        // Locate and click the submit button
        driver.findElement(By.xpath("//input[@value='Update Product']")).click();

        //Deletes admin user
        deleteUser();

    }

    @Test //negative
    public void editPriceOfProductString(){
        String newPrice = "number";
        String originalPrice = "29.99";

        //register(newUser, newPassword, newPassword);
        login(newUser, newPassword);

        driver.findElement(By.linkText("Products")).click();
        waitForElementById("column2");
        driver.findElement(By.linkText(originalTitle)).click();
        waitForElementById("main");
        WebElement editButton = driver.findElement(By.xpath("//div[@class='back_button']/a[text()='Edit']"));
        editButton.click();
        waitForElementById("product_price");
        WebElement priceInput = driver.findElement(By.id("product_price"));

        // Clear the existing text
        priceInput.clear();

        // Enter new title

        priceInput.sendKeys(newPrice);

        // Locate and click the submit button
        WebElement submitButton = driver.findElement(By.xpath("//input[@value='Update Product']"));
        submitButton.click();

        WebElement errorBox = driver.findElement(By.id("error_explanation"));
        WebElement errorMessage = errorBox.findElement(By.tagName("li"));

        assertEquals("Price is not a number", errorMessage.getText());

        //waitForElementById("column2");
        //driver.findElement(By.xpath("//div[@class='back_button']/a[text()='Edit']")).click();
        //waitForElementById("product_price");

        // Clear the existing text
        driver.findElement(By.id("product_price")).clear();

        // Enter new title
        newPrice = originalPrice;
        driver.findElement(By.id("product_price")).sendKeys(newPrice);

        // Locate and click the submit button
        driver.findElement(By.xpath("//input[@value='Update Product']")).click();

        //Deletes admin user
        deleteUser();

    }

    @Test //negative
    public void editTitleOfProductEmpty(){

        register(newUser, newPassword, newPassword);
        //login(newUser, newPassword);

        driver.findElement(By.linkText("Products")).click();
        waitForElementById("column2");
        driver.findElement(By.linkText(originalTitle)).click();
        waitForElementById("main");
        WebElement editButton = driver.findElement(By.xpath("//div[@class='back_button']/a[text()='Edit']"));
        editButton.click();
        waitForElementById("product_title");
        WebElement titleInput = driver.findElement(By.id("product_title"));

        // Clear the existing text
        titleInput.clear();

        // Enter new title

        titleInput.sendKeys(emptyString);

        // Locate and click the submit button
        WebElement submitButton = driver.findElement(By.xpath("//input[@value='Update Product']"));
        submitButton.click();

        WebElement errorBox = driver.findElement(By.id("error_explanation"));
        WebElement errorMessage = errorBox.findElement(By.tagName("li"));

        assertEquals("Title can't be blank", errorMessage.getText());

        //waitForElementById("column2");
        //driver.findElement(By.xpath("//div[@class='back_button']/a[text()='Edit']")).click();
        //waitForElementById("product_title");

        // Clear the existing text
        driver.findElement(By.id("product_title")).clear();

        // Enter new title
        newTitle = originalTitle;
        driver.findElement(By.id("product_title")).sendKeys(newTitle);

        // Locate and click the submit button
        driver.findElement(By.xpath("//input[@value='Update Product']")).click();

        //Deletes admin user
        deleteUser();

    }

    @Test //negative
    public void editPriceOfProductNegative(){
        String newPrice = "-5.00";
        String originalPrice = "29.99";

        register(newUser, newPassword, newPassword);
        //login(newUser, newPassword);

        driver.findElement(By.linkText("Products")).click();
        waitForElementById("column2");
        driver.findElement(By.linkText(originalTitle)).click();
        waitForElementById("main");
        WebElement editButton = driver.findElement(By.xpath("//div[@class='back_button']/a[text()='Edit']"));
        editButton.click();
        waitForElementById("product_price");
        WebElement priceInput = driver.findElement(By.id("product_price"));

        // Clear the existing text
        priceInput.clear();

        // Enter new title

        priceInput.sendKeys(newPrice);

        // Locate and click the submit button
        WebElement submitButton = driver.findElement(By.xpath("//input[@value='Update Product']"));
        submitButton.click();

        WebElement errorBox = driver.findElement(By.id("error_explanation"));
        WebElement errorMessage = errorBox.findElement(By.tagName("li"));

        assertEquals("Price must be greater than or equal to 0.01", errorMessage.getText());

        //waitForElementById("column2");
        //driver.findElement(By.xpath("//div[@class='back_button']/a[text()='Edit']")).click();
        //waitForElementById("product_price");

        // Clear the existing text
        driver.findElement(By.id("product_price")).clear();

        // Enter new title
        newPrice = originalPrice;
        driver.findElement(By.id("product_price")).sendKeys(newPrice);

        // Locate and click the submit button
        driver.findElement(By.xpath("//input[@value='Update Product']")).click();

        //Deletes admin user
        deleteUser();

    }

    @Test
    public void addProductAndDelete(){

        register(newUser, newPassword, newPassword);
        //login(newUser, newPassword);

        driver.findElement(By.linkText("Products")).click();
        waitForElementById("column2");
        driver.findElement(By.linkText("New product")).click();

        addProduct(newTitle, newDescription, newPrice, newProdType);

        WebElement newProductRow = driver.findElement(By.xpath("//tr[@id='" + newTitle + "']"));
        assertNotNull("New product was not found in the table", newProductRow);

        WebElement deleteButton = newProductRow.findElement(By.xpath(".//a[contains(@href, '/products') and contains(text(), 'Delete')]"));
        deleteButton.click();

        WebElement notice = driver.findElement(By.id("notice"));
        assertEquals("Product was successfully destroyed.", notice.getText());

        deleteUser();

    }

    @Test //negative
    public void editDescriptionOfProductEmpty() {

        register(newUser, newPassword, newPassword);
        //login(newUser, newPassword);

        driver.findElement(By.linkText("Products")).click();
        waitForElementById("column2");
        driver.findElement(By.linkText(originalTitle)).click();
        waitForElementById("main");
        WebElement editButton = driver.findElement(By.xpath("//div[@class='back_button']/a[text()='Edit']"));
        editButton.click();
        waitForElementById("product_description");

        WebElement descriptionInput = driver.findElement(By.id("product_description"));

        String oldDescriptionInput = descriptionInput.getText();

        // Clear the existing text

        descriptionInput.clear();

        // Enter new description

        descriptionInput.sendKeys(emptyString);

        // Locate and click the submit button
        WebElement submitButton = driver.findElement(By.xpath("//input[@value='Update Product']"));
        submitButton.click();


        WebElement errorBox = driver.findElement(By.id("error_explanation"));
        WebElement errorMessage = errorBox.findElement(By.tagName("li"));

        assertEquals("Description can't be blank", errorMessage.getText());

        // Clear the existing text
        driver.findElement(By.id("product_description")).clear();

        // Enter new title
        newTitle = originalTitle;
        driver.findElement(By.id("product_description")).sendKeys(oldDescriptionInput);

        // Locate and click the submit button
        driver.findElement(By.xpath("//input[@value='Update Product']")).click();

        //Deletes admin user
        deleteUser();

    }
}
