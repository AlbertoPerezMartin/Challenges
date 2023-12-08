package demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SantanderTestCase010FailureMessage {
	public static void main(String[] args) {
		//Still working on the bonus
		//Set up the driver, I chose Chrome
		WebDriverManager.chromedriver().setup();
		ChromeDriver chromeDriver = new ChromeDriver(); //driver
		WebDriverWait wait = new WebDriverWait(chromeDriver, null); //Explicit wait
		ExtentReports extent = new ExtentReports(); //Reports
		ExtentSparkReporter spark = new ExtentSparkReporter("src/test/resources/reports/SparksReport.html");//destination
		extent.attachReporter(spark);//Assigning reporter
		ExtentTest test = extent.createTest("Santander Test Case 010 - Suggested Execution");//Create test for the report
		
		
		
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
		input.sendKeys("0000");
		input.sendKeys(Keys.ENTER);	
		chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		
		//Step 6. Validate the failure message that appears
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[ng-repeat*=\"alert in $ctrl-alerts\"][class*=\"alert-notifier__alert alert-notifier__alert--danger\"]")));
		WebElement failureElement = chromeDriver.findElement(By.cssSelector("div[ng-repeat*=\"alert in $ctrl-alerts\"][class*=\"alert-notifier__alert alert-notifier__alert--danger\"]"));
		
		assertEquals("NO RESULTS FOUND",failureElement.getText().toUpperCase());
		
		chromeDriver.close();//Close window
		
		test.log(Status.PASS, "Santander Test Case 010 Failure Message was successful.");//Fill info for report
		extent.flush();//flush
		/*<div ng-repeat="alert in $ctrl.alerts" class="alert-notifier__alert alert-notifier__alert--danger" style="">
		<ul>
		<li>
		<!-- ngIf: alert.type === 'danger' --><span ng-if="alert.type === 'danger'" role="alert" ng-bind-html="alert.msg" class="ng-binding ng-scope">No results found</span><!-- end ngIf: alert.type === 'danger' -->
		<!-- ngIf: alert.type !== 'danger' -->
		<button class="btn btn-link" ng-click="alert.close()">
		<span aria-hidden="true">×</span>
		<span class="sr-only ng-scope" translate="COMMON.BUTTON.CLOSE">Close</span>
		</button>
		</li>
		</ul>
		</div>*/
		
	}
}
