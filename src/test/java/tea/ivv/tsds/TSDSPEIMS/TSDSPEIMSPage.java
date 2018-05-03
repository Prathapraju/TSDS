package tea.ivv.tsds.TSDSPEIMS;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import us.tx.state.tea.seleniumfw.utils.*;

/*In Core Collection Page, user can either select Prepare/Finalize Data, View Reports, Configure Data Set easy buttons
or ECDS Application Home link. User can also change Org Id from this page as well */
public class TSDSPEIMSPage {
	
    private WebDriver driver;
    
    public TSDSPEIMSPage(WebDriver driver) {
        this.driver = driver;
    }
    
    public TSDSDataPromotionPage launchPromoteLoadedData() {
    	driver.findElement(By.id("EasyBtnPromote")).click();
    	(new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                for(String winHandle : driver.getWindowHandles()) {
                    driver.switchTo().window(winHandle);
                    if(driver.getTitle().equals("Promote Loaded Data")) {
                        break;
                    }
                }
                return driver.getTitle().equals("Promote Loaded Data");   
            }
            });
    	return new TSDSDataPromotionPage(driver);
    }
    
    public TSDSPrepareFinalizePage launchPrepareFinalizeData()  {  
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
        
        return new TSDSPrepareFinalizePage(driver);   
    }
	
public WebDriver setUpParams(String acadYr, String collectionId, String submissionId) throws Exception{
        new Select(driver.findElement(By.id("hdrAcadYearLst"))).selectByVisibleText(acadYr);
        new Select(driver.findElement(By.id("hdrCollectionsLst"))).selectByVisibleText(collectionId);
        new Select(driver.findElement(By.id("hdrSubmissionsLst"))).selectByVisibleText(submissionId);
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