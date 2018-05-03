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

public class TSDSMonitorPromotionDataPage {
	private WebDriver driver;
	
	public TSDSMonitorPromotionDataPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public String getPromotionStatus() throws InterruptedException {
		System.out.println("Entering Monitor Data Promotions");
		waitForPageRefresh(10);
		WebElement promotiontableElement;
		List<WebElement> promotiontableRows;
		WebElement monitorRow;
		List<WebElement> promotiontableColumns;
		promotiontableElement = driver.findElement(By.id("dt_taskErrors"));
		promotiontableRows = promotiontableElement.findElements(By.tagName("tr"));
		monitorRow = promotiontableRows.get(1);
		promotiontableColumns = monitorRow.findElements(By.tagName("td"));
		String status = promotiontableColumns.get(5).getText();
		while (status.equals("IN PROGRESS")) {
			Thread.sleep(5000);
			driver.findElement(By.id("refreshBtn")).click();
			promotiontableElement = driver.findElement(By.id("dt_taskErrors"));
			promotiontableRows = promotiontableElement.findElements(By.tagName("tr"));
			monitorRow = promotiontableRows.get(1);
			promotiontableColumns = monitorRow.findElements(By.tagName("td"));
			status = promotiontableColumns.get(5).getText();
		}
		return status;
	}
	
	public TSDSValidatePromotedDataPage launchValidateData() {
		waitForPageRefresh(10);
		Actions action = new Actions(driver);
		WebElement validate = driver.findElement(By.xpath("//*[@id='nav']/li[3]/span"));
		action.moveToElement(validate).build().perform();
		driver.findElement(By.linkText("Validate Submission Data")).click();
		return new TSDSValidatePromotedDataPage(driver);
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
