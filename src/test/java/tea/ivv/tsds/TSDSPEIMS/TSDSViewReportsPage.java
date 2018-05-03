package tea.ivv.tsds.TSDSPEIMS;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class TSDSViewReportsPage {
    private WebDriver driver;
    
    public TSDSViewReportsPage(WebDriver driver) {
        this.driver = driver;
    }

     public TSDSViewReportsParametersPage runReport(String rtpType, String rptNum) throws InterruptedException {
        WebElement SelectElement;
        Select ReportSelect;
        TSDSViewReportsParametersPage rpt = new TSDSViewReportsParametersPage(driver);
        try {
            
            switch(rptNum) {
            case "PDM1-100-001":
            	System.out.println("Running Report");
            	SelectElement = driver.findElement(By.id("subgroupSelect-0"));
                ReportSelect = new Select(SelectElement);
                ReportSelect.selectByVisibleText("Budget");
                Thread.sleep(1000);
                System.out.println(driver.findElement(By.xpath("//*[@id='dTable-0']/tbody/tr[1]/td[5]")).getText());
                if(!driver.findElement(By.xpath("//*[@id='dTable-0']/tbody/tr[1]/td[5]")).getText().equals("IN PROGRESS")) {
                	driver.findElement(By.xpath("//*[@id='dTable-0']/tbody/tr[1]/td[6]/center/a/i")).click();
                	System.out.println("Report started");
                }
                rpt.executeReport(rtpType, rptNum);
                break;
            case "PDM2-100-001":
            	SelectElement = driver.findElement(By.id("subgroupSelect-0"));
                ReportSelect = new Select(SelectElement);
                ReportSelect.selectByVisibleText("Actual");
                Thread.sleep(1000);
                if(!driver.findElement(By.xpath("//*[@id='dTable-0']/tbody/tr[1]/td[5]")).getText().equals("IN PROGRESS")) {
                	driver.findElement(By.xpath("//*[@id='dTable-0']/tbody/tr[1]/td[6]/center/a/i")).click();
                }
                rpt.executeReport(rtpType, rptNum);
                break;
            case "PDM3-130-001":
            	SelectElement = driver.findElement(By.id("subgroupSelect-0"));
                ReportSelect = new Select(SelectElement);
                ReportSelect.selectByVisibleText("Attendance");
                Thread.sleep(1000);
                if(!driver.findElement(By.xpath("//*[@id='dTable-0']/tbody/tr[1]/td[5]")).getText().equals("IN PROGRESS")) {
                	driver.findElement(By.xpath("//*[@id='dTable-0']/tbody/tr[1]/td[6]/center/a/i")).click();
                }
                if (rtpType.equals("By LEA")) {
                	rpt.executeReport(rtpType, rptNum);
                }
                break;
                case "PDM1-120-014":
                    SelectElement = driver.findElement(By.id("subgroupSelect-0"));
                    ReportSelect = new Select(SelectElement);
                    ReportSelect.selectByVisibleText(rtpType);
                    Thread.sleep(1000);
                    if(!driver.findElement(By.xpath("//table[@id='dTable-0']/tbody/tr[14]/td[5]")).getText().equalsIgnoreCase("IN PROGRESS")) {
                        driver.findElement(By.xpath("//table[@id='dTable-0']/tbody/tr[14]/td[6]/center/a/i")).click();
                    }
                    rpt.executeReport(rtpType, rptNum);
                    break;
                case "PDM1-120-001":
                	SelectElement = driver.findElement(By.id("subgroupSelect-0"));
                    ReportSelect = new Select(SelectElement);
                    ReportSelect.selectByVisibleText("Student");
                    Thread.sleep(1000);
                    if(!driver.findElement(By.xpath("//*[@id='dTable-0']/tbody/tr[1]/td[5]")).getText().equals("IN PROGRESS")) {
                    	driver.findElement(By.xpath("//*[@id='dTable-0']/tbody/tr[1]/td[6]/center/a/i")).click();
                    }
                    rpt.executeReport(rtpType, rptNum);
                    break;
                case "PDM1-120-004":
                	SelectElement = driver.findElement(By.id("subgroupSelect-0"));
                    ReportSelect = new Select(SelectElement);
                    ReportSelect.selectByVisibleText(rtpType);
                    Thread.sleep(1000);
                    if(!driver.findElement(By.xpath("//table[@id='dTable-0']/tbody/tr[4]/td[5]")).getText().equalsIgnoreCase("IN PROGRESS")) {
                        driver.findElement(By.xpath("//table[@id='dTable-0']/tbody/tr[4]/td[6]/center/a/i")).click();
                    }
                    rpt.executeReport(rtpType, rptNum);
                    break;
                case "PDM1-120-006":
                	SelectElement = driver.findElement(By.id("subgroupSelect-0"));
                    ReportSelect = new Select(SelectElement);
                    ReportSelect.selectByVisibleText(rtpType);
                    Thread.sleep(1000);
                    if(!driver.findElement(By.xpath("//table[@id='dTable-0']/tbody/tr[6]/td[5]")).getText().equalsIgnoreCase("IN PROGRESS")) {
                        driver.findElement(By.xpath("//table[@id='dTable-0']/tbody/tr[6]/td[6]/center/a/i")).click();
                    }
                    rpt.executeReport(rtpType, rptNum);
                    break;
                case "PDM1-120-013":     //Early Childhood Pre-Kindergarten Data Submission    
                	SelectElement = driver.findElement(By.id("subgroupSelect-0"));
                    ReportSelect = new Select(SelectElement);
                    ReportSelect.selectByVisibleText(rtpType);
                    Thread.sleep(1000);
                    if(!driver.findElement(By.xpath("//table[@id='dTable-0']/tbody/tr[13]/td[5]")).getText().equalsIgnoreCase("IN PROGRESS")) {
                        driver.findElement(By.xpath("//table[@id='dTable-0']/tbody/tr[13]/td[6]/center/a/i")).click();
                    }
                    rpt.executeReport(rtpType, rptNum);
                    break;
                case "PDM1-120-015":
                	SelectElement = driver.findElement(By.id("subgroupSelect-0"));
                    ReportSelect = new Select(SelectElement);
                    ReportSelect.selectByVisibleText(rtpType);
                    Thread.sleep(1000);
                    if(!driver.findElement(By.xpath("//table[@id='dTable-0']/tbody/tr[15]/td[5]")).getText().equalsIgnoreCase("IN PROGRESS")) {
                        driver.findElement(By.xpath("//table[@id='dTable-0']/tbody/tr[15]/td[6]/center/a/i")).click();
                    }
                    rpt.executeReport(rtpType, rptNum);
                    break;
                case "PDM1-220-001":     //Early Childhood PRE-K Completion
                    SelectElement = driver.findElement(By.id("subgroupSelect-1"));
                    ReportSelect = new Select(SelectElement);
                    ReportSelect.selectByVisibleText(rtpType);
                    Thread.sleep(1000);
                    if(!driver.findElement(By.xpath("//table[@id='dTable-1']/tbody/tr/td[5]")).getText().equalsIgnoreCase("IN PROGRESS")) {
                        driver.findElement(By.xpath("//table[@id='dTable-1']/tbody/tr/td[6]/center/a/i")).click();
                    }
                    rpt.executeReport(rtpType, rptNum);
                    break;
                case "PDM1-231-001":     //Early Childhood Incomplete Assessment Data
                    SelectElement = driver.findElement(By.id("subgroupSelect-1"));
                    ReportSelect = new Select(SelectElement);
                    ReportSelect.selectByIndex(1);
                    Thread.sleep(1000);
                    if(!driver.findElement(By.xpath("//table[@id='dTable-1']/tbody/tr/td[5]")).getText().equalsIgnoreCase("IN PROGRESS")) {
                        driver.findElement(By.xpath("//table[@id='dTable-1']/tbody/tr/td[6]/center/a/i")).click();
                    }
                    rpt.executeReport(rtpType, rptNum);
                    break;
                case "PDM1-230-002":
                    SelectElement = driver.findElement(By.id("subgroupSelect-1"));
                    ReportSelect = new Select(SelectElement);
                    ReportSelect.selectByVisibleText(rtpType);
                    Thread.sleep(1000);
                    if(!driver.findElement(By.xpath("//table[@id='dTable-1']/tbody/tr/td[5]")).getText().equalsIgnoreCase("IN PROGRESS")) {
                        driver.findElement(By.xpath("//table[@id='dTable-1']/tbody/tr/td[6]/center/a/i")).click();
                    }
                    break;
            }
            (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return d.getTitle().contentEquals("View Reports");
            }
        });
           
        }
        catch(NoSuchElementException e) {
            //Do nothing
        }
        return new TSDSViewReportsParametersPage(driver);
    }
     
     public TSDSSearchSubmissionDataPage accessSearchSubmissionData() {
 		waitForPageRefresh(100);
     	Actions action = new Actions(driver);
     	WebElement AccessData = driver.findElement(By.xpath("//*[@id='nav']/li[5]/span"));
     	action.moveToElement(AccessData).build().perform();
     	driver.findElement(By.linkText("Search Submission Data")).click();
     	return new TSDSSearchSubmissionDataPage(driver);
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
     
    public TSDSViewReportsPage downloadReport(String rptNum) throws InterruptedException, IOException  {
        System.out.println("RptType: " + rptNum);
        String status;
        try {    
            switch(rptNum) {
            case "PDM1-100-001":
            case "PDM2-100-001":
            case "PDM1-120-001":
            case "PDM3-130-001":
            	Thread.sleep(1000);
            	status = driver.findElement(By.xpath("//*[@id='dTable-0']/tbody/tr[1]/td[5]")).getText();
                while(status.equals("IN PROGRESS")){
                    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
                    driver.findElement(By.id("refreshBtn")).click();
                    Thread.sleep(20000);
                    status = driver.findElement(By.xpath("//*[@id='dTable-0']/tbody/tr[1]/td[5]")).getText();
                }
                Assert.assertFalse(status.equals("FAILED"));
                WebElement element = driver.findElement(By.xpath("//*[@id='dTable-0']/tbody/tr[1]/td[5]"));
                Actions actions = new Actions(driver);
                actions.moveToElement(element);
                actions.click();
                actions.build().perform();
                break;
                case "PDM1-120-014": //Early Childhood Assessment Completion  
                    Thread.sleep(1000);
                    while(driver.findElement(By.xpath("//table[@id='dTable-0']/tbody/tr[14]/td[5]")).getText().equalsIgnoreCase("IN PROGRESS")){
                        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
                        driver.findElement(By.id("refreshBtn")).click();
                        Thread.sleep(20000);
                    } 
                    driver.findElement(By.xpath("//table[@id='dTable-0']/tbody/tr[14]/td[5]/a")).sendKeys(Keys.ENTER);
                    break;
                case "PDM1-120-004":
                    Thread.sleep(1000);
                    while(driver.findElement(By.xpath("//table[@id='dTable-0']/tbody/tr[4]/td[5]")).getText().equalsIgnoreCase("IN PROGRESS")){
                        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
                        driver.findElement(By.id("refreshBtn")).click();
                        Thread.sleep(20000);
                    } 
                    driver.findElement(By.xpath("//table[@id='dTable-0']/tbody/tr[4]/td[5]/a")).sendKeys(Keys.ENTER);
                    break;
                case "PDM1-120-006":    
                    Thread.sleep(1000);
                    while(driver.findElement(By.xpath("//table[@id='dTable-0']/tbody/tr[6]/td[5]")).getText().equalsIgnoreCase("IN PROGRESS")){
                        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
                        driver.findElement(By.id("refreshBtn")).click();
                        Thread.sleep(20000);
                    } 
                    driver.findElement(By.xpath("//table[@id='dTable-0']/tbody/tr[6]/td[5]/a")).sendKeys(Keys.ENTER);
                    break;
                case "PDM1-120-013": 
                    Thread.sleep(1000);
                    while(driver.findElement(By.xpath("//table[@id='dTable-0']/tbody/tr[13]/td[5]")).getText().equalsIgnoreCase("IN PROGRESS")){
                        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
                        driver.findElement(By.id("refreshBtn")).click();
                        Thread.sleep(20000);
                    } 
                    driver.findElement(By.xpath("//table[@id='dTable-0']/tbody/tr[13]/td[5]/a")).sendKeys(Keys.ENTER);
                    break;
                case "PDM1-120-015":    
                    Thread.sleep(1000);
                    while(driver.findElement(By.xpath("//table[@id='dTable-0']/tbody/tr[15]/td[5]")).getText().equalsIgnoreCase("IN PROGRESS")){
                        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
                        driver.findElement(By.id("refreshBtn")).click();
                        Thread.sleep(20000);
                    } 
                    driver.findElement(By.xpath("//table[@id='dTable-0']/tbody/tr[15]/td[5]/a")).sendKeys(Keys.ENTER);
                    break;
                  
                case "PDM1-220-001":
                    Thread.sleep(1000);
                    while(driver.findElement(By.xpath("//table[@id='dTable-1']/tbody/tr/td[5]")).getText().equalsIgnoreCase("IN PROGRESS")){
                        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
                        driver.findElement(By.id("refreshBtn")).click();
                        Thread.sleep(20000);
                    } 
                    driver.findElement(By.xpath("//table[@id='dTable-1']/tbody/tr/td[5]/a")).sendKeys(Keys.ENTER);
                    break;
                    
                case "PDM1-231-001":
                    Thread.sleep(1000);
                    while(driver.findElement(By.xpath("//table[@id='dTable-1']/tbody/tr/td[5]")).getText().equalsIgnoreCase("IN PROGRESS")){
                        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
                        driver.findElement(By.id("refreshBtn")).click();
                        Thread.sleep(20000);
                    } 
                    driver.findElement(By.xpath("//table[@id='dTable-1']/tbody/tr/td[5]/a")).sendKeys(Keys.ENTER);
                    break;
                
                case "PDM1-230-002":
                    Thread.sleep(1000);
                    while(driver.findElement(By.xpath("//table[@id='dTable-1']/tbody/tr/td[5]")).getText().equalsIgnoreCase("IN PROGRESS")){
                        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
                        driver.findElement(By.id("refreshBtn")).click();
                        Thread.sleep(20000);
                    } 
                    driver.findElement(By.xpath("//table[@id='dTable-1']/tbody/tr/td[5]/a")).sendKeys(Keys.ENTER);
                    break;
            }
        }
        catch(NoSuchElementException e) {}
        return new TSDSViewReportsPage(driver);
    }
    
    public TSDSDataElementSummaryPage accessDataElementSummary() {
    	Actions action = new Actions(driver);
    	WebElement AccessData = driver.findElement(By.xpath("//*[@id='nav']/li[5]/span"));
    	action.moveToElement(AccessData).build().perform();
    	driver.findElement(By.linkText("Data Element Summary")).click();
    	return new TSDSDataElementSummaryPage(driver);
    }

    public static boolean isLinkExist(String id, WebDriver driver) {
        boolean exists = false;
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            driver.findElement(By.linkText(id));
            exists = true;
                 
        }
        catch (NoSuchElementException e) {}
        return exists;
    } 
     
     public boolean isElementPresent(By by) {
       try {
          driver.findElement(by);
          return true;
     } catch (NoSuchElementException e) {
          return false;
     }
   }
     
    public WebElement waitForElement(By by, long timeout) {
      WebDriverWait wait = new WebDriverWait(driver,timeout);
      WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
      return element;
   }
} 