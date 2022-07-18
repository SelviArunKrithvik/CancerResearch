package objRepo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.relevantcodes.extentreports.LogStatus;

import cardTest.DonationScenario;

public class DonationPage extends DonationScenario{
	
	String donationTypeValue;
	WebDriver driver;
	String data;
	public DonationPage(WebDriver driver){
		donationTypeValue = new DonationScenario().donationType;
		System.out.println("donationPage "+donationTypeValue);
		PageFactory.initElements(driver, this);
	    this.driver = driver;
		data = "//span[text()='"+donationTypeValue+"']";
	}
	
	@FindBy(id = "onetrust-accept-btn-handler")
	public WebElement acceptCookies;
	
	@FindBy(id = "otherAmount")
	public WebElement donationAmount;
	
	@FindBy(id = "typeRadioGroup0")
	public WebElement donationType2;
	
	@FindBy(name = "motivation")
	public WebElement yourMotivation;
	
	@FindBy(name = "restriction")
	public WebElement cancerType;
	
	@FindBy(id = "destinationRadioGroup1")
	public WebElement selectCancerType;
	
	@FindBy(name = "title")
	public WebElement title;
	
	@FindBy(id = "forename")
	public WebElement firstName;
	
	@FindBy(name = "surname")
	public WebElement lastName;
	
	@FindBy(id = "emailAddress")
	public WebElement email;
	
	@FindBy(id = "phoneNumber")
	public WebElement phoneNumber;
	
	@FindBy(xpath = "//button[text()='Enter address manually']")
	public WebElement addressLink;
	
	@FindBy(name = "addressLine1")
	public WebElement address1;
	
	@FindBy(name = "city")
	public WebElement city;
	
	@FindBy(name = "postalCode")
	public WebElement postCode;
	
	@FindBy(xpath = "//span[text()='Continue']")
	public WebElement continueButton;
	
	@FindBy(xpath = "//span[contains(text(),'Credit')]")
	public WebElement payType;
	
	@FindBy(id = "cardholderName")
	public WebElement cardHolderName;
	
	@FindBy(name = "credit-card-number")
	public WebElement cardNumber;
	
	@FindBy(name = "expiration")
	public WebElement expiryDate;
	
	@FindBy(name = "cvv")
	public WebElement cvv;
	
	@FindBy(id = "giftAid1")
	public WebElement giftAid;
	
	@FindBy(xpath = "//button[@type='submit']")
	public WebElement submitButton;
	
	@FindBy(xpath = "(//h2)[1]")
	public WebElement donatedAmount;
	
	public WebElement clickDonation() {
		return driver.findElement(By.xpath(data));
	}

	public void selectByText(String selectText, WebElement element) {
		Select sel = new Select(element);
		sel.selectByVisibleText(selectText);
	}
	
	public String getDonationAmount() throws InterruptedException {
		Thread.sleep(15000);
		String text = donatedAmount.getText();
		String[] temp = text.split("£");
		return temp[1];
		
	}
	
	public void clickByActions(WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().perform();
	}
	
	public void switchToFrame(String frameID) {
		driver.switchTo().frame(frameID);
	}
	
	public void switchToWindow() {
		driver.switchTo().defaultContent();
	}
	
	public void DonationDetails() throws InterruptedException {
		// Entering Donation details
				donationAmount.sendKeys(prop.getProperty("amount"));
				acceptCookies.click();
				Thread.sleep(4000);
				clickByActions(donationType2);
				selectByText(prop.getProperty("motivation"), yourMotivation);
				clickByActions(selectCancerType);
				selectByText(prop.getProperty("cancerType"), cancerType);
				continueButton.click();
				Thread.sleep(5000);
	}
	
	public void DonarDetails() throws InterruptedException {
		// Entering Donar Details
		Thread.sleep(5000);
		selectByText("Mrs", title);
		firstName.sendKeys(prop.getProperty("firstname"));
		lastName.sendKeys(prop.getProperty("lastname"));
		email.sendKeys(prop.getProperty("email"));
		phoneNumber.sendKeys(prop.getProperty("phone"));
		//logger.log(LogStatus.INFO, "Entered all the details and Clicked on Continue button");
		
		addressLink.click();
		address1.sendKeys(prop.getProperty("address1"));
		Thread.sleep(5000);
		city.sendKeys(prop.getProperty("town"));
		postCode.sendKeys(prop.getProperty("postcode"));
		//logger.log(LogStatus.INFO, "Entered the Donar details and Clicked on Continue button");
		continueButton.click();
		Thread.sleep(5000);
	}
	
	public void cardDetails() throws InterruptedException {
		// Entering Card Details
		Thread.sleep(5000);
				wait.until(ExpectedConditions.elementToBeClickable(payType));
				clickByActions(payType);
				cardHolderName.sendKeys(prop.getProperty("emailOptIn"));
				
				Thread.sleep(5000);
				switchToFrame("braintree-hosted-field-number");
				cardNumber.sendKeys(prop.getProperty("cardNumber"));
				switchToWindow();
				
				driver.switchTo().frame(driver.findElement(By.xpath("//div[@id='card-expiration-date']/iframe")));
				expiryDate.sendKeys(prop.getProperty("cardExpiry"));
				switchToWindow();
				
				switchToFrame("braintree-hosted-field-cvv");
				cvv.sendKeys(prop.getProperty("cvv"));
				switchToWindow();
				
				giftAid.click();
				// logger.log(LogStatus.INFO, "Entered all the details and Clicked on Continue button");
				submitButton.click();
				Thread.sleep(15000);
	}
}

