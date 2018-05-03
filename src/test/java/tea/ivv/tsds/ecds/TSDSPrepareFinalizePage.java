/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tea.ivv.tsds.ecds;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import us.tx.state.tea.seleniumfw.utils.*;

/**
 *
 * @author jtran
 */
public class TSDSPrepareFinalizePage {
    private static WebDriver driver;
    private static boolean isPrepare;
    private static boolean prepareEnable;
    private static boolean isComplete;
    private static boolean completeEnable;
    private static boolean isReset;
    private static boolean resetEnable;
    private Logger log = Logger.getLogger(this.getClass().getName());
    public TSDSPrepareFinalizePage(WebDriver driver) {
        this.driver = driver;
    } 
    public void setPrepare(boolean isPrepare) {
        this.isPrepare = isPrepare;
    }
    public boolean getPrepare() {
        return isPrepare;
    }
    public boolean prepareEnabled() {
        prepareEnable  = driver.findElement(By.id("prepareButtonId")).isEnabled();
        return  prepareEnable;
    }
    public void setComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }
    public boolean getComplete() {
        return isComplete;
    }
    public boolean completeEnabled() {
        completeEnable = driver.findElement(By.id("completeButtonId")).isEnabled();
        return completeEnable;
    }
    public void setReset(boolean isReset) {
        this.isReset = isReset;
    }
    public boolean getReset() {
        return isReset;
    }    
    public boolean resetEnabled() {
        resetEnable = driver.findElement(By.id("resetButtonId")).isEnabled();
        return resetEnable;
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
    
    public WebDriver runPrepare() throws Exception {
        long timeoutInSeconds = 1540;
        if(ElementUtils.isElementPresent(driver, By.id("prepareButtonId")) && prepareEnabled()) {
            driver.findElement(By.id("prepareButtonId")).click();
            log.info("Processing...");
            try {

                new WebDriverWait(driver,timeoutInSeconds).until(new ExpectedCondition<Boolean>() {
                @Override
             
                    public Boolean apply(WebDriver d) {
                        if (! ElementUtils.isElementPresent(driver, By.id("progressbartext"))) {
                            setPrepare(true);
                            return true;
                            
                        }
                        else {
                            return false;
                        }
                    }
                });  
                
            }
 
            catch (StaleElementReferenceException e) {
                 log.info("Progress bar is no longer exists");
            }
                               
        }
        else {
           log.info("Prepare button is disabled.");
        }
       
        return driver;
    }
    
    public WebDriver runComplete() throws Exception {
        long timeoutInSeconds = 240;
        log.info("Launch Complete..." + completeEnabled());
        if ( completeEnabled() && ElementUtils.isElementPresent(driver, By.id("completeButtonId"))) {
            
            driver.findElement(By.id("completeButtonId")).click();
        
            //new WebDriverWait(driver, timeoutInSeconds).until(ExpectedConditions.invisibilityOfElementLocated(By.id("progressbartext")));
            new WebDriverWait(driver, timeoutInSeconds).ignoring(StaleElementReferenceException.class).until(new ExpectedCondition<Boolean>() {

                    @Override
                    public Boolean apply(WebDriver driver) {
                        if (! ElementUtils.isElementPresent(driver, By.id("progressbartext"))) {
                            return true;
                            
                        }
                        else {
                            return false;
                        }
                    }

                });

            if(driver.findElements(By.id("confirmErrmessage")).size() > 0) {
                log.info("Data contains fatal error");
                
            }
            else {
                log.info("Data contains error free");
                new WebDriverWait(driver, timeoutInSeconds).until(ExpectedConditions.visibilityOfElementLocated(By.id("cbconfirmId")));
                log.info(driver.findElement(By.id("confirmAckMsg")).getText().trim());
                Assert.assertTrue(driver.findElement(By.id("confirmAckMsg")).getText().trim().contains("By checking this box, I acknowledge that all data included in the submission has been validated and reviewed for accuracy and authenticity. All errors have been reviewed and confirmed."));
                driver.findElement(By.id("cbconfirmId")).click();   //Check radio button to confirm
                driver.findElement(By.id("confirmCompleteBtn")).click(); //Click Confirm button
                new WebDriverWait(driver, timeoutInSeconds).until(ExpectedConditions.visibilityOfElementLocated(By.id("resetButtonId")));
                setComplete(true); 
                
            }
        }
        else {
            log.info("Complete button is disabled");
                
        }
        return driver;
    }
    
    public WebDriver runReset() {
        long timeoutInSeconds = 30;
        log.info("Launch Reset..." + resetEnabled());
        if(resetEnabled()&& ElementUtils.isElementPresent(driver, By.id("resetButtonId"))) {
            log.info("Resetting data...");
            driver.findElement(By.cssSelector("#resetButtonId")).click();
            new WebDriverWait(driver, timeoutInSeconds).until(ExpectedConditions.visibilityOfElementLocated(By.id("prepareButtonId")));

            setReset(true);
        }    
        else {
            log.info("Reset button is disabled");
        }
        return driver;
    }
    
    public WebDriver runValidateSubmissionData() throws InterruptedException {
        log.info("Launch Validations Submission Data...");
        Actions action = new Actions(driver);
        WebElement we = driver.findElement(By.xpath("//*[@id='nav']/li[2]/span"));
        action.moveToElement(we).build().perform();
        driver.findElement(By.linkText("Validate Submission Data")).click();
        (new WebDriverWait(driver, 240)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                for(String winHandle : driver.getWindowHandles()) {
                    driver.switchTo().window(winHandle);
                    if(driver.getTitle().equals("Validate Submission Data")) {
                        break;
                    }
                }
                return driver.getTitle().equals("Validate Submission Data");   
            }
            });
        log.info("Current page = " + driver.getTitle());
        return driver;
       
    }
    
    public WebDriver runMonitorDataValidations() {
        log.info("Launch Monitor Data Validations...");
        Actions action = new Actions(driver);
        WebElement we = driver.findElement(By.xpath("//ul[@id='nav']/li[2]/span"));
        action.moveToElement(we).moveToElement(driver.findElement(By.linkText("Monitor Data Validations"))).click().build().perform();
        (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                for(String winHandle : driver.getWindowHandles()) {
                    driver.switchTo().window(winHandle);
                    if(driver.getTitle().equals("Monitor Data Validations")) {
                        break;
                    }
                }
                return driver.getTitle().equals("Monitor Data Validations");   
            }
            });
        
        log.info("Current page = " + driver.getTitle());
        return driver;
    }
    
    public WebDriver selectViewReports() {
        try {
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
    public void launchExit() {
        driver.findElement(By.linkText("Exit")).click();
    }
}
