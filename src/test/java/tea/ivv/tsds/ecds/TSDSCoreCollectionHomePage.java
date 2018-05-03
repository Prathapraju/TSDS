/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tea.ivv.tsds.ecds;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import us.tx.state.tea.seleniumfw.utils.*;

/**
 *
 * @author jtran
 */
public class TSDSCoreCollectionHomePage {
    private static WebDriver driver;
    protected Logger log = Logger.getLogger(this.getClass().getName());
    public TSDSCoreCollectionHomePage(WebDriver driver) {
        this.driver = driver;
        
    }
    public WebDriver launchPrepareFinalizeSubmission() {
        driver.findElement(By.id("prepareFinalize")).click();
            
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
            //log.info("Current URL: " + driver.getTitle());
	    if (driver.getTitle().equals("Prepare / Finalize Submission")) 
	        {
		    break;
		}
        }
             
        return driver;
    }
   
    public WebDriver launchViewReports() {
        driver.findElement(By.id("viewReports")).click();
        for(String winHandle: driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
            if (driver.getTitle().equals("View Reports"))
            {
                break;
            }
        }
        return driver;
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
}
