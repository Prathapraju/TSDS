package tea.ivv.tsds.TSDSPEIMS;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TSDSPEIMSApplicationHomePage {
	private WebDriver driver;
	
	public TSDSPEIMSApplicationHomePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public TSDSPEIMSAdministrationPage updateSubmissionStatus() {
    	Actions action = new Actions(driver);
    	WebElement AccessData = driver.findElement(By.xpath("//*[@id='nav']/li[4]/span"));
    	action.moveToElement(AccessData).build().perform();
    	driver.findElement(By.partialLinkText("Override SOA/Submission Status")).click();
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
    	return new TSDSPEIMSAdministrationPage(driver);
	}

}
