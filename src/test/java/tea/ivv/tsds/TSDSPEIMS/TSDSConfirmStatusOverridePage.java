package tea.ivv.tsds.TSDSPEIMS;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TSDSConfirmStatusOverridePage {
	private WebDriver driver;
	
	public TSDSConfirmStatusOverridePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public TSDSPEIMSAdministrationPage confirmOverride() {
		driver.findElement(By.id("confirmButtonId")).sendKeys(Keys.ENTER);;
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
