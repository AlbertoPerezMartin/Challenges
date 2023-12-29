package demo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SantanderTestCase010Suggestion {
	public static void main(String[] args) {
		//Set up the driver, I chose Chrome
		WebDriverManager.chromedriver().setup();
		ChromeDriver chromeDriver = new ChromeDriver(); //driver
		ExtentReports extent = new ExtentReports(); //Reports
		ExtentSparkReporter spark = new ExtentSparkReporter("src/test/resources/reports/SparksReport.html");//destination
		extent.attachReporter(spark);//Assigning reporter
		ExtentTest test = extent.createTest("Santander Test Case 010 - Suggested Execution");//Create test for the report
		try {
			//Step 1. Connect to main page
			chromeDriver.get("https://www.santanderbank.com/");
		
			//Step 2. Click the "Find a branch" button on "Find us"
			chromeDriver.findElement(By.cssSelector("a[href*=\"https://booking.santanderbank.com/branch\"")).click();
			chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
					
			//Step 3. Ensure the 4 boxes exist and are enabled
			assertTrue(chromeDriver.findElement(By.cssSelector("button[class*=\"btn btn-primary ng-scope\"][type*=\"button\"][aria-label*=\"Select Everyday Banking\"]")).isEnabled()); //Check if Everyday Banking exists/enabled
			assertTrue(chromeDriver.findElement(By.cssSelector("button[class*=\"btn btn-primary ng-scope\"][type*=\"button\"][aria-label*=\"Select Borrowing\"]")).isEnabled()); //Check if Borrowing exists/enabled
			assertTrue(chromeDriver.findElement(By.cssSelector("button[class*=\"btn btn-primary ng-scope\"][type*=\"button\"][aria-label*=\"Select Investments\"]")).isEnabled()); //Check if Investments exists/enabled
			assertTrue(chromeDriver.findElement(By.cssSelector("button[class*=\"btn btn-primary ng-scope\"][type*=\"button\"][aria-label*=\"Select Business Support\"]")).isEnabled()); //Check if Select Business Support exists/enabled
			chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
			//Step 4. Click the "Select" button for "Everyday Banking"
			chromeDriver.findElement(By.cssSelector("button[class*=\"btn btn-primary ng-scope\"][type*=\"button\"][aria-label*=\"Select Everyday Banking")).click();
			chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			
			//Step 5. Enter Zip Code "07733" on the search box and press enter
			WebElement input = chromeDriver.findElement(By.id("search-location-input"));
			input.sendKeys("07733");
			input.sendKeys(Keys.ENTER);
			
			//Step 6. Validate there are 4 branch boxes and one of the belongs to "Hazlet"
			List<String> townsList = new ArrayList<String>(); //List to save all texts from the boxes and check if they exist
			
			WebElement box1 = chromeDriver.findElement(By.id("card__title--37054"));
			WebElement box2 = chromeDriver.findElement(By.id("card__title--37272"));
			WebElement box3 = chromeDriver.findElement(By.id("card__title--37052"));
			WebElement box4 = chromeDriver.findElement(By.id("card__title--37112"));
			
			townsList.add(box1.getText().toUpperCase());
			townsList.add(box2.getText().toUpperCase());
			townsList.add(box3.getText().toUpperCase());
			townsList.add(box4.getText().toUpperCase());
			
			assertTrue(box1.isEnabled()); //Check if first box exists/enabled
			assertTrue(box2.isEnabled()); //Check if second box exists/enabled
			assertTrue(box3.isEnabled()); //Check if third box exists/enabled
			assertTrue(box4.isEnabled()); //Check if fourth box exists/enabled
			
			assertTrue(townsList.contains("HAZLET"));
			
			test.log(Status.PASS, "Santander Test Case 010 was successful.");//Fill info for report
			extent.flush();//flush
		}
		catch (Exception e) {
			test.log(Status.FAIL, "Santander Test Case 010 failed.");
			extent.flush();
			throw e;
		}
		finally {
			if(chromeDriver!=null) {
				chromeDriver.quit();
			}
			
		}
		
		
		
	}
}
