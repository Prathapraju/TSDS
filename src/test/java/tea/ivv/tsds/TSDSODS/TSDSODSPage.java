package tea.ivv.tsds.TSDSODS;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TSDSODSPage {
	private WebDriver driver;
	
	public TSDSODSPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public TSDSManageDataLoadsPage launchManageDataLoads() {
		driver.findElement(By.id("EasyBtnEDM")).click();
		(new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                for(String winHandle : driver.getWindowHandles()) {
                    driver.switchTo().window(winHandle);
                    if(driver.getTitle().equals("eScholar Data Manager")) {
                        break;
                    }
                }
                return driver.getTitle().equals("eScholar Data Manager");   
            }
            });
    	return new TSDSManageDataLoadsPage(driver);
	}

}
