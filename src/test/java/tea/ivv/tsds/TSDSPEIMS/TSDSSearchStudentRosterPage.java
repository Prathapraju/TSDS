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

public class TSDSSearchStudentRosterPage {
	private WebDriver driver;
	
	public TSDSSearchStudentRosterPage (WebDriver driver) {
		this.driver = driver;
	}
	
	public TSDSSearchStudentRosterPage searchByLastName (String lname) {
		waitForPageRefresh(10);
		waitForElement(By.id("LastName"), 5000).sendKeys(lname);
		driver.findElement(By.id("btnSearch")).sendKeys(Keys.ENTER);
		return this;
	}
	
	public WebElement waitForElement(By by, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver,timeout);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        return element;
     }
	
	public int countTableRows() {
		return driver.findElement(By.id("dtSearchResults")).findElements(By.tagName("tr")).size();
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
