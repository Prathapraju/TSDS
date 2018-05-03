package tea.ivv.tsds.TSDSDashboards;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TSDSDashboardLEAAdminPage {
	private WebDriver driver;
	
	public TSDSDashboardLEAAdminPage(WebDriver driver){
		this.driver = driver;
	}
	
	public TSDSDashboardsLEAOverviewPage clicklea(String leaname) {
		driver.findElement(By.linkText(leaname)).click();
		waitforPageSwitch("Overview");
		return new TSDSDashboardsLEAOverviewPage(driver);
	}
	
	private void waitforPageSwitch(final String pagename) {
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                for(String winHandle: driver.getWindowHandles()) {
                    driver.switchTo().window(winHandle);
                    if (driver.getTitle().contains("ISD - " + pagename)) {
                        break;
                    }
                }
                return d.getTitle().contains("ISD - " + pagename);
            }
        });
	}

}
