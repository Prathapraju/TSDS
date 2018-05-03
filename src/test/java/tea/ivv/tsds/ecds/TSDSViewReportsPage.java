/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tea.ivv.tsds.ecds;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import us.tx.state.tea.seleniumfw.utils.*;

/**
 *
 * @author jtran
 */
public class TSDSViewReportsPage {
    private static WebDriver driver;
    public TSDSViewReportsPage(WebDriver driver) {
        //this.campusList = new ArrayList();
        this.driver = driver;
    }
    public TSDSViewReportsParametersPage runReport(String rptType, String rptNum) throws InterruptedException {
        TSDSViewReportsParametersPage rpt = new TSDSViewReportsParametersPage(driver);
        try {
			switch(rptNum) {
                case "ECD0-000-001": //Early Childhood Assessment Completion
                	if(!ElementUtils.waitForElement(driver, By.xpath("//*[@id='reportDataTable']/tbody/tr[1]/td[5]"), 20).getText().equals("INPROGRESS")) {
                    	driver.findElement(By.xpath("//*[@id='reportDataTable']/tbody/tr[1]/td[6]/center/a/i")).click();
                    }
                    rpt.executeReport(rptType, rptNum);
                    break;
                case "ECD1_102_001": //Early Childhood Assessemnt PRE-K Sources   
                    
                    if(!driver.findElement(By.xpath("//*[@id='reportDataTable']/tbody/tr[1]/td[5]/center")).getText().equals("INPROGRESS")) {
                        driver.findElement(By.xpath("//table[@id='reportDataTable']/tbody/tr[1]/td[6]/center/a/i")).click();
                    }  
                    //addParams(orgType, campusIds, ecdsStatus, row);
                    break;
               
                    
                case "ECD1_101_001": //Early Childhood Assessment Summary
                case "ECD1_105_001":     //Early Childhood Pre-Kindergarten Data Submission    
                    
                    if(!driver.findElement(By.xpath("//*[@id='reportDataTable']/tbody/tr[2]/td[5]/center")).getText().equals("INPROGRESS")) {
                        driver.findElement(By.xpath("//table[@id='reportDataTable']/tbody/tr[2]/td[6]/center/a/i")).click();
                    }  
                    //addParams(orgType, campusIds, ecdsStatus,row);
                    break;
              
                case "ECD1_103_001":     //Early Childhood Kindergarten Data Submission
                case "ECD1_106_001":     //Early Childhood PRE-K Completion
                    
                    if(!driver.findElement(By.xpath("//*[@id='reportDataTable']/tbody/tr[3]/td[5]/center")).getText().equals("INPROGRESS")) {
                        driver.findElement(By.xpath("//table[@id='reportDataTable']/tbody/tr[3]/td[6]/center/a/i")).click();
                    }  
                    //addParams(orgType,campusIds,sSN,row );
                    
                    break;
                  
                case "ECD1_104_001":     //Early Childhood Incomplete Assessment Data
                    
                    if(!driver.findElement(By.xpath("//*[@id='reportDataTable']/tbody/tr[4]/td[5]/center")).getText().equals("INPROGRESS")) {
                        driver.findElement(By.xpath("//table[@id='reportDataTable']/tbody/tr[4]/td[6]/center/a/i")).click();
                        System.out.println("DEBUG");
                    }
                   
                    break;    
            }
        }
        catch(NoSuchElementException e) {
            //Do nothing
        }
        return new TSDSViewReportsParametersPage(driver);
    }
    public TSDSCoreCollectionPage launchExit() {
        driver.findElement(By.linkText("Exit")).click();
        (new WebDriverWait(driver, 120)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                for(String winHandle: driver.getWindowHandles()) {
                    driver.switchTo().window(winHandle);
                    if(driver.getTitle().equals("TSDS: Core Collection")) {
                        break;
                    }
                }
                return d.getTitle().contentEquals("TSDS: Core Collection");
            }
        });
        return new TSDSCoreCollectionPage(driver);
    }
    
    public TSDSCoreCollectionHomePage setUpParams(String acadYr, String collectionId) throws Exception {
        
        //new Select(driver.findElement(By.id("hdrAcadYearLst"))).selectByVisibleText(acadYr);
        selectAnElementById("hdrAcadYearLst",acadYr);
        //new Select(driver.findElement(By.id("hdrCollectionsLst"))).selectByVisibleText(collectionId);
        switch(collectionId) {
            case "BOY KG":
                driver.findElement(By.cssSelector("#hdrCollectionsLst > option[value=\"1\"]")).click();
                break;
            case "EOY PK":
                driver.findElement(By.cssSelector("#hdrCollectionsLst > option[value=\"2\"]")).click();
                new Select(driver.findElement(By.id("hdrCollectionsLst"))).selectByVisibleText(collectionId);
                break;
        }
        //selectAnElementById("hdrCollectionsLst",collectionId);
        //driver.manage().timeouts().pageLoadTimeout(3, TimeUnit.SECONDS);
        //driver.navigate().refresh();
        //TSDSUtilities util = new TSDSUtilities(driver);
        //if(util.isElementPresent(By.id("goButton"))) {
        //    if(util.clickAndRetry(By.id("goButton"))) {
        clickAnElementById("goButton");    
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
            //}
        //}
        
        return new TSDSCoreCollectionHomePage(driver);
    }
    public  TSDSViewReportsPage downloadReport(String rptType) throws InterruptedException  {
        int row;
        String str1 = "//*[@id='reportDataTable']/tbody/tr[";
        String str2 = "]/td[5]/center";
        String status;
        try { 
			switch(rptType) {
                case "ECD0-000-001": //Early Childhood Assessment Completion
                	status = ElementUtils.waitForElement(driver, By.xpath("//*[@id='reportDataTable']/tbody/tr[1]/td[5]"), 20).getText();
                    while(status.contains("PROGRESS")){
                        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
                        driver.findElement(By.id("refreshBtn")).click();
                        Thread.sleep(2000);
                        status = driver.findElement(By.xpath("//*[@id='reportDataTable']/tbody/tr[1]/td[5]")).getText();
                    }
                    Assert.assertFalse(status.equals("FAILED"));
                    driver.findElement(By.xpath("//*[@id='reportDataTable']/tbody/tr[1]/td[5]")).click();
                    break;
                case "ECD1_102_001": //Early Childhood Assessemnt PRE-K Sources   
                    row = 1;
                    while(driver.findElement(By.xpath(str1 + row + str2 )).getText().equals("IN PROGRESS") ){
                        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
                        driver.findElement(By.id("refreshBtn")).click();
                    } 
                    //driver.findElement(By.xpath("//*[@id='reportDataTable']/tbody/tr[3]/td[5]/center/a")).click();
                    driver.findElement(By.xpath(str1 + row + str2 + "/a")).click();
                    break;
               
                    
                case "ECD1_101_001": //Early Childhood Assessment Summary
                case "ECD1_105_001":     //Early Childhood Pre-Kindergarten Data Submission    
                    row = 2;
                    while(driver.findElement(By.xpath(str1 + row + str2 )).getText().equals("IN PROGRESS") ){
                        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
                        driver.findElement(By.id("refreshBtn")).click();
                    }
                    driver.findElement(By.xpath(str1 + row + str2 + "/a")).click();
                    break;
              
                case "ECD1_103_001":     //Early Childhood Kindergarten Data Submission
                case "ECD1_106_001":     //Early Childhood PRE-K Completion
                    row = 3;
                    while(driver.findElement(By.xpath(str1 + row + str2 )).getText().equals("IN PROGRESS") ){
                        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
                        driver.findElement(By.id("refreshBtn")).click();
                    }
                    driver.findElement(By.xpath(str1 + row + str2 +"/a")).click();
                    break;
                  
                case "ECD1_104_001":     //Early Childhood Incomplete Assessment Data
                    row = 4;
                    while(driver.findElement(By.xpath(str1 + row + str2 )).getText().equals("IN PROGRESS") ){
                        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
                        driver.findElement(By.id("refreshBtn")).click();
                    }
                    driver.findElement(By.xpath("//*[@id='reportDataTable']/tbody/tr[4]/td[5]/center/a")).click();
                      
                    break;    
            }
           
          
            
        }
        catch(NoSuchElementException e) {}
        return this;        
           
        
    }
    
    public void clickAnElementById(String id) {
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id))).click();
        driver.findElement(By.id(id)).click();
    }
    
    public void selectAnElementById(String id, String inParam) {
        WebDriverWait wait = new WebDriverWait(driver,3);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
        new Select(driver.findElement(By.id("hdrAcadYearLst"))).selectByVisibleText(inParam);
    }
}
