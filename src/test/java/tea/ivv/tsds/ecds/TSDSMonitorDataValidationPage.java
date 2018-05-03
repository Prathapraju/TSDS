package tea.ivv.tsds.ecds;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import tea.ivv.tsds.TSDSPEIMS.TSDSPrepareFinalizePage;

public class TSDSMonitorDataValidationPage {
	private WebDriver driver;
	
	public TSDSMonitorDataValidationPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void waittillValidationCompletes() throws InterruptedException {
		waitForPageRefresh(10);
		WebElement validationtableElement;
		List<WebElement> validationtableRows;
		WebElement monitorRow;
		List<WebElement> validationtableColumns;
		validationtableElement = driver.findElement(By.id("dt_jobrequests"));
		validationtableRows = validationtableElement.findElements(By.tagName("tr"));
		monitorRow = validationtableRows.get(1);
		validationtableColumns = monitorRow.findElements(By.tagName("td"));
		String status = validationtableColumns.get(5).getText();
		while (status.equals("IN PROGRESS")) {
			Thread.sleep(5000);
			driver.findElement(By.id("refreshBtn")).click();
			validationtableElement = driver.findElement(By.id("dt_jobrequests"));
			validationtableRows = validationtableElement.findElements(By.tagName("tr"));
			monitorRow = validationtableRows.get(1);
			validationtableColumns = monitorRow.findElements(By.tagName("td"));
			status = validationtableColumns.get(5).getText();
		}
		Assert.assertFalse(status.endsWith("FAILED"));
	}
	
	public TSDSPrepareFinalizePage accessPrepareFinalize() throws InterruptedException {
		Thread.sleep(1000);
		driver.findElement(By.linkText("Prepare / Finalize Submission")).click();
		return new TSDSPrepareFinalizePage(driver);
	}
	
	 public void waitForPageRefresh(final long timeout) {
		 	System.out.println("Monitor Data Validations");
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
