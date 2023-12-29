package demo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SantanderTestCase015Suggestion {
	public static void main(String[] args) {
		//Set up the driver, I chose Chrome
		WebDriverManager.chromedriver().setup();
		ChromeDriver chromeDriver = new ChromeDriver(); //driver
		String winHandleBefore; //To save original page
		ExtentReports extent = new ExtentReports(); //Reports
		ExtentSparkReporter spark = new ExtentSparkReporter("src/test/resources/reports/SparksReport.html");//destination
		extent.attachReporter(spark);//Assigning reporter
		ExtentTest test = extent.createTest("Santander Test Case 015 - Suggested Execution");//Create test for the report
		
		try {
			//Step 1. Connect to main page
			chromeDriver.get("https://www.santanderbank.com/");
		
			//Step 2. Click the "Get Started" button on "Checking accounts"
			chromeDriver.findElement(By.id("better-btn-02")).click();
			chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
					
			//Step 3. Ensure the 3 boxes exist and are enabled
			assertTrue(chromeDriver.findElement(By.cssSelector("a[class*=\"btn-primary btn btn-round trk-btn gate-trk\"][href*=\"select-checking\"]")).isEnabled()); //Check if SantanderSelectChecking exists/enabled
			assertTrue(chromeDriver.findElement(By.cssSelector("a[class*=\"btn-primary btn btn-round trk-btn gate-trk\"][href*=\"simply-right-checking\"]")).isEnabled()); //Check if SantanderSimplyRightChecking exists/enabled
			assertTrue(chromeDriver.findElement(By.cssSelector("a[class*=\"btn-primary btn btn-round trk-btn gate-trk\"][href*=\"essential-checking\"]")).isEnabled()); //Check if SantanderEssentialChecking exists/enabled, it should be student value, not sure if that's okay
			chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
			//Step 4. Click the "Open account" link for "Simply Right Checking" box
			winHandleBefore = chromeDriver.getWindowHandle();
			chromeDriver.findElement(By.cssSelector("a[class*=\"openlink_txt\"][href*=\"https://secureopen.santanderbank.com/apps/servlet/SmartForm.html?formCode=sbnadao&product=SimplyRightChecking\"][target*=\"_blank\"]")).click();
			chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			
			//Step 5. Validate the url is correct
			for(String winHandle : chromeDriver.getWindowHandles()){ //Switch to new window due to access denied
			    chromeDriver.switchTo().window(winHandle);
			}
				
			assertTrue(chromeDriver.getCurrentUrl().contains("https://secureopen.santanderbank.com/apps/servlet/SmartForm.html?formCode=sbnadao&product=SimplyRightChecking")); //check if page contains the requested url
			
			chromeDriver.close();//Close new window
			chromeDriver.switchTo().window(winHandleBefore);//Switch to previous window
			
			test.log(Status.PASS, "Santander Test Case 015 was successful.");//Fill info for report
			extent.flush();//flush
		}
		catch(Exception e) {
			test.log(Status.FAIL, "Santander Test Case 015 failed.");
			extent.flush();
			throw e;
		}
		finally {
			if(chromeDriver!=null) {
				chromeDriver.quit();
			}		}
		
		
		
		
	}
}
