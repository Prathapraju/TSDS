package tea.ivv.tsds.TSDSPEIMS;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TSDSConfirmDataPage {
	private WebDriver driver;
	
	public TSDSConfirmDataPage(WebDriver driver){
		this.driver = driver;
	}
	
	public TSDSMonitorPromotionDataPage submitPromotionData() throws InterruptedException {
		System.out.println("Data Promotion Submitted");
		waitForPageRefresh(100);
		Thread.sleep(2000);
		driver.findElement(By.id("submitButtonId")).sendKeys(Keys.ENTER);
		return new TSDSMonitorPromotionDataPage(driver);
	}
	
	public TSDSMonitorDataValidationPage submitValidationData() throws InterruptedException {
		System.out.println("Data Validation Submitted");
		waitForPageRefresh(100);
		Thread.sleep(1000);
		driver.findElement(By.id("submitButtonId")).sendKeys(Keys.ENTER);
		return new TSDSMonitorDataValidationPage(driver);
	}
	
	public TSDSMonitorDataDownloadPage submitDataDownload() throws InterruptedException {
		System.out.println("Data Download Submitted");
		waitForPageRefresh(100);
		Thread.sleep(1000);
		driver.findElement(By.id("submitButtonId")).sendKeys(Keys.ENTER);
		return new TSDSMonitorDataDownloadPage(driver);
	}
	
	 public WebElement waitForElement(By by, long timeout) {
	        WebDriverWait wait = new WebDriverWait(driver,timeout);
	        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	        return element;
	     }
	 
	 public void waitForPageRefresh(final long timeout) {
		    WebDriverWait wait = new WebDriverWait(driver, timeout);
		    wait.withTimeout(60, TimeUnit.SECONDS);
		    wait.pollingEvery(5, TimeUnit.MILLISECONDS);
		    wait.until(new ExpectedCondition<Boolean>(){
		    @Override
		    public Boolean apply(WebDriver driver) {
		    	try {
		            	driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		            	WebDriverWait wait = new WebDriverWait(driver,timeout);
		            	boolean present = wait.ignoring(NoSuchElementException.class).until(ExpectedConditions.not(ExpectedConditions.visibilityOf(driver.findElement(By.id("spinner")))));
		            	return present;
		    	    } catch (Exception e) {
		    	        return true;
		    	    } finally {
		    	        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		    	    }
		    	}
		    	});
		        
		    }
}
