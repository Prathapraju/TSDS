package tea.ivv.tsds.TSDSPEIMS;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TSDSMonitorDataDownloadPage {
	private WebDriver driver;
	
	public TSDSMonitorDataDownloadPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public String waittillDataDowloadCompletes() throws InterruptedException {
		waitForPageRefresh(10);
		WebElement datadownloadtableElement;
		List<WebElement> datadownloadtableRows;
		WebElement monitorRow;
		List<WebElement> datadownloadtableColumns;
		datadownloadtableElement = driver.findElement(By.id("dt_taskErrors"));
		datadownloadtableRows = datadownloadtableElement.findElements(By.tagName("tr"));
		monitorRow = datadownloadtableRows.get(1);
		datadownloadtableColumns = monitorRow.findElements(By.tagName("td"));
		String status = datadownloadtableColumns.get(5).getText();
		while (status.equals("IN PROGRESS")) {
			Thread.sleep(5000);
			driver.findElement(By.id("refreshBtn")).click();
			datadownloadtableElement = driver.findElement(By.id("dt_taskErrors"));
			datadownloadtableRows = datadownloadtableElement.findElements(By.tagName("tr"));
			monitorRow = datadownloadtableRows.get(1);
			datadownloadtableColumns = monitorRow.findElements(By.tagName("td"));
			status = datadownloadtableColumns.get(5).getText();
		}
		return status;
	}
	
	public TSDSMonitorDataDownloadPage downloadData() throws InterruptedException {
		waitForPageRefresh(10);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id='dt_taskErrors']/tbody/tr/td[7]/a")).click();
		return this;
	}
	
	public TSDSSearchStudentRosterPage accessSearchStudentRoster() {
		waitForPageRefresh(100);
    	Actions action = new Actions(driver);
    	WebElement AccessData = driver.findElement(By.xpath("//*[@id='nav']/li[5]/span"));
    	action.moveToElement(AccessData).build().perform();
		driver.findElement(By.linkText("Search Student Roster")).click();
		return new TSDSSearchStudentRosterPage(driver);
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
