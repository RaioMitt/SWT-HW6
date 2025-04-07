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

// this is a separate class for a method
// that would not work when run together with the others

public class AddAndDelete extends TestHelper {
    private String newTitle = "Updated Testing Book";
    private String newDescription = "New Description";
    private String newPrice = "10.00";
    private String newProdType = "Sunglasses";
    private String newUser = "admin_test2";
    private String newPassword = "test";

    @Test
    public void addProductAndDelete() {

        register(newUser, newPassword, newPassword);
        //login(newUser, newPassword);

        waitForElementById("column2");
        waitForElementById("Products");
        driver.findElement(By.linkText("Products")).click();
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

}
