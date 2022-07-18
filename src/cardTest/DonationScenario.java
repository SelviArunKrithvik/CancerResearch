package cardTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;
import objRepo.DonationPage;

public class DonationScenario {
	
	WebDriver driver;
	FileInputStream file;	
	public static Properties prop;
	DonationPage donationPage;
	protected static WebDriverWait wait;
	private static ExtentReports report; 
	private static ExtentTest logger; 
	
	public static String donationType;
	@BeforeSuite
	public void baseDriver() throws IOException {
	
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		file = new FileInputStream(System.getProperty("user.dir")+"\\src\\donarData.Properties");
        prop = new Properties();
        prop.load(file);
        donationType = prop.getProperty("donationType");
        System.out.println(donationType);
        donationPage = new DonationPage(driver);
        wait = new WebDriverWait(driver, 20);
        report = new ExtentReports(System.getProperty("user.dir") + "\\result.html");
		logger = report.startTest("Opening the Browser");
        
        
	}	
	
	@BeforeTest 
	public void openURL() {
		
		driver.get(prop.getProperty("baseURL"));
		logger.log(LogStatus.INFO, "Navigated to Donation Page");
		donationPage.acceptCookies.click();
		logger.log(LogStatus.INFO, "Clicked on the Okay for Cookies");
		
	}
	
	@Test(priority = 0)    
	public void verifyPageLoad() {
		logger = report.startTest("Starting the test for Verifying Donation URL");
		String actualTitle = driver.getTitle();
		String expectedTitle = prop.getProperty("Title");
		Assert.assertEquals(actualTitle,expectedTitle);
		logger.log(LogStatus.INFO, "Verified the page load");
		logger.log(LogStatus.PASS, "Actual and Expected Title are equal");
	}
	
	@Test(priority = 1)
	public void makeDonation() throws InterruptedException {
		logger = report.startTest("Starting the test for Making Donation");

		donationPage.DonationDetails();
		if(driver.getCurrentUrl().contains("details")) {
			logger.log(LogStatus.PASS, "Clicked on Continue button and Navigated to Details page");
		}else {
			logger.log(LogStatus.FAIL, "Page is not redirected to details page ");
		}
		
		donationPage.DonarDetails();
		if(driver.getCurrentUrl().contains("payment")) {
			logger.log(LogStatus.PASS, "Clicked on Continue button and Navigated to payment page");
		}else {
			logger.log(LogStatus.FAIL, "Page is not redirected to payment page ");
		}
		
		
		donationPage.cardDetails();
		
		if(driver.getCurrentUrl().contains("thanks")) {
		Assert.assertEquals(donationPage.getDonationAmount(), prop.getProperty("amount"));
		logger.log(LogStatus.INFO, "Verified the given amount in Checkout page");
		logger.log(LogStatus.PASS, "Actual and Expected amount are equal");
		}else {
			logger.log(LogStatus.FAIL, "Page is not redirected to Thank you page");
			
		}
		
	}
	
	
	@AfterSuite	
	public void closeDriver() {	
		report.endTest(logger);
		report.flush();
		driver.get(System.getProperty("user.dir")+"\\result.html");
	}

}
