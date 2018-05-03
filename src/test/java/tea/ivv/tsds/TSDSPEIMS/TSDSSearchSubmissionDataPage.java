package tea.ivv.tsds.TSDSPEIMS;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TSDSSearchSubmissionDataPage {
	private WebDriver driver;
	
	public TSDSSearchSubmissionDataPage (WebDriver driver) {
		this.driver = driver;
	}
	
	public TSDSSearchSubmissionDataPage searchSubmissionbyFirstName (String category, String subcat, String name) {
		waitForPageRefresh(10);
		Select catselect = new Select(driver.findElement(By.id("categoriesListId")));
		catselect.selectByVisibleText(category);
		Select subcatselect = new Select(waitForElement(By.id("subCategoryListId"), 2000));
		subcatselect.selectByVisibleText(subcat);
		waitForElement(By.id("FirstName"), 2000).sendKeys(name);
		return this;
	}
	
	public int verifyDataSearch() {
		return waitForElement(By.id("dtSearchResults"), 10000).findElements(By.tagName("tr")).size();
	}
	
	public WebElement waitForElement(By by, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver,timeout);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        return element;
     }
	
	public TSDSSearchStudentRosterPage accessSearchStudentRoster() {
		waitForPageRefresh(100);
    	Actions action = new Actions(driver);
    	WebElement AccessData = driver.findElement(By.xpath("//*[@id='nav']/li[5]/span"));
    	action.moveToElement(AccessData).build().perform();
		driver.findElement(By.linkText("Search Student Roster")).click();
		return new TSDSSearchStudentRosterPage(driver);
	}
	
	public TSDSRetrieveSubmissionDataPage accessRetrieveSubmissionData() {
		waitForPageRefresh(10);
		Actions action = new Actions(driver);
    	WebElement AccessData = driver.findElement(By.xpath("//*[@id='nav']/li[5]/span"));
    	action.moveToElement(AccessData).build().perform();
    	driver.findElement(By.linkText("Retrieve Submission Data")).click();
		return new TSDSRetrieveSubmissionDataPage(driver);
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
