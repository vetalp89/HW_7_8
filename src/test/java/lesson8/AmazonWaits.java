package test.java.lesson8;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class AmazonWaits {

    WebDriver driver;
    WebDriverWait wait;
    By signinBtn = By.id("nav-link-accountList");
    By createAccountSubmit = By.id("createAccountSubmit");
    By inputName = By.xpath("//*[@id=\"ap_customer_name\"]");
    By inputEmail = By.xpath("//*[@id=\"ap_email\"]");
    By inputPassword = By.xpath("//*[@id=\"ap_password\"]");
    By inputPasswordCheck = By.id("ap_password_check");
    By submitBtn = By.xpath("//*[@id=\"continue\"]");
    By alertBlock = By.id("auth-customerName-missing-alert");
    By passwordAlertBlock = By.xpath("//*[@id=\"auth-passwordCheck-missing-alert\"]/div/div");
    String expectedColor = "rgba(221, 0, 0, 1)";
    String expectedAlert = "block";
    String actualNameColor;
    String actualEmailColor;
    String actualPasswordColor;
    String actualPasswordCheckColor;
    String actualAlert;
    String actualPasswordAlert;


    @BeforeMethod
    public void initBrowser() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("start-maximized");
        driver = new ChromeDriver(chromeOptions);
        wait = new WebDriverWait(driver,5);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get("https://www.amazon.com/");
        click(signinBtn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(createAccountSubmit));
        click(createAccountSubmit);
    }

    public void click(By link) {
        wait.until(ExpectedConditions.elementToBeClickable(link)).click();
    }

    public String getColor(By color) {
        return driver.findElement(color).getCssValue("border-top-color");
    }

    public void borderAlert(By borderAlert) {
        wait.until(ExpectedConditions
                .attributeToBe(driver.findElement(borderAlert), "border-top-color", expectedColor));
    }
    public String getAlertText(By alertText) {
        return driver.findElement(alertText).getCssValue(("display"));
    }

    public void alert(By fieldAlert) {
        wait.until(ExpectedConditions
                .attributeToBe(driver.findElement(fieldAlert), "display", expectedAlert));
    }



    @Test
    public void allEmpty()  {

        //        Click on each input
        click(inputName);
        click(inputEmail);
        click(inputPassword);
        //         Click button
        click(submitBtn);


        //        Check for for empty input

        borderAlert(inputName);
        actualNameColor = getColor(inputName);
        borderAlert(inputEmail);
        actualEmailColor = getColor(inputEmail);
        borderAlert(inputPassword);
        actualPasswordColor = getColor(inputPassword);

        //        Check for alert text "Enter your..."
        alert(alertBlock);
        actualAlert = getAlertText(alertBlock);


        //        Test for for empty input
        assertEquals(
                actualNameColor,
                expectedColor,
                "Expected border color of the field 'Your name' to be " + expectedColor +
                        " but is " + actualNameColor
        );

        assertEquals(
                expectedColor,
                actualEmailColor,
                "Expected border color of the field 'Email' to be " + expectedColor +
                        " but is " + actualEmailColor
        );

        assertEquals(
                expectedColor,
                actualPasswordColor,
                "Expected border color of the field 'Password' to be " + expectedColor +
                        " but is " + actualPasswordColor
        );
        //        Test for alert text "Enter your..."
        assertEquals(
                actualAlert,
                expectedAlert,
                "Expected text alert to be " + expectedAlert +
                        " but is " + actualAlert
        );

    }

    @Test
    public void typedFields()  {


        WebElement nameField = driver.findElement(inputName);
        WebElement emailField = driver.findElement(inputEmail);
        WebElement passwordField = driver.findElement(inputPassword);

        // Fill in the input fields
        nameField.sendKeys("Vitalii" + Keys.ENTER);
        emailField.sendKeys("test@gmail.com" + Keys.ENTER);
        passwordField.sendKeys("12345678" + Keys.ENTER);

        //        Click button
        click(submitBtn);

        actualNameColor = getColor(inputName);
        actualEmailColor = getColor(inputEmail);
        actualPasswordColor = getColor(inputPassword);
        actualPasswordCheckColor = getColor(inputPasswordCheck);
        //        Check for for text alert  "Type your password again"
        alert(passwordAlertBlock);
        actualPasswordAlert = getAlertText(passwordAlertBlock);

//        Test
        assertNotEquals(
                actualNameColor,
                expectedColor,
                "Expected border color of the field 'Password' not to be " + expectedColor +
                        " but it is." + actualNameColor
        );
//        Test for for not empty input
        assertNotEquals(
                expectedColor,
                actualEmailColor,
                "Expected border color of the field 'Password' not to be " + expectedColor +
                        " but it is." + actualEmailColor
        );

        assertNotEquals(
                expectedColor,
                actualPasswordColor,
                "Expected border color of the field 'Password' not to be" + expectedColor +
                        " but it is."
        );
        assertEquals(
                expectedColor,
                actualPasswordCheckColor,
                "Expected border color of the field 'Password' to be " + expectedColor +
                        " but is " + actualPasswordCheckColor
        );

//        Test for alert text "Type your password again"
        assertEquals(
                actualPasswordAlert,
                expectedAlert,
                "Expected text alert to be " + expectedAlert +
                        " but is " + actualPasswordAlert
        );

    }


    @AfterMethod
    public void finilizeBrowser() {
        driver.quit();
    }
}
