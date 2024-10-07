import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GuestRegistrationFormAutomation {

    WebDriver driver;
    Faker faker;

    @BeforeAll
    public void setUp(){
        driver = new ChromeDriver();
        driver.get("https://demo.wpeverest.com/user-registration/guest-registration-form/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        faker = new Faker();
    }

    @Test
    public void automateGuestReg() {

        List<WebElement> textFields = driver.findElements(By.className("input-text"));
        List<WebElement> radioBtns = driver.findElements(By.className("input-radio"));

        String fName = faker.name().firstName();
        String lName = faker.name().lastName();
        String password = Utils.generateRandomPassword();

        textFields.get(0).sendKeys(fName);  // First Name
        textFields.get(3).sendKeys(lName);  // Last Name
        textFields.get(1).sendKeys(fName+"1234@gmail.com"); // Email
        radioBtns.get(0).click(); // Male radio Button click
        textFields.get(2).sendKeys(password); // Password

        // Date Of Birth option click
        driver.findElements(By.className("ur-flatpickr-field")).get(0).click();

        //selecting month
        Select monthSelect = new Select(driver.findElement(By.xpath("(//select[@aria-label='Month'])[1]")));
        monthSelect.selectByValue("10");

        //selecting year
        WebElement year = driver.findElement(By.xpath("(//input[@aria-label='Year'])[1]"));
        year.click();
        year.clear();
        year.sendKeys("1995");

        //Selecting Date
        driver.findElements(By.className("flatpickr-day")).get(15).click();


        // Date of Birth (Using Javascript Executor)
        /*
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement dateElement = driver.findElements(By.className("ur-flatpickr-field")).get(0);
        //String date = "1999-12-08";
        js.executeScript("arguments[0].setAttribute('value','1999-12-08')",dateElement);
        */

        // Nationality
        textFields.get(5).sendKeys("Bangladeshi");
        // Phone Number
        driver.findElement(By.xpath("//input[@id='phone_1665627880']")).sendKeys(Utils.generatePhoneNumber());

        // Country
        Select countrySelect = new Select( driver.findElement(By.id("country_1665629257")) );
        countrySelect.selectByValue("BD");

        Utils.scroll(driver,0,700);//scroll down


        // Terms and Condition Checkbox
        driver.findElement(By.cssSelector("[name=privacy_policy_1665633140]")).click();


        // Submit Button
        driver.findElement(By.className("ur-submit-button")).click();

        String expectedMsg = "User successfully registered.";
        String actualMsg = driver.findElement(By.className("user-registration-message")).getText();
        Assertions.assertEquals(expectedMsg,actualMsg);
    }

    @AfterAll
    public void closeBrowser(){
        driver.quit();
    }

}
