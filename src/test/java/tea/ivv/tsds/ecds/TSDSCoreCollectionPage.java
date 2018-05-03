/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tea.ivv.tsds.ecds;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import us.tx.state.tea.seleniumfw.utils.*;

/**
 *
 * @author jtran
 */
public class TSDSCoreCollectionPage {
    private static WebDriver driver;
    protected Logger log = Logger.getLogger(this.getClass().getName());
    public TSDSCoreCollectionPage(WebDriver driver) {
        this.driver = driver;
    }
    public WebDriver launchPrepareFinalizeData()  {
                
        driver.findElement(By.id("EasyBtnFinalSub")).click();
        (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                for(String winHandle : driver.getWindowHandles()) {
                    driver.switchTo().window(winHandle);
                    if(driver.getTitle().equals("Prepare / Finalize Submission")) {
                        break;
                    }
                }
                return driver.getTitle().equals("Prepare / Finalize Submission");   
            }
            }); 
        
        return driver;   
    } 
    
    public WebDriver selectOrgId(String orgId) {
        new Select(driver.findElement(By.id("orgList"))).selectByValue(orgId);
        driver.findElement(By.id("goButton")).click();
        (new WebDriverWait(driver,30)).until(new ExpectedCondition<Boolean>() {
            
            @Override
            public Boolean apply(WebDriver d) {
                for(String winHandle : driver.getWindowHandles()){
                    driver.switchTo().window(winHandle);
                    if(driver.getTitle().equals("TSDS: Core Collection")) 
                        {
                            break;
                        }
                }
                return driver.getTitle().equals("TSDS: Core Collection");
            }    
        }); 
        return driver;
    }
    
    public WebDriver launchECDSApplicationHome() throws Exception {
        JavascriptExecutor executor = null;
        String currentWin = driver.getWindowHandle();
        WebElement we = driver.findElement(By.cssSelector("a[title=\"ECDS Application Home\"]"));
        if (ElementUtils.isElementPresent(driver, By.cssSelector("a[title=\"ECDS Application Home\"]"))) {
            Assert.assertTrue(true, "ECDS Application Home link exists");
            log.info("DEBUG");
            executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", we);
            //driver.findElement(By.xpath("//a[contains(@href, \"javascript:setSelectionCredential('https://tealtst.tea.state.tx.us/CCDS/index.jsp?pagename=home',null,'false','https://tealtst.tea.state.tx.us/TSDS/error','openCORE','selectLink');\")]")).click();
            
        }  
       else {
           log.info("Failed to find ECDS Application home link");
           Assert.fail("ECDS Application Home link does not exist");
        }
        Thread.sleep(3000); //wait for the second page to load
        //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        Set<String>s1 = driver.getWindowHandles();
        Iterator<String> i1 = s1.iterator();
        
        while(i1.hasNext()) {
            String childWin = i1.next();
            if(!currentWin.equals(childWin)) {
                driver.switchTo().window(childWin);
                
                //log.info("Current url: " + driver.getCurrentUrl());
                String url = driver.getCurrentUrl();
                driver.get(url);
            }
            
        }
        return driver;
    }
    public WebDriver launchExit() throws Exception{
        //Need to debug
        System.out.println("Current Url : "+ driver.getCurrentUrl() + " - " + driver.getWindowHandle());
        if(ElementUtils.clickAndRetry(driver, By.linkText("Exit"))) {
       
            (new WebDriverWait(driver,30)).until(new ExpectedCondition<Boolean>() {
            
            @Override
            public Boolean apply(WebDriver d) {
                for(String winHandle : driver.getWindowHandles()){
                    driver.switchTo().window(winHandle);
                    if(driver.getTitle().equals("Texas Education Agency")) 
                        {
                            break;
                        }
                }
                return driver.getTitle().equals("Texas Education Agency");
            }    
            });
        }    
       return driver;
    }    
    
    public void  closeAll() {
        driver.close();
    }
   
    /*
    public void launchECDSApplicationHome() {
        //Much to do
    }
    
*/
    public WebDriver launchViewReports() {
        //Select View Reports easy button from the TSDS: Core Collection page
       driver.findElement(By.id("EasyBtnReport")).click();
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
        
        return driver;   
    } 
}
