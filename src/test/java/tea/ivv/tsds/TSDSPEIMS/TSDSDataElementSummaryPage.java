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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class TSDSDataElementSummaryPage {
	private WebDriver driver;
	
	public TSDSDataElementSummaryPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public TSDSDataElementSummaryPage searchDataElement (String elementnum) throws InterruptedException {
		waitForPageRefresh(10);
		Thread.sleep(1000);
		Select elementselect = new Select(driver.findElement(By.id("dataElementList")));
		elementselect.selectByValue(elementnum);
		driver.findElement(By.id("dataElementList-add-to-target")).click();
		waitUntilSelectOptionsPopulated(new Select(driver.findElement(By.id("dataElementList-target"))));
		driver.findElement(By.id("submitButtonId")).click();
		return this;
	}
	
	public TSDSDataElementSummaryPage verifyDataElement (String elementnum, String elementname) throws InterruptedException {
		waitForPageRefresh(100);
		WebElement elementtableid = waitForElement(By.id("dataElementSummaryTableId"), 10000);
		List<WebElement> elementtablerows = elementtableid.findElements(By.tagName("tr"));
		WebElement elementrow = elementtablerows.get(1);
		List<WebElement> elementtablecols = elementrow.findElements(By.tagName("td"));
		String dataelementnum = elementtablecols.get(0).getText();
		String dataelementname = elementtablecols.get(1).getText();
		String elementcount = elementtablecols.get(6).getText();
		Assert.assertTrue(dataelementnum.equals(elementnum), dataelementnum);
		Assert.assertTrue(dataelementname.equals(elementname), dataelementname);
		Assert.assertTrue(Integer.parseInt(elementcount) > 0, elementcount);
		return this;
	}
	
	public TSDSSearchSubmissionDataPage accessSearchSubmissionData() {
		waitForPageRefresh(100);
    	Actions action = new Actions(driver);
    	WebElement AccessData = driver.findElement(By.xpath("//*[@id='nav']/li[5]/span"));
    	action.moveToElement(AccessData).build().perform();
    	driver.findElement(By.linkText("Search Submission Data")).click();
    	return new TSDSSearchSubmissionDataPage(driver);
    }
	
	public WebElement waitForElement(By by, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver,timeout);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        return element;
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
