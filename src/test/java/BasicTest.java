import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BasicTest extends TestHelper {


    private String password = "no";
    private String falsePassword = "Test";
    private String newUser = "admin_test2";
    private String newPassword = "test";
    private String originalTitle = "Web Application Testing Book";
    private String newTitle = "Updated Testing Book";
    private String newDescription = "New Description";
    private String newPrice = "10.00";
    private String newProdType = "Sunglasses";
    private String emptyString = "";
    private String productName = "B45593 Sunglasses";
    private String name = "John Smith";
    private String address = "Tartu";
    private String email = "john.smith@gmail.com";
    private String expectedPrice = "€260.00";
    private String searchWord = "Sunglasses";
    private String category = "Sunglasses";
    private String expectedPriceOneProduct = "€26.00";




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
       // deleteUser();
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

        register(newUser, newPassword, newPassword);
        //login(newUser, newPassword);

        WebDriverWait wait = new WebDriverWait(driver, 10); // 10 sekundit
        WebElement productsLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Products")));
        productsLink.click();

        //driver.findElement(By.linkText("Products")).click();
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

    // tests for customer

    @Test
    public void addProductToCart(){
        driver.get(baseUrl);
        waitForElementById("column2");
        // add product to cart
        WebElement cartButton = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div[3]/div[2]/form/input[1]"));
        cartButton.click();

        //check if the product appears in cart
        waitForElementById("current_item");
        WebElement currentItem = driver.findElement(By.id("current_item"));
        Assert.assertThat(currentItem.getText(), CoreMatchers.containsString(productName));
    }

    @Test
    public void increaseQuantityInCart(){
        driver.get(baseUrl);
        waitForElementById("column2");
        // add product to cart
        WebElement cartButton = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div[3]/div[2]/form/input[1]"));
        cartButton.click();

        //increase the quantity
        waitForElementById("current_item");
        WebElement increaseButton = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/table/tbody/tr[1]/td[5]/a"));
        increaseButton.click();
        WebElement amount = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/table/tbody/tr[1]/td[1]"));
        Assert.assertThat(amount.getText(), CoreMatchers.containsString("2"));


    }

    @Test
    public void decreaseQuantityInCart(){
        driver.get(baseUrl);
        waitForElementById("column2");
        // add products to cart
        WebElement cartButton = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div[3]/div[2]/form/input[1]"));
        cartButton.click();
        cartButton.click();

        // decrease quantity
        waitForElementById("current_item");
        WebElement decreaseButton = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/table/tbody/tr[1]/td[4]/a"));
        decreaseButton.click();
        WebElement amount = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/table/tbody/tr[1]/td[1]"));
        Assert.assertThat(amount.getText(), CoreMatchers.containsString("1"));

    }

    @Test
    public void deleteItemsOneByOne(){
        driver.get(baseUrl);
        waitForElementById("column2");
        // adding product 1
        WebElement cartButton = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div[3]/div[2]/form/input[1]"));
        cartButton.click();

        //adding product 2
        cartButton = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div[5]/div[2]/form/input[1]"));
        cartButton.click();


        //remove product 1 from cart
        WebElement deleteButton = driver.findElement(By.id("delete_button"));
        deleteButton.click();

        // check that the item was removed
        waitForElementById("notice");
        WebElement notice = driver.findElement(By.id("notice"));
        assertEquals("Item successfully deleted from cart.", notice.getText());

    }

    @Test
    public void emptyCart(){
        driver.get(baseUrl);
        waitForElementById("column2");
        // adding product 1
        WebElement cartButton = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div[3]/div[2]/form/input[1]"));
        cartButton.click();

        //adding product 2
        cartButton = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div[5]/div[2]/form/input[1]"));
        cartButton.click();

        // click on empty cart
        WebElement emptyCartButton = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/form[1]/input[2]"));
        emptyCartButton.click();

        // check if the cart was emptied successfully
        waitForElementById("notice");
        WebElement notice = driver.findElement(By.id("notice"));
        assertEquals("Cart successfully deleted.", notice.getText());

    }

    @Test
    public void checkoutPurcheseOrder(){
        driver.get(baseUrl);
        waitForElementById("column2");

        // add 10 products to the cart
        WebElement cartButton = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div[3]/div[2]/form/input[1]"));
        for (int i = 0; i < 10; i++) {
            cartButton.click();
        }

        // click on the checkout button
        WebElement chechoutButton = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/form[2]/input"));
        chechoutButton.click();

        waitForElementById("order_page");

        // entering the order info
        WebElement nameInput = driver.findElement(By.id("order_name"));
        nameInput.sendKeys(name);

        WebElement addressInput = driver.findElement(By.id("order_address"));
        addressInput.sendKeys(address);

        WebElement emailInput = driver.findElement(By.id("order_email"));
        emailInput.sendKeys(email);

        WebElement paymentMethod = driver.findElement(By.id("order_pay_type"));
        new Select(paymentMethod).selectByValue("Purchase order");

        // click Place order
        WebElement orderButton = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/form/div[5]/input"));
        orderButton.click();

        // cheching the info on the order_receipt
        waitForElementById("order_receipt");
        WebElement totalAmountMoney = driver.findElement(By.className("total_cell"));
        assertEquals(expectedPrice, totalAmountMoney.getText());


    }

    @Test
    public void checkoutCheck(){
        driver.get(baseUrl);
        waitForElementById("column2");

        // add 10 products to the cart
        WebElement cartButton = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div[3]/div[2]/form/input[1]"));
        for (int i = 0; i < 10; i++) {
            cartButton.click();
        }

        // click on the checkout button
        WebElement chechoutButton = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/form[2]/input"));
        chechoutButton.click();

        waitForElementById("order_page");

        // entering the order info
        WebElement nameInput = driver.findElement(By.id("order_name"));
        nameInput.sendKeys(name);

        WebElement addressInput = driver.findElement(By.id("order_address"));
        addressInput.sendKeys(address);

        WebElement emailInput = driver.findElement(By.id("order_email"));
        emailInput.sendKeys(email);

        WebElement paymentMethod = driver.findElement(By.id("order_pay_type"));
        new Select(paymentMethod).selectByValue("Check");

        // click Place order
        WebElement orderButton = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/form/div[5]/input"));
        orderButton.click();

        // cheching the info on the order_receipt
        waitForElementById("order_receipt");
        WebElement totalAmountMoney = driver.findElement(By.className("total_cell"));
        assertEquals(expectedPrice, totalAmountMoney.getText());
    }


    @Test
    public void checkoutCreditCard(){
        driver.get(baseUrl);
        waitForElementById("column2");

        // add 10 products to the cart
        WebElement cartButton = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div[3]/div[2]/form/input[1]"));
        for (int i = 0; i < 10; i++) {
            cartButton.click();
        }

        // click on the checkout button
        WebElement chechoutButton = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/form[2]/input"));
        chechoutButton.click();

        waitForElementById("order_page");

        // entering the order info
        WebElement nameInput = driver.findElement(By.id("order_name"));
        nameInput.sendKeys(name);

        WebElement addressInput = driver.findElement(By.id("order_address"));
        addressInput.sendKeys(address);

        WebElement emailInput = driver.findElement(By.id("order_email"));
        emailInput.sendKeys(email);

        WebElement paymentMethod = driver.findElement(By.id("order_pay_type"));
        new Select(paymentMethod).selectByValue("Credit card");

        // click Place order
        WebElement orderButton = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/form/div[5]/input"));
        orderButton.click();

        // checking the price info on the order_receipt
        waitForElementById("order_receipt");
        WebElement totalAmountMoney = driver.findElement(By.className("total_cell"));
        assertEquals(expectedPrice, totalAmountMoney.getText());


    }

    @Test
    public void checkoutWithEmptyInfo(){
        driver.get(baseUrl);
        waitForElementById("column2");

        // add 5 products to the cart
        WebElement cartButton = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div[3]/div[2]/form/input[1]"));
        for (int i = 0; i < 5; i++) {
            cartButton.click();
        }

        // click on the checkout button
        WebElement chechoutButton = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/form[2]/input"));
        chechoutButton.click();

        waitForElementById("order_page");

        // entering the order info
        WebElement nameInput = driver.findElement(By.id("order_name"));
        nameInput.sendKeys(emptyString);

        WebElement addressInput = driver.findElement(By.id("order_address"));
        addressInput.sendKeys(emptyString);

        WebElement emailInput = driver.findElement(By.id("order_email"));
        emailInput.sendKeys(emptyString);

        WebElement paymentMethod = driver.findElement(By.id("order_pay_type"));
        new Select(paymentMethod).selectByValue("Credit card");

        // click Place order
        WebElement orderButton = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/form/div[5]/input"));
        orderButton.click();

        //checking if the page shows an error message, that the info fields cant be empty
        WebElement nameError = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/form/div[1]/ul/li[1]"));
        assertEquals("Name can't be blank", nameError.getText());

        WebElement addressError = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/form/div[1]/ul/li[2]"));
        assertEquals("Address can't be blank", addressError.getText());

        WebElement emailError = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/form/div[1]/ul/li[3]"));
        assertEquals("Email can't be blank", emailError.getText());

    }

    @Test
    public void checkoutWithOneProduct(){
        driver.get(baseUrl);
        waitForElementById("column2");

        // add 1 product to the cart
        WebElement cartButton = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div[3]/div[2]/form/input[1]"));
        cartButton.click();

        // click on the checkout button
        WebElement chechoutButton = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/form[2]/input"));
        chechoutButton.click();

        waitForElementById("order_page");

        // entering the order info
        WebElement nameInput = driver.findElement(By.id("order_name"));
        nameInput.sendKeys(name);

        WebElement addressInput = driver.findElement(By.id("order_address"));
        addressInput.sendKeys(address);

        WebElement emailInput = driver.findElement(By.id("order_email"));
        emailInput.sendKeys(email);

        WebElement paymentMethod = driver.findElement(By.id("order_pay_type"));
        new Select(paymentMethod).selectByValue("Credit card");

        // click Place order
        WebElement orderButton = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/form/div[5]/input"));
        orderButton.click();

        // checking the info on the order receipt page
        waitForElementById("order_receipt");

        WebElement totalAmountMoney = driver.findElement(By.className("total_cell"));
        assertEquals(expectedPriceOneProduct, totalAmountMoney.getText());

        WebElement purchasedProduct = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/table/tbody/tr[1]/td[2]"));
        assertEquals(productName, purchasedProduct.getText());
    }

    @Test
    public void searchProductsByName(){
        driver.get(baseUrl);
        waitForElementById("column2");

        // searching by name
        WebElement search_input = driver.findElement(By.id("search_input"));
        search_input.sendKeys(searchWord);

        // testing that searching worked
        var entries = driver.findElements(By.cssSelector("div.entry:not([style*='display: none'])"));

        for (WebElement entry : entries) {
            var entry_id = entry.getAttribute("id");
            Assert.assertThat(entry_id, CoreMatchers.containsString(searchWord));
        }
    }

    @Test
    public void filterByCategory(){
        driver.get(baseUrl);
        waitForElementById("column2");

        // clicking on the category button
        WebElement categoryButton = driver.findElement(By.xpath("/html/body/div[2]/div/ul/li[2]/a"));
        categoryButton.click();

        waitForElementById("store_header");

        // check that all the elements being displayed now are of the correct category
        var entries = driver.findElements(By.cssSelector("div.entry:not([style*='display: none'])"));
        for (WebElement entry : entries) {
            var entry_id = entry.getAttribute("id");
            Assert.assertThat(entry_id, CoreMatchers.containsString(category));
        }
    }

}
