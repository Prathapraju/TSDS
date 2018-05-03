package tea.ivv.tsds.TSDSPEIMS;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TSDSPEIMSAdministrationPage {
	private WebDriver driver;
	
	public TSDSPEIMSAdministrationPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public String getConfirmationMessage() {
		return driver.findElement(By.id("confirmmessage")).getText();
	}
	
	public TSDSConfirmStatusOverridePage overrideCollection(String leaid, String type, String newstatus) {
		driver.findElement(By.id("organizationID")).sendKeys(leaid);
		Select typeselect = new Select(driver.findElement(By.id("displayActionId")));
		typeselect.selectByVisibleText(type);
		Select statusselect = new Select(driver.findElement(By.id("displayStatusId")));
		statusselect.selectByVisibleText(newstatus);
		driver.findElement(By.id("overrideButtonId")).click();
		(new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                for(String winHandle : driver.getWindowHandles()) {
                    driver.switchTo().window(winHandle);
                    if(driver.getTitle().contains("Confirm Status Override")) {
                        break;
                    }
                }
                return driver.getTitle().equals("Confirm Status Override");   
            }
            });
		return new TSDSConfirmStatusOverridePage(driver);
	}
	
	public TSDSPEIMSAdministrationPage setUpParams(String acadYr, String collectionId, String submissionId) throws InterruptedException {
		waitForPageLoad("Override SOA/Submission Status");
        new Select(driver.findElement(By.id("hdrAcadYearLst"))).selectByVisibleText(acadYr);
        new Select(driver.findElement(By.id("hdrCollectionsLst"))).selectByVisibleText(collectionId);
        new Select(driver.findElement(By.id("hdrSubmissionsLst"))).selectByVisibleText(submissionId);
        Thread.sleep(1000);
        waitForElement(By.id("goButton"), 1000).click();
        (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                for(String winHandle : driver.getWindowHandles()) {
                    driver.switchTo().window(winHandle);
                    if(driver.getTitle().equals("Override SOA/Submission Status")) {
                        break;
                    }
                }
                return driver.getTitle().equals("Override SOA/Submission Status");   
            }
            });
        return this;
    }
	
	public TSDSPEIMSPage launchExit() {
		String currentWindowHandle = driver.getWindowHandle();
		ArrayList<String> windowHandles = new ArrayList<String>(driver.getWindowHandles());
		driver.findElement(By.linkText("Exit")).click();
		for (String window:windowHandles){
	        if (!window.equals(currentWindowHandle)){
	            driver.switchTo().window(window);
	        }
	    }
		return new TSDSPEIMSPage(driver);
	}
	
    public WebElement waitForElement(By by, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver,timeout);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        return element;
     }
    
    public void waitForPageLoad(String title){
    	WebDriverWait wait = new WebDriverWait(driver, 100);
    	wait.until(ExpectedConditions.titleIs(title));
    }


}
