package tea.ivv.tsds.TSDSPEIMS;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TSDSRetrieveSubmissionDataPage {
	private WebDriver driver;
	
	public TSDSRetrieveSubmissionDataPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public TSDSConfirmDataPage downloadAllCategories() {
		waitForPageRefresh(100);
		Select catselect = new Select(driver.findElement(By.id("categoriesListId")));
		catselect.selectByValue("All");
		//driver.findElement(By.id("subCategoryList-add-to-target")).click();
		waitUntilSelectOptionsPopulated(new Select(driver.findElement(By.id("subCategoryList-target"))));
		driver.findElement(By.id("jobNameId")).sendKeys("TSDS Test DataDownload");
		driver.findElement(By.id("submitButtonId")).click();
		return new TSDSConfirmDataPage(driver);
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

	private void waitUntilSelectOptionsPopulated(final Select select) {
    	WebDriverWait wait = new WebDriverWait(driver, 60);
    	wait.withTimeout(60, TimeUnit.SECONDS);
    	wait.pollingEvery(5, TimeUnit.MILLISECONDS);
    	wait.until(new ExpectedCondition<Boolean>(){
    	@Override
    	public Boolean apply(WebDriver driver) {
    		return !(select.getOptions().isEmpty());
    	}
    	});
    }
}
