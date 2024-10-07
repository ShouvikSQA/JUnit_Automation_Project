import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WebformAutomation {
    WebDriver driver;
    Faker faker;
    @BeforeAll
    public void setUp(){
        driver = new ChromeDriver();
        driver.get("https://www.digitalunite.com/practice-webform-learners");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        faker = new Faker();
    }

    @Test
    @DisplayName("Check if the WebForm is successfully submitted or not")
    public void automateWebForm() {

        // Accept all the cookies
        driver.findElement(By.xpath("//button[text()='Accept All Cookies']")).click();

        List<WebElement> formControls = driver.findElements(By.className("form-control"));

        // name textBox
         formControls.get(0).sendKeys(faker.name().fullName());

        // number textbox
         formControls.get(1).sendKeys( Utils.generatePhoneNumber()  );

        // Select date

        String today = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
        formControls.get(2).sendKeys(today);

        // email textBox
        formControls.get(3).sendKeys(faker.internet().emailAddress());

        // ScrollDown
        Utils.scroll(driver , 0 , 300);

        // Introduction Textbox
         formControls.get(4).sendKeys("Hello , I am Shouvik !");

        // Upload a File
        WebElement btnFileUpload = driver.findElement(By.xpath("//input[@id='edit-uploadocument-upload']"));
        btnFileUpload.sendKeys(System.getProperty("user.dir")+ "./src/test/resources/Shouvik.jpg");


        // ScrollDown
        Utils.scroll(driver , 0 , 700);

       // Thread.sleep(3000); // Time for  file upload completion

        // We are using explicit wait here, instead of Thread.sleep()
        // Cz Its a Dynamic wait, which is preferable to use
        // Check if the remote button is visible or not
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Remove']")));

        //Check the checkBox
        WebElement checkBox = driver.findElement(By.xpath("//input[@id='edit-age']"));
        checkBox.click();
        

        // Click on submit button
        WebElement btnSubmit = driver.findElement(By.xpath("//input[@id='edit-submit']"));

        btnSubmit.click();

        // Assertion of successful submission message
        String text = driver.findElement(By.xpath("//div[@id='block-pagetitle-2']")).getText();
        Assertions.assertTrue(text.contains("Thank you for your submission!"));

    }

    @AfterAll
    public void closeDriver(){
        driver.quit();
    }

}
