package tea.ivv.tsds.TSDSDashboards;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TSDSDashboardsDistrictInfoPage {
	private WebDriver driver;
	
	public TSDSDashboardsDistrictInfoPage(WebDriver driver) {
		this.driver = driver;
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
