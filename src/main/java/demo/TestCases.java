package demo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class TestCases {
    ChromeDriver driver;
    //FirefoxDriver driver;

    public TestCases() {
        System.out.println("Constructor: TestCases");
        driver = new ChromeDriver();
        //driver = new FirefoxDriver();
        driver.manage().window().maximize();
    }
    public void endTest(){
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();
    }

    public void testCase01() {
        System.out.println("Start Test case: TestCase01");
        //navigate to google form
        driver.get("https://forms.gle/wjPkzeSEk1CM7KgGA");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        try {
            //Send Name in the TextBox
            addText(driver, By.xpath("//div['Xb9hP']/input[@type='text']"), "Balaji");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
            //send Text with EpochTime in the TextBox
            addText(driver, By.xpath("//textarea[@aria-label='Your answer']"), "I want to be the best QA Engineer!"+ calculateEpochTimeToString(0));
            //Enter the experience from the Radio Buttons
            selectCheckBox(driver, By.xpath(
                    "(//span[normalize-space(text()) = 'How much experience do you have in Automation Testing?']/ancestor::div[4]//div[@class='AB7Lab Id5V1'])[2]"));
            //Enter the skills from the  CheckBoxes
            selectCheckBox(driver, By.xpath(
                    "(//*[normalize-space(text()) = 'Which of the following have you learned in Crio.Do for Automation Testing?']/ancestor::div[4]//div[@class='uHMk6b fsHoPb'])[1]"));
            selectCheckBox(driver, By.xpath(
                    "(//*[normalize-space(text()) = 'Which of the following have you learned in Crio.Do for Automation Testing?']/ancestor::div[4]//div[@class='uHMk6b fsHoPb'])[2]"));
            selectCheckBox(driver, By.xpath(
                    "(//*[normalize-space(text()) = 'Which of the following have you learned in Crio.Do for Automation Testing?']/ancestor::div[4]//div[@class='uHMk6b fsHoPb'])[4]"));
            //Select the Title Prefix from the dropdown
            selectDropdown(driver, By.xpath(
                            "//*[normalize-space(text()) = 'How should you be addressed?']/ancestor::div[4]//div[@class='MocG8c HZ3kWc mhLiyf LMgvRb KKjvXb DEh1R']"),
                    "Mr");
            //Send current date minus 7 days in the Date field
            sendDate(driver,By.xpath("//*[normalize-space(text()) = 'What was the date 7 days ago?']/ancestor::div[4]//input[@class='whsOnd zHQkBf']"));
            //Send current time
            addText(driver, By.xpath(
                            "(//*[normalize-space(text()) = 'What is the time right now?']/ancestor::div[4]//input[@class='whsOnd zHQkBf'])[1]"),
                    DateTimeToString("HH", 0));
            addText(driver, By.xpath(
                            "(//*[normalize-space(text()) = 'What is the time right now?']/ancestor::div[4]//input[@class='whsOnd zHQkBf'])[2]"),
                    DateTimeToString("mm", 0));
            //Navigate to another website
            driver.get("https://amazon.in");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
            // Perform cancel
            clickCancel(driver, false);
            //Submit the Form
            driver.findElement(By.xpath("//*[normalize-space(text())='Submit']/ancestor::div[1]")).click();
            //Print the completion message
            System.out.println(driver.findElement(By.xpath("//div[@role='heading']/../div[3]")).getText());
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Testcase Failed");
            return;
        }
        System.out.println("End Test case: testCase01");
    }




    private static void addText(ChromeDriver driver, By selector, String text) throws Exception {
        System.out.println("Add text");
        //Added explicit wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
        WebElement textBox = driver.findElement(selector);
        //remove the old text
        textBox.clear();
        textBox.sendKeys(text);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        System.out.println("Testcase Passed-Text Added");
    }

    private String calculateEpochTimeToString(int i) {
        //get the current date and time with Instant class
        Instant now = Instant.now();
        //Add the offset in ms to current time
        Instant newInstant = now.plusMillis(i);
        //convert current time to Epoch Time
        long epochMilli = newInstant.toEpochMilli();
        return String.valueOf(epochMilli);

    }

    private static void selectCheckBox(ChromeDriver driver, By selector) throws Exception {
        System.out.println("Select the check boxes/ radio buttons");
        // Initialize the webdriver wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Wait till the element is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
        // Click the button
        WebElement checkBox = driver.findElement(selector);
        checkBox.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        System.out.println("Testcase Passed-CheckBox Selected");
    }

    private static void selectDropdown(ChromeDriver driver, By dropDownSelector, String textToSelect)
            throws Exception {
        System.out.println("Select from the dropdown");
        // Find Dropdown
        WebElement dropdownElement = driver.findElement(dropDownSelector);
        // Create a Select instance
        dropdownElement.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        // Select the option by visible text
        driver.findElement(By.xpath("(//div[@data-value='" + textToSelect + "'])[2]")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        System.out.println("Testcase Passed-Dropdown Selected");

        Thread.sleep(2000);
        Actions action = new Actions(driver);
        action.sendKeys(Keys.TAB).perform();
        Thread.sleep(2000);
        action.sendKeys("17").perform();
        Thread.sleep(2000);
        action.sendKeys("May").perform();
        Thread.sleep(2000);
        action.sendKeys(Keys.TAB).perform();
        action.sendKeys("2024").perform();
        Thread.sleep(10000);
        System.out.println("Testcase Passed-Date Selected");
    }

    private static void sendDate(ChromeDriver driver, By selector) {
        //Get the today date
        LocalDate today = LocalDate.now();
        //Get the date 7 days before
        LocalDate date7DaysBefore = today.minusDays(7);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //Convert to string with the req date format
        String outputString = dateFormat.format(date7DaysBefore);
        WebElement dateField = driver.findElement(selector);
        dateField.sendKeys(outputString);
        System.out.println("Testcase Passed-Required Date sent");
    }

    private static String DateTimeToString(String formatString, long offsetInMs) {
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Add the offset in milliseconds to the current date and time
        long seconds = offsetInMs / 1000;
        long nanos = (offsetInMs % 1000) * 1000000;
        LocalDateTime newDateTime = now.minus(Duration.ofSeconds(seconds, nanos));

        // Format the new date and time according to the provided format string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatString);
        String formattedDateTime = newDateTime.format(formatter);

        // Print the formatted date and time
        return formattedDateTime;
    }


    private static void clickCancel(ChromeDriver driver, Boolean confirm) throws InterruptedException {
        System.out.println("Click Cancel");
        // Wait for the alert to be present and switch to it
        Alert alert = driver.switchTo().alert();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        if (confirm) {
            // Accept the alert (click OK)
            alert.accept();
        } else {
            // Dismiss the alert (click Cancel)
            alert.dismiss();
        }
        System.out.println("Testcase Passed-Cancel performed");
    }
}
