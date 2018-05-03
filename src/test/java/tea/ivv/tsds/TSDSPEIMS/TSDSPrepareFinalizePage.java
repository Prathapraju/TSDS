package tea.ivv.tsds.TSDSPEIMS;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

//import tea.ivv.tsds.ecds.Logger;
//import tea.ivv.tsds.ecds.StaleElementReferenceException;
import us.tx.state.tea.seleniumfw.utils.*;


public class TSDSPrepareFinalizePage {
    private WebDriver driver;
    private boolean isPrepare;
    private boolean isComplete;
    private boolean isReset;
//    protected Logger log = Logger.getLogger(this.getClass().getName());
    public TSDSPrepareFinalizePage(WebDriver driver) {
        this.driver = driver;
    }
    public void setPrepare(boolean isPrepare) {
        this.isPrepare = isPrepare;
    }
    public boolean getPrepare() {
        return isPrepare;
    }
    public void setComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }
    public boolean getComplete() {
        return isComplete;
    }
    public void setReset(boolean isReset) {
        this.isReset = isReset;
    }
    public boolean getReset() {
        return isReset;
    }
    public WebDriver setUpParams(String acadYr, String collectionId) throws Exception{
        
        new Select(driver.findElement(By.id("hdrAcadYearLst"))).selectByVisibleText(acadYr);
        new Select(driver.findElement(By.id("hdrCollectionsLst"))).selectByVisibleText(collectionId);
        if(ElementUtils.isElementPresent(driver, By.id("goButton"))) {
            if(ElementUtils.clickAndRetry(driver, By.id("goButton"))) {
            
                (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
                @Override
                    public Boolean apply(WebDriver d) {
                        for(String winHandle : driver.getWindowHandles()) {
                            driver.switchTo().window(winHandle);
                            if(driver.getTitle().equals("Core Collection Home")) {
                                break;
                            }
                        }
                        return driver.getTitle().equals("Core Collection Home");   
                    }
                });
                 
            }        
        }
        return driver;
    }
    
    public TSDSValidatePromotedDataPage launchValidateECDSData() {
		waitForPageRefresh(10);
		Actions action = new Actions(driver);
		WebElement validate = driver.findElement(By.xpath("//*[@id='nav']/li[2]/span"));
		action.moveToElement(validate).build().perform();
		driver.findElement(By.linkText("Validate Submission Data")).click();
		return new TSDSValidatePromotedDataPage(driver);
	}
    
    public WebDriver runPrepare() throws Exception {
        long timeoutInSeconds = 840;
        if(driver.findElement(By.id("prepareButtonId")).isEnabled() && ElementUtils.isElementPresent(driver, By.id("prepareButtonId"))) {
            driver.findElement(By.id("prepareButtonId")).click();
//            log.info("Processing...");
            try {

                new WebDriverWait(driver,timeoutInSeconds).until(new ExpectedCondition<Boolean>() {
                @Override
             
                    public Boolean apply(WebDriver d) {
                        if (! ElementUtils.isElementPresent(driver, By.id("progressbartext"))) {
                            isPrepare = true;
                            return true;
                            
                        }
                        else {
                            return false;
                        }
                    }
                });    
            }
            catch (StaleElementReferenceException e) {
//                 log.info("Progress bar is no longer exists");
            }              
        }
        else {
 //           log.info("Prepare button is disabled.");
        }
        return driver;
    }
    
    public WebDriver selectLEA(String leaid) {
    	WebElement leaSelectElement = driver.findElement(By.id("hdrescLeaList"));
    	Select leaSelect = new Select(leaSelectElement);
    	leaSelect.selectByValue(leaid);
    	driver.findElement(By.id("goButton")).sendKeys(Keys.ENTER);;
    	return driver;
    }
    
    public boolean getcompleteButtonstatus() {
    	return driver.findElement(By.id("completeButtonId")).isEnabled();
    }
    public String runComplete() throws Exception {
    	String confirmmessage;
        long timeoutInSeconds = 180;
        driver.findElement(By.id("completeButtonId")).click();
        new WebDriverWait(driver, timeoutInSeconds).until(ExpectedConditions.invisibilityOfElementLocated(By.id("progressbartext")));
        confirmmessage = driver.findElement(By.id("confirmAckMsg")).getText().trim();
        driver.findElement(By.id("cbconfirmId")).click();   //Check radio button to confirm
        driver.findElement(By.id("confirmCompleteBtn")).click(); //Click Confirm button
        new WebDriverWait(driver, timeoutInSeconds).until(ExpectedConditions.visibilityOfElementLocated(By.id("inComment")));
        driver.findElement(By.id("inComment")).sendKeys("Test Complete Comments");
        Thread.sleep(1000);
        driver.findElement(By.id("save")).click();
        new WebDriverWait(driver, timeoutInSeconds).until(ExpectedConditions.invisibilityOfElementLocated(By.id("inComment")));
        return confirmmessage;
    }
    
    public WebDriver runReset() {
        long timeoutInSeconds = 30;
//        log.info("Launch Reset...");
        if(driver.findElement(By.id("resetButtonId")).isEnabled() && ElementUtils.isElementPresent(driver, By.id("resetButtonId"))) {
            driver.findElement(By.cssSelector("#resetButtonId")).click();
            new WebDriverWait(driver, timeoutInSeconds).until(ExpectedConditions.visibilityOfElementLocated(By.id("prepareButtonId")));
        }    
        else {
//            log.info("Reset button is disabled");
        }
        return driver;
    }
    
    public WebDriver selectViewReports() throws InterruptedException {
        try {
        	Thread.sleep(2000);
            driver.findElement(By.id("viewReports")).click();
            (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                for(String winHandle : driver.getWindowHandles()) {
                    driver.switchTo().window(winHandle);
                    if(driver.getTitle().equals("View Reports")) {
                        break;
                    }
                }
                return driver.getTitle().equals("View Reports");   
            }
            });
        
        }
        catch(NoSuchElementException e) {
            //nothing to do
        }
        return driver;
    }
    
    public String findPrepareStatus() {
    	String status = "VALIDATED";
    	String jobstatus;
    	WebElement tableelement = waitForElement(By.id("dt_preparefinalize"), 20);
    	List<WebElement> table_rows = tableelement.findElements(By.tagName("tr"));
    	List<WebElement> rowcolumns;
    	for (int i = 1; i < table_rows.size(); i++) {
    		rowcolumns = table_rows.get(i).findElements(By.tagName("td"));
    		jobstatus = rowcolumns.get(13).getText();
    		if (! jobstatus.equals("VALIDATED")) {
    			System.out.println(jobstatus);
    			status = "PROMOTED";
    			break;
    		}
    	}
    	return status;
    }
    
    public String acceptData() {
    	long timeoutInSeconds = 180;
    	waitForElement(By.id("acceptButtonId"), 20).sendKeys(Keys.ENTER);
    	String message = waitForElement(By.className("message"), 20).getText();
    	driver.findElement(By.cssSelector("#confirm > div.buttons > div.yes.btn-primary")).click();
    	waitForElement(By.id("inComment"), 20).sendKeys("Test Acceptance Comments");
    	driver.findElement(By.id("save")).click();
    	new WebDriverWait(driver, timeoutInSeconds).until(ExpectedConditions.invisibilityOfElementLocated(By.id("inComment")));
    	return message;
    }
    
    public String getCollectionStatus() {
    	return driver.findElement(By.xpath("//*[@id='prepareFinalizeFormID']/table/tbody/tr[1]/td[1]/a")).getText();
    }
    
    public boolean isRejectButtonEnabled() {
    	return driver.findElement(By.id("rejectButtonId")).isEnabled();
    }
    
    
    public boolean isAcceptButtonEnabled() {
    	return driver.findElement(By.id("acceptButtonId")).isEnabled();
    }
    
    
    public boolean isCompleteButtonEnabled() {
    	return driver.findElement(By.id("completeButtonId")).isEnabled();
    }
    
    public int findECDSPrepareTableCount() {
    	return waitForElement(By.id("dtTaskSummary"), 20).findElements(By.tagName("tr")).size();
    }
    
    public String findCampusPrepareStatus() {
    	String status = "VALIDATED";
    	String jobstatus;
    	WebElement tableelement = waitForElement(By.id("dt_campuspreparefinalize"), 20);
    	List<WebElement> table_rows = tableelement.findElements(By.tagName("tr"));
    	List<WebElement> rowcolumns;
    	for (int i = 1; i < table_rows.size(); i++) {
    		rowcolumns = table_rows.get(i).findElements(By.tagName("td"));
    		jobstatus = rowcolumns.get(13).getText();
    		if (! jobstatus.equals("VALIDATED")) {
    			System.out.println(jobstatus);
    			status = "PROMOTED";
    			break;
    		}
    	}
    	return status;
    }
    
    public WebElement waitForElement(By by, long timeout) {
      WebDriverWait wait = new WebDriverWait(driver,timeout);
      WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
      return element;
   }
    
    
    
    public String verifyStatus() {
    	return driver.findElement(By.xpath("//*[@id='prepareFinalizeFormID']/table/tbody/tr[1]/td[1]/a")).getText();
    }
    
    public String verifyCampusStatus() {
    	return driver.findElement(By.xpath("//*[@id='campusprepareFinalizeFormID']/table/tbody/tr[1]/td/a")).getText();
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
